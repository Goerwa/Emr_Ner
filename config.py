# coding:utf8
import warnings
import torch as t

class DefaultConfig(object):
    model = 'NERLSTM_CRF'  # 使用的模型，名字必须与models/__init__.py中的名字一致

    pickle_train_path = 'train.pkl'  #

    load_model_path = None  # 加载预训练的模型的路径，为None代表不加载
    max_len = 70
    load_vec_path = 'words_vec.json'

    batch_size = 16 # batch size
    num_workers = 0  # how many workers for loading data
    print_freq = 20  # print info every N batch

    max_epoch = 200
    lr = 0.001  # initial learning rate
    lr_decay = 0.5  # when val_loss increase, lr = lr*lr_decay
    weight_decay = 1e-5  # 损失函数

    word_dim = 300
    cnn_out_dim = 150
    filter_size = 3
    embedding_dim = 766
    hidden_dim = 256
    dropout = 0.5

    def _parse(self, kwargs):
        """
        根据字典kwargs 更新 config参数
        """
        for k, v in kwargs.items():
            if not hasattr(self, k):
                warnings.warn("Warning: opt has not attribut %s" % k)
            setattr(self, k, v)
        
        opt.device =t.device('cuda') if opt.use_gpu else t.device('cpu')


        print('user config:')
        for k, v in self.__class__.__dict__.items():
            if not k.startswith('_'):
                print(k, getattr(self, k))

opt = DefaultConfig()
