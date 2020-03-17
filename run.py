import json
import pickle
import os
import numpy as np
from sklearn.metrics import precision_score, recall_score, f1_score, classification_report
from config import opt
import torch
from torch.utils.data import Dataset,DataLoader
import torch.nn as nn
import torch.optim as optim
from LSTM import NERLSTM
from LSTM_CRF import NERLSTM_CRF
from utils import get_f1score
from embed_util import load_vec

# torch.cuda.set_device(0)

class NERDataset(Dataset):
    def __init__(self, X, Y, *args, **kwargs):
        self.data = [{'x':X[i],'y':Y[i]} for i in range(X.shape[0])]

    def __getitem__(self, index):
        return self.data[index]

    def __len__(self):
        return len(self.data)

def train1():
    with open(opt.pickle_train_path, 'rb') as inp:
        word2id = pickle.load(inp)
        id2word = pickle.load(inp)
        tag2id = pickle.load(inp)
        id2tag = pickle.load(inp)
        x_train = pickle.load(inp)
        y_train = pickle.load(inp)
        x_valid = pickle.load(inp)
        y_valid = pickle.load(inp)

    print("train len:", len(x_train))
    print("valid len", len(x_valid))
    # print("test len", len(x_test))
    train_dataset = NERDataset(x_train,y_train)
    valid_dataset = NERDataset(x_valid, y_valid)
    # valid_dataset = NERDataset(x_valid, y_valid)
    # test_dataset = NERDataset(x_test, y_test)

    train_dataloader = DataLoader(train_dataset, batch_size=opt.batch_size, shuffle=True, num_workers=opt.num_workers)
    valid_dataloader = DataLoader(valid_dataset, batch_size=opt.batch_size, shuffle=True, num_workers=opt.num_workers)
    # test_dataloader = DataLoader(test_dataset, batch_size=opt.batch_size, shuffle=True, num_workers=opt.num_workers)
    # x = train_dataset[0]
    # print(x)
    # for index, batch in enumerate(train_dataloader):
    #     print(index)
    #     print(batch)
    models = {'NERLSTM': NERLSTM,
              'NERLSTM_CRF': NERLSTM_CRF}
    all_vec = load_vec(opt.load_vec_path)
    # device = torch.device('cuda')
    # model = models[opt.model](opt.embedding_dim, opt.hidden_dim, opt.dropout, word2id, tag2id).cuda()
    model = models[opt.model](opt.word_dim, opt.embedding_dim, opt.hidden_dim, opt.filter_size, opt.cnn_out_dim,
                              opt.dropout, word2id, tag2id, all_vec).cuda()

    criterion = nn.CrossEntropyLoss(ignore_index=0)
    optimizer = optim.Adam(model.parameters(), lr=opt.lr, weight_decay=opt.weight_decay)

    if opt.model == 'NERLSTM':
        for epoch in range(opt.max_epoch):
            model.train()
            for index, batch in enumerate(train_dataloader):
                optimizer.zero_grad()
                X = batch['x'].cuda()
                y = batch['y'].cuda()

                y = y.view(-1, 1)
                y = y.squeeze(-1)
                pred = model(X)
                pred = pred.view(-1, pred.size(-1))
                loss = criterion(pred, y)
                loss.backward()
                optimizer.step()
                if index % 200 == 0:
                    print('epoch:%04d,------------loss:%f' % (epoch, loss.item()))

            aver_loss = 0
            preds, labels = [], []
            for index, batch in enumerate(valid_dataloader):
                model.eval()
                val_x, val_y = batch['x'].cuda(), batch['y'].cuda()
                predict = model(val_x)
                predict = torch.argmax(predict, dim=-1)
                if index % 500 == 0:
                    print([id2word[i.item()] for i in val_x[0].cpu() if i.item() > 0])
                    length = [id2tag[i.item()] for i in val_y[0].cpu() if i.item() > 0]
                    print(length)
                    print([id2tag[i.item()] for i in predict[0][:len(length)].cpu() if i.item() > 0])

                # 统计非0的，也就是真实标签的长度
                leng = []
                for i in val_y.cpu():
                    tmp = []
                    for j in i:
                        if j.item() > 0:
                            tmp.append(j.item())
                    leng.append(tmp)

                # 提取真实长度的预测标签
                for index, i in enumerate(predict.tolist()):
                    preds.extend(i[:len(leng[index])])

                # 提取真实长度的真实标签
                for index, i in enumerate(val_y.tolist()):
                    labels.extend(i[:len(leng[index])])

            precision = precision_score(labels, preds, average='macro')
            recall = recall_score(labels, preds, average='macro')
            f1 = f1_score(labels, preds, average='macro')
            report = classification_report(labels, preds)
            print(report)
    elif opt.model == 'NERLSTM_CRF':
        best_score = 0.0
        for epoch in range(opt.max_epoch):
            model.train()
            for index, batch in enumerate(train_dataloader):
                optimizer.zero_grad()
                X = batch['x'].cuda()
                y = batch['y'].cuda()
                # CRF
                loss = model.log_likelihood(X, y)
                loss.backward()
                # CRF
                torch.nn.utils.clip_grad_norm_(parameters=model.parameters(), max_norm=10)

                optimizer.step()
                if index % 200 == 0:
                    print('best_score:%f' % (best_score))
                    print('epoch:%02d,idnex%4d------------loss:%f' % (epoch, index, loss.item()))

            aver_loss = 0
            preds, labels = [], []
            for index, batch in enumerate(valid_dataloader):
                model.eval()
                val_x, val_y = batch['x'].cuda(), batch['y'].cuda()
                predict = model(val_x)
                # CRF
                loss = model.log_likelihood(val_x, val_y)
                aver_loss += loss.item()
                # 统计非0的，也就是真实标签的长度
                leng = []
                for i in val_y.cpu():
                    tmp = []
                    for j in i:
                        if j.item() > 0:
                            tmp.append(j.item())
                    leng.append(tmp)

                for index, i in enumerate(predict):
                    preds += i[:len(leng[index])]

                for index, i in enumerate(val_y.tolist()):
                    labels += i[:len(leng[index])]
            aver_loss /= (len(valid_dataloader) * 64)
            precision = precision_score(labels, preds, average='macro')
            recall = recall_score(labels, preds, average='macro')
            f1 = f1_score(labels, preds, average='macro')
            # report = classification_report(labels, preds)
            # print(report)
            print('p', precision)
            print('r', recall)
            print('f1', f1)
            if f1 > best_score:
                best_score = f1
                path_name = './model/model' + str(epoch) + '----' + str(f1) + '.pkl'
                torch.save(model, path_name)
                print('model has been saved')


def test1(model_path,output_file,output_file1):
    def list2tags(l_list):
        r = []
        for l in l_list:
            r.append(id2tag[l])
        return r
    with open(opt.pickle_train_path, 'rb') as inp:
        word2id = pickle.load(inp)
        id2word = pickle.load(inp)
        tag2id = pickle.load(inp)
        id2tag = pickle.load(inp)
        x_train = pickle.load(inp)
        y_train = pickle.load(inp)
        x_test = pickle.load(inp)
        y_test = pickle.load(inp)

    print("valid len", len(x_test))

    test_dataset = NERDataset(x_test, y_test)

    test_dataloader = DataLoader(test_dataset, batch_size=opt.batch_size, shuffle=False, num_workers=opt.num_workers)

    model = torch.load(model_path)
    model.eval()

    aver_loss = 0
    preds, labels = [], []
    for index, batch in enumerate(test_dataloader):
        model.eval()
        val_x, val_y = batch['x'].cuda(), batch['y'].cuda()
        predict = model(val_x)
        # CRF
        loss = model.log_likelihood(val_x, val_y)
        aver_loss += loss.item()
        # 统计非0的，也就是真实标签的长度
        leng = []
        for i in val_y.cpu():
            tmp = []
            for j in i:
                if j.item() > 0:
                    tmp.append(j.item())
            leng.append(tmp)

        for index, i in enumerate(predict):
            preds += i[:len(leng[index])]

        for index, i in enumerate(val_y.tolist()):
            labels += i[:len(leng[index])]

    print('prediction\n' + str(len(list2tags(preds))) + '\n', list2tags(preds))
    print('labels\n'+ str(len(list2tags(labels))) + '\n', list2tags(labels))
    aver_loss /= (len(test_dataloader) * 64)
    # precision = precision_score(labels, preds, average='macro')
    # recall = recall_score(labels, preds, average='macro')
    # f1 = f1_score(labels, preds, average='macro')
    precision = precision_score(labels, preds, average='macro')
    recall = recall_score(labels, preds, average='macro')
    f1 = f1_score(labels, preds, average='macro')
    # report = classification_report(labels, preds)
    # print(report)
    print('p',precision)
    print('r',recall)
    print('f1',f1)
    p, r, f = get_f1score(list2tags(preds), list2tags(labels))
    print('p', p)
    print('r', r)
    print('f1', f)
    # with open(output_file,'w') as fw:
    #     for p in list2tags(preds):
    #         fw.write(p)
    #         fw.write('\n')
    # with open(output_file1,'w') as fw1:
    #     for p in list2tags(labels):
    #         fw1.write(p)
    #         fw1.write('\n')

if __name__ == '__main__':
    # train1()

    test1('model/model64----0.9100404177625905.pkl ','prediction.txt','result.txt')


