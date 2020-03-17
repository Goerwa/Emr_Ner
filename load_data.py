import json

# 从语料文件中划分句子
def get_split_sentence(raw_data_file,split_data_file):
    sentence=[]
    with open(raw_data_file,'r',encoding='utf-8') as f:
        split_word=[]
        for line in f.readlines():
            item=line.strip().split()
            # print(item)
            if len(item)==8:
                word=item[1]
                split_word.append(word)
            else:
                sentence.append(split_word)
                split_word=[]
    f.close()
    with open(split_data_file,'w') as fs:
        for s in sentence:
            new_s=' '.join(s)
            fs.writelines(new_s)
            fs.writelines('\n')
    fs.close()

# 获取每个词的标注
def get_data(raw_data_file,new_data_file):
    sentence=[]
    with open(raw_data_file,'r',encoding='utf-8') as f:
        word,pos,head,label=[],[],[],[]
        for line in f:
            item=line.strip().split()
            if len(item)==8:
                word.append(item[1])
                pos.append(item[4])
                head.append(int(item[6]))
                label.append(item[7])
            else:
                sentence.append({'word':word,'pos':pos,'head':head,'label':label })
                word, pos, head, label = [], [], [], []
    f.close()

    with open(new_data_file,'w') as fs:
        for s in sentence:
            json.dump(s,fs,ensure_ascii=False)
            fs.writelines('\n')
    fs.close()
    print('句子个数：' + str(len(sentence)))

# 构造词表
def get_vocab(data_file,vocab_file):
    words=["<UNK>","<ROOT>","<NULL>","<P><UNK>","<P><NULL>","<P><ROOT>","<l><NULL>"]
    words_num, pos_num, label_num = 0, 0, 0
    with open(data_file,'r',encoding='utf-8') as f:
        for line in f:
            data=json.loads(line)
            word=data["word"]
            pos=data["pos"]
            label=data["label"]

            for w in word:
                if w not in words:
                    words.append(w)
                    words_num+=1

            for p in pos:
                if "<p>"+p not in words:
                    words.append("<p>"+p)
                    pos_num+=1

            for l in label:
                if "<l>"+l not in words:
                    words.append("<l>"+l)
                    label_num+=1

    f.close()
    words2id={j:i for i,j in enumerate(words)}
    with open(vocab_file,'w') as fs:
        fs.write(json.dumps(words2id,ensure_ascii=False,indent=4))
    fs.close()

    print("单词个数:[%d]" % words_num)
    print("词性个数:[%d]" % pos_num)
    print("关系个数:[%d]" % label_num)
    '''
    单词个数:[14868]
    词性个数:[77]
    关系个数:[69]
    '''

# 将词转化为相应的数字
def data2id(vocab_file,data_file,data2id_file):
    with open(vocab_file,'r',encoding='utf-8') as f:
        vocab=json.load(f)
    f.close()
    data2id=[]
    with open(data_file,'r',encoding='utf-8') as fs:
        for line in fs:
            data=json.loads(line)
            word=[vocab["<ROOT>"]]+[vocab[w] if w in vocab else vocab["<UNK>"] for w in data["word"]]
            pos=[vocab["<P><ROOT>"]]+[vocab["<p>"+p] if "<p>"+p in vocab else vocab["<P><UNK>"] for p in data["pos"]]
            head=[-1]+data["head"]
            label=[-1]+[vocab["<l>"+l] if "<l>"+l in vocab else -1 for l in data["label"]]
            data2id.append({"word":word,"pos":pos,"head":head,"label":label})
    fs.close()

    with open(data2id_file,'w') as fw:
        for item in data2id:
            json.dump(item,fw,ensure_ascii=False)
            fw.write("\n")
    fw.close()




if __name__=='__main__':
    train_file = 'lab_data/train.conll'
    sentence_file = 'lab_data/train_sentence.txt'
    # get_split_sentence(train_file, sentence_file)

    new_train_file = 'lab_data/train.json'
    # get_data(train_file, new_train_file)

    vocab_file = 'lab_data/vocab.json'
    # get_vocab(new_train_file,vocab_file)

    train_data2id_file = "lab_data/train_id.json"
    # data2id(vocab_file,new_train_file,train_data2id_file)

    test_file = 'lab_data/dev.conll'
    test_sentence_file = 'lab_data/test_sentence.txt'
    # get_split_sentence(test_file, test_sentence_file)

    new_test_file = 'lab_data/test.json'
    # get_data(test_file, new_test_file)

    vocab_file = 'lab_data/vocab.json'
    # get_vocab(new_train_file,vocab_file)

    test_data2id_file = "lab_data/test_id.json"
    # data2id(vocab_file,new_test_file,test_data2id_file)