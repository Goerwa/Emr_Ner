import os
import re
import json
import math
import collections
import pandas as pd
import pickle
import numpy as np
from sklearn.model_selection import train_test_split
import jieba
from embed_util import get_all_word

# 获取所有文件
def getListFiles(path):
    ret = []
    for root, dirs, files in os.walk(path):
        for filespath in files:
            # print(filespath[-3:])
            if filespath[-3:] == 'ent':
                ret.append(os.path.join(root,filespath))
    ret = sorted(ret)
    return ret

def get_all_data(all_datafile,output_file):
    def load_data(data_file):
        data = ''
        with open(data_file, 'r', encoding='UTF-8') as f:
            # print(chardet.detect(f.read()))
            for line in f:
                data += line
        f.close()
        return  data

    def get_pos(pos_list,type):
        r = []
        if len(pos_list) == 1:
            r.append('S-' + type)
            return r
        for i in range(len(pos_list)-1):
            if i == 0:
                r.append('B-' + type)
            else:
                r.append('I-' + type)
        r.append('E-' + type)
        return r

    def adjust(input_list):
        punch = [' ','\t']
        r = []
        flag = False
        falg_n = False
        for i in input_list:
            if falg_n == True:
                falg_n = False
                continue
            if flag == False:
                if i == '<':
                    flag = True
                else:
                    if i not in punch:
                        if len(i.split('\t')) == 2 or i == '\n':
                            r.append(i)
                        else:
                            r.append(i + '\t' + 'O')
            else:
                if i == '>':
                    flag = False
                    falg_n = True
        # print(r)
        return r

    def replace_punch(input_list):
        r = []
        digit = re.compile(r'[0-9]')  # 数字
        alpha = re.compile(r'[a-zA-Z]')  # 字母
        greek = [ '×', 'í', 'ó', 'α','β', 'γ', 'μ', 'π',] # 希腊字母
        rome = ['Ⅰ', 'Ⅱ', 'Ⅲ', 'Ⅳ', 'Ⅴ', 'Ⅵ', 'Ⅷ','ⅳ', 'ⅴ', 'ⅹ',] # 罗马字母
        e2c_dict = {'.':'。', '"':'”',':':'：','，':',','(':'（',')':'）'}
        punch = [' ',"", '!', '#', '%', "'", '*', '+', '-', '/', ';', '<', '=', '>', '?','[', '\\', ']', '^', '_', '`',
                 '{', '|', '}', '~', '°', '±', '·', 'º', '—', '‘', '’', '′','″', '℃', '℅',  '→', '∕', '≈', '≤', '≥',
                 '①','②', '③', '④', '△', '、', '〈', '〉', '〔', '〕','㎎', '㎜', '㎝', '一','﹪', '\ufeff', '！', '＂',
                 '％', '＋', '－', '．', '／','；', '＜', '＝', '＞', '？','～','﹥']
        digit1 = ['０', '１', '２', '３', '４', '５', '６', '７', '８', '９',]
        alpha1 = [ 'Ａ', 'Ｂ', 'Ｃ', 'Ｅ', 'Ｇ', 'Ｈ', 'Ｉ', 'Ｊ', 'Ｌ', 'Ｍ', 'Ｏ', 'Ｐ', 'Ｒ', 'Ｔ', 'Ｕ', 'Ｘ', 'Ｚ', 'ａ',
                   'ｃ', 'ｄ', 'ｇ', 'ｌ', 'ｍ', 'ｏ', 'ｐ', 'ｑ', 'ｕ', 'ｚ']

        for i in range(len(input_list)):
            if input_list[i] != '\n':
                word = input_list[i].split('\t')[0]
                if re.match(digit,word) != None or word in digit1:
                    r.append(word + '\t' + '1' + '\t' + input_list[i].split('\t')[-1])
                elif re.match(alpha, word) != None or word in alpha1:
                    r.append(word + '\t' + 'a' + '\t' + input_list[i].split('\t')[-1])
                elif word in greek:
                    r.append(word + '\t' + 'α' + '\t' + input_list[i].split('\t')[-1])
                elif word in rome:
                    r.append(word + '\t' + 'Ⅰ' + '\t' + input_list[i].split('\t')[-1])
                elif word in e2c_dict.keys():
                    if word == '。':
                        r.append(word + '\t' + e2c_dict[word] + '\t' + input_list[i].split('\t')[-1] + '\n')
                    else:
                        r.append(word + '\t' + e2c_dict[word] + '\t' + input_list[i].split('\t')[-1])
                elif word == '。':
                    r.append(word + '\t' + word + '\t' + input_list[i].split('\t')[-1] + '\n')
                elif word not in punch:
                    r.append(word + '\t' + word + '\t' + input_list[i].split('\t')[-1])
                else:
                    continue
            else:
                r.append('\n')
        return r

    all_rawdata = []
    for datafile in all_datafile[:]:
        # print(datafile)
        # datafile = all_datafile[0]
        raw_datafile = datafile[:-4]
        raw_data_list = list(load_data(raw_datafile))
        ent_data_list = load_data(datafile).split('\n')
        # print(raw_data_list)
        # print(ent_data_list)
        for ent_data in ent_data_list:
            if len(ent_data.split()) > 2:
                ent = ent_data.split('=')[1][:-2]
                pos = ent_data.split('=')[2][:-2]
                if len(ent_data.split('=')) == 5:
                    type = ent_data.split('=')[3][:-2]
                else:
                    type = ent_data.split('=')[3]
                # print(ent,pos,type)
                pos = pos.split(':')
                pos_list = list(range(int(pos[0]),int(pos[1])))
                pos_tag = get_pos(pos_list, type)
                for i in range(len(pos_list)):
                    if raw_data_list[pos_list[i]].split('\t')[0] == '\n' or raw_data_list[pos_list[i]].split('\t')[0] \
                            == '\t':
                        continue
                    else:
                        raw_data_list[pos_list[i]] = raw_data_list[pos_list[i]].split('\t')[0] + '\t' + pos_tag[i]
        temp = []
        temp.append('###' + raw_datafile.split('\\')[-1] + '\n')
        raw_data_list = adjust(raw_data_list)
        raw_data_list = temp + replace_punch(raw_data_list)
        all_rawdata.append(raw_data_list)
        # for i in range(len(raw_data_list)):
        #     print(raw_data_list[i])
    with open(output_file,'w',encoding='UTF-8') as fw:
        for line in all_rawdata:
            for data in line:
                fw.write(data)
                if data != '\n':
                    fw.write('\n')

# 分句
def tagsplit(inputfile,outputfile,max_len = 70):
    def adjust_cut_word(cut_word_list):
        r = []
        r1 =  []
        digit = re.compile(r'[0-9]')  # 数字
        alpha = re.compile(r'[a-zA-Z]')  # 字母
        for cw in cut_word_list:
            if len(cw) > 1:
                if cw[-1] == '。' or re.match(digit,cw[-1]) != None or re.match(alpha,cw[-1]) != None:
                    r.append(cw[:-1])
                    r.append(cw[-1])
                else:
                    r.append(cw)
            else:
                r.append(cw)
        for cwr in r:
            if len(cwr) > 1:
                if cwr[0] == '。':
                    r1.append(cwr[0])
                    r1.append(cwr[1:])
                elif re.match(digit,cwr[0]) != None and re.match(digit,cwr[1]) == None and cwr[1] != '。':
                    r1.append(cwr[0])
                    r1.append(cwr[1:])
                elif re.match(alpha, cwr[0]) != None and re.match(alpha, cwr[1]) == None:
                    r1.append(cwr[0])
                    r1.append(cwr[1:])
                else:
                    r1.append(cwr)
            else:
                r1.append(cwr)
        # print(r1)
        return r1

    def add_feature(word_list,r_word_list,s):
        word_str = '' # 词本身
        r_word_str = '' # 替代词
        words_str = ''
        e_word_str = '' # 边界
        for i in range(len(word_list)):
            word_str += word_list[i] + ' '
            r_word_str += r_word_list[i] + ' '
            # words_str += word_list[i].split('/.')[0]
            words_str += r_word_list[i].split('/.')[0]
        # print(words_str)
        cut_words = '\t'.join(jieba.cut(words_str,cut_all=False))
        words_str = ''
        for t in adjust_cut_word(cut_words.split('\t')):
            for j in range(len(t)):
                words_str += t + ' '
                if j ==  0:
                    e_word_str += '1 '
                elif j == len(t)-1:
                    e_word_str += '3 '
                else:
                    e_word_str += '2 '
        # print(word_str)
        # print(r_word_str)
        # print(words_str)
        # print(e_word_str)
        if len(word_str.split(' ')) != len(words_str.split(' ')):
            print(s)
            print(word_list)
            print(word_str)
            print(word_str)
            print(words_str)
        return {'wordtag':word_str[:-1], 'replace':r_word_str[:-1],
                'wordspos':words_str[:-1], 'wordedge':e_word_str[:-1]}

    sentences = []
    sentence = ''
    r_sentence = ''
    continue_flag = False
    with open(inputfile,'r',encoding='utf-8') as f:
        for line in f:
            if continue_flag == True:
                continue_flag = False
            else:
                # print(line)
                if line[:3] == '###':
                    # print(line[:-1])
                    continue_flag = True
                elif line == '\n':
                    if (len(sentence.split())) > max_len:
                        for i in range(math.ceil(len(sentence.split())/max_len)):
                            sentences.append(add_feature(sentence[:-1].split()[i*max_len:(i+1)*max_len],
                                                         r_sentence[:-1].split()[i*max_len:(i+1)*max_len],sentence))
                    elif sentence == '':
                        continue
                    else:
                        sentences.append(add_feature(sentence[:-1].split(),r_sentence[:-1].split(),sentence))
                    sentence = ''
                    r_sentence = ''
                else:
                    # print(line)
                    # if len(line[:-1].split('\t')) != 3:
                    # print(list(line[:-1]))
                    #     continue
                    word,r_word,pos = line[:-1].split('\t')
                    if word != '　':
                        sentence += word + '/.' + pos + ' '
                        r_sentence += r_word + '/.' + pos + ' '
    f.close()
    num_line = 0
    for s in sentences:
        num_line += len(s['wordtag'].split())
    print(num_line)
    with open(outputfile,'w',encoding='utf-8') as fs:
        for s in sentences:
            json.dump(s,fs,ensure_ascii=False)
            fs.writelines('\n')
    fs.close()

def tagsplit1(inputfile,outputfile,max_len = 70):
    def list2str(l_list):
        # print(l_list)
        r_str = ''
        for l in l_list:
            r_str += l + ' '
        return r_str[:-1]
    sentences = []
    sentence = ''
    continue_flag = False

    with open(inputfile,'r',encoding='utf-8') as f:
        punch = ['!','.','?','。','','','']
        for line in f:
            if continue_flag == True:
                continue_flag = False
            else:
                # print(line)
                if line[:3] == '###':
                    continue_flag = True
                elif line == '\n' or line[:-1] in punch:
                    if (len(sentence.split())) > max_len:
                        for i in range(math.ceil(len(sentence.split())/max_len)):
                            sentences.append({'wordtag': list2str(sentence.split()[i*max_len:(i+1) * max_len])})
                    else:
                        sentences.append({'wordtag':sentence})
                    sentence = ''
                else:
                    # print(line)
                    word,pos = line[:-1].split('\t')
                    sentence += word + '/.' + pos + ' '
    f.close()
    num_line = 0
    for s in sentences:
        num_line += len(s['wordtag'].split())
    print(num_line)
    with open(outputfile,'w',encoding='utf-8') as fs:
        for s in sentences:
            json.dump(s,fs,ensure_ascii=False)
            fs.writelines('\n')
    fs.close()

def get_word_dict(input_file,worddict_file,word_pos_file):
    def flatten(x):
        result = []
        for el in x:
            if isinstance(x, collections.Iterable) and not isinstance(el, str):
                result.extend(flatten(el))
            else:
                result.append(el)
        return result
    datas = []
    labels = []
    tags = set()
    cut_words = set()
    with open(input_file, 'r', encoding='utf-8') as input_data:
        for l in input_data.readlines():
            single_line = json.loads(l)
            # line = l['wordtag']
            single_line_char = single_line['replace']
            line = single_line_char.split(' ')
            single_line_words = single_line['wordspos']
            for j in single_line_words.split(' '):
                cut_words.add(j)
            linedata = []
            linelabel = []
            numNotO = 0
            for word in line:
                word = word.split('/.')
                if word[0] == '':
                    print(single_line)
                linedata.append(word[0])
                linelabel.append(word[1])
                tags.add(word[1])
                if word[1] != 'O':
                    numNotO += 1
            if numNotO != 0:
                datas.append(linedata)
                labels.append(linelabel)
    input_data.close()
    print(len(datas))
    print(len(labels))
    print(tags)
    # 统计词频
    all_words = flatten(datas)
    all_words += list(cut_words)
    all_words.append('')
    sr_allwords = pd.Series(all_words)
    sr_allwords = sr_allwords.value_counts()
    set_words = sr_allwords.index
    set_ids = list(range(1, len(set_words) + 1))
    # tags.remove('')
    tags = sorted(tags)
    set_words = sorted(set_words)
    tags = [i for i in tags]
    tag_ids = list(range(1,len(tags)+1))
    word2id = pd.Series(set_ids, index=set_words)
    tag2id = pd.Series(tag_ids, index=tags)
    word2id["unknow"] = len(word2id) + 1
    with open(worddict_file,'w', encoding='utf-8') as fs:
        fs.write(json.dumps(word2id.to_dict(),ensure_ascii=False,indent=4))
    fs.close()
    with open(word_pos_file,'w', encoding='utf-8') as fs1:
        fs1.write(json.dumps(tag2id.to_dict(),ensure_ascii=False,indent=4))
    fs1.close()

def get_word(input_file):
    all_sentences = []
    senence = ''
    with open(input_file,'r',encoding='utf-8') as f:
        for line in f:
            senence += line[:-1]

def load_data1(input_file,need_o):
    datas = []
    all_repalced = []
    all_words = []
    all_edge = []
    labels = []
    line_num = 0
    with open(input_file, 'r',encoding='utf-8') as input_data:
        # for l in input_data.readlines():
        for l in input_data:
            # if line_num < 1000:
            #     line_num += 1
            # else:
            #     break
            l = json.loads(l)
            line = l['wordtag'].split()
            line_replace = l['replace'].split()
            line_words = l['wordspos'].split()
            line_edge = l['wordedge'].split()
            linedata = []
            linedreplace = []
            linedword = []
            linededge = []
            linelabel = []
            numNotO = 0
            # print(line)
            # print(line_words)
            for i in range(len(line)):
                word = line[i].split('/.')
                replace = line_replace[i].split('/.')
                linedata.append(word[0])
                linelabel.append(word[1])
                linedreplace.append(replace[0])
                linedword.append(line_words[i])
                linededge.append(line_edge[i])
                if word[1] != 'O':
                    numNotO += 1
            if need_o == False:
                if numNotO != 0:
                    datas.append(linedata)
                    labels.append(linelabel)
                    all_repalced.append(linedreplace)
                    all_words.append(linedword)
                    all_edge.append(linededge)
            else:
                datas.append(linedata)
                labels.append(linelabel)
                all_repalced.append(linedreplace)
                all_words.append(linedword)
                all_edge.append(linededge)
                # tags.add(word[1])
                # if word[1] != 'O':
                #     numNotO += 1
            # if numNotO != 0:
            #     datas.append(linedata)
            #     labels.append(linelabel)
    input_data.close()
    return datas,all_repalced,all_words,all_edge,labels

def data2pkl(input_file1,word2id_file,tag2id_file,output_file,max_len=70):
    def flat_gen(x):
        def iselement(e):
            return not (isinstance(e, collections.Iterable) and not isinstance(e, str))

        for el in x:
            if iselement(el):
                yield el
            else:
                yield from flat_gen(el)

    datas_train = []
    labels_train = []
    tags = set()
    tags.add('')
    line_num = 0
    all_word2id = {}
    all_tag2id = {}
    all_line = []
    sentences = []
    with open(word2id_file, 'r', encoding='utf-8') as fv:
        all_word2id = json.load(fv)
    fv.close()
    with open(tag2id_file, 'r', encoding='utf-8') as ft:
        all_tag2id = json.load(ft)
    ft.close()
    datas_train, replace_train, words_train, edge_train, labels_train = load_data1(input_file1,need_o=False)
    print(len(datas_train))
    print(len(labels_train))
    print(len(edge_train))

    all_words = [k for k in all_word2id.keys()]
    set_words = all_words
    # set_ids = range(1, len(set_words) + 1)
    set_ids = range(0, len(set_words))
    tags = [k for k in all_tag2id.keys()]
    tags =[''] + tags
    tag_ids = range(len(tags))
    print(all_words)
    print(tags)

    word2id = pd.Series(set_ids, index=set_words)
    id2word = pd.Series(set_words, index=set_ids)
    tag2id = pd.Series(tag_ids, index=tags)
    id2tag = pd.Series(tags, index=tag_ids)
    # word2id["unknow"] = len(word2id)+1
    # id2word[len(word2id)+1] = "unknow"
    print(tag2id)
    max_len = 70
    print(word2id)
    print(tag2id)

    def X_padding(char,word,edge):
        r = []
        for j in range(len(char)):
            chars = []
            words = []
            edges = []
            for i in range(len(char[j])):
                if char[j][i] not in word2id.keys():
                    chars.append(word2id["unknow"])
                else:
                    chars.append(word2id[char[j][i]])
                if word[j][i] not in word2id.keys():
                    words.append(word2id["unknow"])
                else:
                    words.append(word2id[word[j][i]])
                edges.append(edge[j][i])
            chars.extend([0] * (max_len - len(chars)))
            words.extend([0] * (max_len - len(words)))
            edges.extend([0] * (max_len - len(edges)))
            r.append(chars + words + edges)
            # print(r[j])
        return r

    def y_padding(tags):
        ids = []
        for tag in tags:
            ids.append(tag2id[tag])
        # ids = list(tag2id[tags])
        if len(ids) >= max_len:
            return ids[:max_len]
        ids.extend([0] * (max_len - len(ids)))
        return ids

    # df_data_train = pd.DataFrame({'words': datas_train, 'tags': labels_train}, index=range(len(datas_train)))
    # df_data_train['x'] = df_data_train['words'].apply(X_padding)
    # df_data_train['y'] = df_data_train['tags'].apply(y_padding)

    df_data_train = pd.DataFrame({'datas':datas_train,'replace':replace_train,'words':words_train,'edge':edge_train,
                                  'tags': labels_train}, index=range(len(labels_train)))
    df_data_train['x'] = X_padding(replace_train,words_train,edge_train)
    df_data_train['y'] = df_data_train['tags'].apply(y_padding)
    print(df_data_train['x'])
    print(df_data_train['y'])
    x = np.asarray(list(df_data_train['x'].values)).astype('int64')
    y = np.asarray(list(df_data_train['y'].values))

    x_train, x_valid, y_train, y_valid = train_test_split(x, y, test_size=0.45, random_state=43)
    print(type(x), x[0])
    print(len(x))
    print(len(y))
    print(x_train.shape)

    with open(output_file, 'wb') as outp:
        pickle.dump(word2id, outp)
        pickle.dump(id2word, outp)
        pickle.dump(tag2id, outp)
        pickle.dump(id2tag, outp)
        pickle.dump(x_train, outp)
        pickle.dump(y_train, outp)
        pickle.dump(x_valid, outp)
        pickle.dump(y_valid, outp)
    print('** Finished saving the data.')


if __name__ == '__main__':
    # 获取所有病历文件名
    # all_datafile = getListFiles('data/已校对已标注-992')
    # all_datafile_train = getListFiles('data/云知声5000')
    # all_datafile = all_datafile + all_datafile_train
    # 读取所有病历文件
    # get_all_data(all_datafile,'raw_data.txt')
    # 分句
    # tagsplit('raw_data.txt','sentences.json')
    # 获取词典，标签
    # get_word_dict('sentences.json','word_dict.json','word_pos.json')
    # 生成训练数据
    data2pkl('sentences.json', 'word_dict.json', 'word_pos.json', 'train.pkl')
    # load_data('sentences.json',True)
    # 获取词向量
    get_all_word('data/sgns.target.word-character.char1-2.dynwin5.thr10.neg5.dim300.iter5','word_dict.json',
                 'words_vec.json')

    # get_all_data(['data/已校对已标注-992/484H913280discharge3.xml.ent'],'r.txt')