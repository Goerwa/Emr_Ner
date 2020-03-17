import json
import numpy as np
from torch import nn
import torch
def get_all_word(input_file,word2id_file,output_file):
    words_embed = {}
    word2ix = {}
    with open(word2id_file, 'r', encoding='utf-8') as fv:
        word2ix = json.load(fv)
    fv.close()
    row = 0
    with open(input_file, mode='r',encoding='utf-8')as f:
        lines = f.readlines()
        for line in lines:
            line_list = line.split()
            word = line_list[0]
            embed = line_list[1:]
            embed = [float(num) for num in embed]
            words_embed[word] = embed
            # if row > 20000:
            #     break
            # row += 1
    all_vec = []
    for ix in list(word2ix.keys()):
        if ix in words_embed.keys():
            all_vec.append({ix:words_embed[ix]})
        else:
            all_vec.append({ix:np.around(np.random.uniform(-1,1,(1,300)),6).tolist()[0]})

    with open(output_file,'w',encoding='utf-8') as fw:
        for s in all_vec:
            json.dump(s,fw,ensure_ascii=False)
            fw.writelines('\n')
    fw.close()

def load_vec(input_file):
    all_vec = []
    with open(input_file, 'r', encoding='utf-8') as input_data:
        for l in input_data.readlines():
            single_line = json.loads(l)
            all_vec.append(single_line[list(single_line.keys())[0]])
    # print(len(all_vec))
    # print(all_vec[-1])
    print(len(all_vec))
    return all_vec


if __name__ == '__main__':
    # get_all_word('data/sgns.target.word-character.char1-2.dynwin5.thr10.neg5.dim300.iter5','word_dict.json',
    #              'words_vec.json')

    load_vec('words_vec.json')

    # t = np.random.uniform(-1,1,(1,300))
    # print(t)
    # print(np.around(t,6).tolist()[0])

    # embedding = nn.Embedding.from_pretrained(torch.FloatTensor(load_vec('words_vec.json')))
    # edge_embedding = nn.Embedding(4,16)
    #
    # x = torch.LongTensor([[0, 1, 2, 3, 1, 3],
    #                      [55,66,77,88,1,1]])
    # x = x.transpose(0,1)
    # print(x)
    # print(embedding(x[:2]).shape)
    # x1 = x[:2]
    # x2 = x[2:4]
    # x3 = x[4:]
    # print(x1)
    # print(x2)
    # print(x3)
    # x_1 = embedding(x1)
    # x_2 = embedding(x2)
    # x_3 = edge_embedding(x3)
    # print(x_1.shape)
    # print(x_2.shape)
    # print(x_3.shape)
    # x4 = torch.cat([x_1,x_2,x_3],dim=2)
    # print(x_1.shape)
    # print(x_2.shape)
    # print(x_3.shape)
    # print(x4)
    # print(x4.shape)



