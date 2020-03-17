import torch
import torch.nn as nn
import torch.optim as optim
from torchcrf import CRF
from torch.nn import init
import torch.nn.functional as F

class NERLSTM_CRF(nn.Module):
    def __init__(self, word_dim, embedding_dim, hidden_dim, filter_size, filter_num ,dropout, word2id, tag2id, word_vec):
        super(NERLSTM_CRF, self).__init__()
        self.max_len = 70
        self.word_dim = word_dim
        self.filter_size = filter_size
        self.embedding_dim = embedding_dim
        self.hidden_dim = hidden_dim
        self.vocab_size = len(word2id) + 1
        self.tag_to_ix = tag2id
        self.tagset_size = len(tag2id)
        self.word_embeds = nn.Embedding.from_pretrained(torch.FloatTensor(word_vec))
        self.edge_embeds = nn.Embedding(4,16)
        self.dropout = nn.Dropout(dropout)
        self.filter_num = filter_num


        self.cnn = CNN(self.vocab_size, self.word_dim, self.filter_size, self.filter_num,
                       dropout, self.word_embeds)

        self.lstm = nn.LSTM(self.embedding_dim, self.hidden_dim // 2, num_layers=1, bidirectional=True, batch_first=False)

        self.hidden2tag = nn.Linear(self.hidden_dim, self.tagset_size)
        self.crf = CRF(self.tagset_size)

    def forward(self, x):
      #CRF
      x = x.transpose(0,1)
      batch_size = x.size(1)
      sent_len = x.size(0)
      x_1 = self.word_embeds(x[:self.max_len])
      x_2 = self.word_embeds(x[self.max_len:2*self.max_len])
      x_3 = self.edge_embeds(x[2*self.max_len:])
      char_feats = self.cnn(x_1)
      embedding = torch.cat([x_1, x_2, x_3, char_feats], dim=2)
      # embedding = torch.cat([x_1,x_2,x_3],dim=2)
      # embedding = self.word_embeds(x)
      outputs, hidden = self.lstm(embedding)
      outputs = self.dropout(outputs)
      outputs = self.hidden2tag(outputs)
      #CRF
      outputs = self.crf.decode(outputs)
      return outputs

    def log_likelihood(self, x, tags):
        x = x.transpose(0,1)
        batch_size = x.size(1)
        sent_len = x.size(0)
        x_1 = self.word_embeds(x[:self.max_len])
        x_2 = self.word_embeds(x[self.max_len:2 * self.max_len])
        x_3 = self.edge_embeds(x[2 * self.max_len:])
        char_feats = self.cnn(x_1)
        # print(x_1.shape)
        # print(char_feats.shape)
        embedding = torch.cat([x_1,x_2 ,x_3, char_feats], dim=2)
        # print(x_1.shape)
        # print(x_2.shape)
        # print(x_3.shape)
        # embedding = torch.cat([x_1, x_2, x_3], dim=2)
        tags = tags.transpose(0,1)
        # embedding = self.word_embeds(x)
        outputs, hidden = self.lstm(embedding)
        outputs = self.dropout(outputs)
        outputs = self.hidden2tag(outputs)
        return - self.crf(outputs, tags)


class CNN(nn.Module):

    def __init__(self, char_size, char_dim, filter_size, char_out_dimension, dropout, word_vec):
        super(CNN, self).__init__()
        self.char_size = char_size
        self.char_dim = char_dim
        self.filter_size = filter_size
        self.out_channels = char_out_dimension
        self.char_embeds = word_vec
        self.char_cnn = nn.Conv2d(in_channels=1, out_channels=self.out_channels,
                                  kernel_size=(self.filter_size, self.char_dim))
        self.relu = nn.ReLU()
        self.dropout = nn.Dropout(dropout)

    def forward(self, x):
        """
        Arguments:
            inputs: [batch_size, word_len, char_len]
        """
        # print(x.shape)
        x = x.transpose(0, 1)
        x = x.unsqueeze(1)
        x = self.char_cnn(x)
        x = self.relu(x)
        x = F.max_pool2d(x, kernel_size=(x.size(2), 1))
        x = self.dropout(x.squeeze())
        x = x.repeat(70,1,1)
        # print(x.shape)
        return x