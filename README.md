# 电子病历实体识别
结果如下
| |P|R|F1|
|--|--|--|--|
|Char-LSTM-CRF|90.96|90.81|90.77|
|word-LSTM-CRF|90.15|87.70|88.50|
|Char+word-LSTM-CRF|91.38|89.42|90.00|
|Char+edge-LSTM-CRF|91.66|90.45|90.90|
|Char+word+edge-LSTM-CRF|91.94|90.79|91.03|
|CNN-LSTM-CRF(char)|91.56|91.08|91.18|
|CNN-LSTM-CRF(Char+word+edge)|91.65|91.02|91.00|
##
batch_size = 16<br>
max_epoch = 200<br>
lr = 0.001<br>
embedding_dim = 300<br>
hidden_dim = 128<br>
dropout = 0.5
