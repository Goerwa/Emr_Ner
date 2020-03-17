import xgboost as xgb
from xgboost import train,DMatrix
from sklearn.model_selection import train_test_split
from sklearn.datasets import load_svmlight_file
from pyltp import Segmentor,NamedEntityRecognizer,Postagger,Parser
from util import BM25
# import synonyms
import math

class L2R():
    def __init__(self):
        self.stop_word = self.load_stopwords('stopwords.txt')
        self.stop_word.append('"')
        # self.stop_word = []
        self.N = 5352

    def load_stopwords(self,filename):
        stop_word = []
        with open(filename, 'r') as f:
            for i in f:
                stop_word.append(i[:-1])
        return stop_word
    def load_data(self,filename_doc,filename_train):
        def list2str(l):
            lstr = '['
            for i in l:
                lstr += i +', '
            lstr = lstr[:-2]
            lstr += ']'
            return lstr
        q_dict = {} # 问题
        t_dict= {} # 文档
        f = open(filename_doc,'r')
        for line in f:
            line_list = line.replace('{"pid": ', 'cut_f').replace('"document": [', 'cut_f').replace(']}\n', 'cut_f')\
                .split('cut_f')
            t_dict[line_list[1].replace(', ','')] = []
            t_dict[line_list[1].replace(', ','')] = line_list[2:3]
            # print(line_list)
            # print(line_list[0],t_dict[line_list[0]])
        ft = open(filename_train,'r')
        for line in ft:
            # print(line)  q,pid,ast,a
            line_list = line.replace('{"question": ', '').replace('"pid": ', 'cut_f').replace('"answer_sentence": [', 'cut_f')\
                .replace('], "answer": ', 'cut_f').replace(' "qid": ', 'cut_f').replace('}','').split('cut_f')
            # print(line_list)
            if len(line_list) != 5:
                print(line)
                print(line_list)
            q_dict[line_list[4][:-1]] = []
            q_dict[line_list[4][:-1]] = line_list[:-1]
        group = []
        data_dict = {}
        q_n = len(q_dict.keys())
        q_k = list(q_dict.keys())
        for i in range(q_n):
            data_pos = []
            q_num = q_k[i] # 问题序号
            # print(q_dict[q_num])
            t_list = t_dict[q_dict[q_num][1].replace(', ','')] # 问题的文档
            t_list[-1] = t_list[-1][:-1]
            # print(t_list)
            # print(t_list[0].split('",'))
            ts_list = t_list[0].split('", ')
            for t in ts_list:
                t_line = t + '"'
                if t_line == q_dict[q_num][2]:
                    data_pos.append('1\t' + t_line)
                else:
                    data_pos.append('0\t' + t_line)
            group.append(len(ts_list))
            # print(q_num + '\t' + q_dict[q_num][0].replace(',',''))
            data_dict[q_num + '\t' + q_dict[q_num][0].replace(',','')] = data_pos

        with open('AC/data_pos.txt','w') as fd:
            for k in data_dict.keys():
                fd.write(k)
                fd.write('\n')
                fd.write(list2str(data_dict[k]))
                fd.write('\n')
        with open('AC/group.txt','w') as fg:
            for i in group:
                fg.write(str(i))
                fg.write('\n')

    def  get_vec(self,filename):
        def list2str(l):
            r_str = ' '
            for i in l:
                r_str += str(int(i.split(':')[0]) + 10)  +':'+ i.split(':')[1]+ ' '
            return r_str
        def adjust_list(l,words):
            l.insert(0,'"')
            n = len(l)
            cut_list = []
            cut_list.append(l[0])
            for i in range(1,n):
                a = ''
                if cut_list[-1]+l[i] in words:
                    cut_list[-1] = cut_list[-1]+l[i]
                    continue
                while l[i] not in words and len(l[i]) > 1:
                    a = l[i][-1] + a
                    l[i] = l[i][:-1]
                if l[i] in words:
                    cut_list.append(l[i])
                if a != '':
                    if a in words:
                        cut_list.append(a)
            # print(cut_list)
            return cut_list
        def get_tf_idf(data,syn_dict):
            all_words = []
            dict_freq = {}  # 词频
            dict_doc = {}  # 文档数量
            all_num = 0
            words = []
            stop_words = self.stop_word
            tf_idf = {}
            all_sent = []
            for k in data.keys():
                # print(data[k])
                line_list = (data[k].replace('[','').replace(']','').split('", '))
                line_list[-1] = line_list[-1][:-1]
                for i in line_list:
                    line_i = i.split('\t')[1] + '"'
                    all_sent.append(line_i)
                    cut_line = '\t'.join(segmentor.segment(line_i))
                    words_list = cut_line.split('\t')  # 分词
                    is_adddoc = []
                    for word in words_list:
                        if word not in stop_words:
                            if word not in dict_freq.keys():
                                dict_freq[word] = 1
                            else:
                                dict_freq[word] += 1
                            if word not in is_adddoc: # 词出现的问题树
                                if word not in dict_doc.keys():
                                    dict_doc[word] = 1
                                else:
                                    dict_doc[word] += 1
                                is_adddoc.append(word)
            for k in dict_freq.keys():
                idf = math.log(self.N / dict_doc[k])
                tf_idf[k] = 1 + math.log(dict_freq[k])
                tf_idf[k] *= idf
            with open('AC/tf-idf.txt','w') as fr:
                for k in tf_idf.keys():
                    fr.write(k)
                    fr.write('\t')
                    fr.write(str(tf_idf[k]))
                    fr.write('\n')

        def get_feature_vec(q_list,a_list):
            feature  = []
            q_den = 1
            for word in q_list:
                q_den += tf_idf[word]**2
            for sa_list in a_list:
                vec_f = 0
                a_den = 1
                for wa in sa_list:
                    a_den += tf_idf[wa]**2
                    if wa in set(q_list):
                        vec_f += tf_idf[wa]
                den = (q_den * a_den)**0.5
                vec_f /= den
                feature.append(round(vec_f*1000,2))
            return feature

        def get_feature_bm25(q_list,a_list,all_words):
            all_wordsl = list(all_words)
            # print(all_wordsl[174017])
            feature = []
            s = BM25(a_list,all_wordsl)
            # s.simall(q_list)
            # print(s.simall(q_list))
            for i in s.simall(q_list):
                feature.append(i)
            # print(feature)
            return feature

        # def get_feature_sim(q_list,a_list):
        #     feature = []
        #     str_q = ''
        #     for q in q_list:
        #         str_q  = str_q + ' ' + q
        #     for as_list in a_list:
        #         str_sa = ''
        #         for a in as_list:
        #             str_sa = str_sa + ' ' + a
        #         # print(q_list,as_list,synonyms.compare(q_list,as_list))
        #         if len(str_sa) < 1 or len(str_q) < 1:
        #             feature.append(0.0)
        #         else:
        #             feature.append(round(synonyms.compare(str_q, str_sa,seg=False)*1000,3))
        #     return feature

        def get_feature_same(q,a_list):
            r = []
            for sa_list in a_list:
                n = 0
                for a in sa_list:
                    if a in q:
                        n += 1
                r.append(n)
            return r
        def get_DA(words_list):
            postags = postagger.postag(words_list)  # 词性标注
            pos_line = '\t'.join(postags)
            pos_list = pos_line.split('\t')
            # print(pos_list)
            # print(pos_list)
            if pos_list == ['']:
                return []
            netags = recognizer.recognize(words_list, pos_list)  # 命名实体识别
            ner_line = '\t'.join(netags)
            ner_list = ner_line.split('\t')

            arcs = parser.parse(words_list, pos_list)  # 句法分析
            arcs_line = "\t".join("%d %s" % (arc.head, arc.relation) for arc in arcs)
            arcs_list = arcs_line.split('\t')
            r = []
            rsyn = []
            for i in range(len(arcs_list)):
                # print(words_list[int(arcs_list[i][0])-1] + '_' + words_list[i],arcs_list[i][0])
                if pos_list[i][0]  in set({'n','v','a'}):
                    r.append(words_list[int(arcs_list[i][0]) - 1] + '_' + words_list[i])
            return r
        def get_feature_DA(q_list,a_list):
            feature = []
            feature_q = get_DA(q_list)
            feature_a = []
            for sa_list in a_list:
                feature_sa = get_DA(sa_list)
                # print(feature_q)
                # print(feature_sa)
                score = 0.0
                n = 0
                for sa in feature_sa:
                    for q in feature_q:
                        n += 1
                        # print(sa,q)
                        if sa == q:
                            score += 1
                        elif sa.split('_')[0] == q.split('_')[0]:
                            score += 0.5
                        elif sa.split('_')[1] == q.split('_')[1]:
                            score += 0.5
                        else:
                            n -= 1
                # print(score,n)
                if score > 0.4:
                    feature.append(score/n)
                else:
                    feature.append(0.0)
            # print(feature)
            return feature

        fd = open(filename, 'r')
        data = []
        data_dict = {}
        for line in fd:
            # print(line[:-1])
            # print(line[:-1].split('\t')[1])
            data.append(line[:-1])
        for i in range(0,len(data),2):
            data_dict[data[i]] = data[i+1]

        segmentor = Segmentor()
        segmentor.load('cws.model')
        postagger = Postagger()  # 初始化实例
        postagger.load('pos.model')  # 加载模型
        recognizer = NamedEntityRecognizer()  # 初始化实例
        recognizer.load('ner.model')  # 加载模型
        parser = Parser()
        parser.load('parser.model')
        tf_idf = {}
        all_word = []
        answer_vec = []
        fti = open('AC/tf-idf.txt', 'r')
        for line in fti:
            k = line[:-1].split('\t')[0]
            v = line[:-1].split('\t')[1]
            tf_idf[k] = round(float(v), 2)
            all_word.append(k)
        all_word = set(all_word)
        j = 0
        for k in data_dict.keys():
            # print(k,data_dict[k])
            # print(j)
            pos_a = []
            cut_line = '\t'.join(segmentor.segment(k.split('\t')[1][1:-1]))
            words_list = cut_line.split('\t')  # 分词
            words_list = adjust_list(words_list, all_word)
            q_list = []
            a_list = []
            for word in words_list:
                if word not in self.stop_word:
                    q_list.append(word)
            line_list = (data_dict[k].replace('[', '').replace(']', '').split('", '))
            line_list[-1] = line_list[-1][:-1]
            for i in line_list:
                sa_list = []
                line_i = i.split('\t')[1] + '"'
                i_n = i.split('\t')[0]
                pos_a.append(i_n)
                cut_line = '\t'.join(segmentor.segment(line_i[1:-1]))
                words_list = cut_line.split('\t')  # 分词
                words_list = adjust_list(words_list, all_word)
                for word in words_list:
                    if word not in self.stop_word:
                        sa_list.append(word)
                a_list.append(sa_list)
            # print(q_list)
            # print(a_list)
            feature_same = get_feature_same(k.split('\t')[1][1:-1],a_list)
            feature_vec = get_feature_vec(q_list,a_list)
            feature_bm25 = get_feature_bm25(q_list,a_list,all_word)
            # feature_sim = get_feature_sim(q_list, a_list)
            feature_DA = get_feature_DA(q_list,a_list)

            for ni in range(len(pos_a)):
                answer_vec.append(pos_a[ni] + ' 1:' + str(feature_vec[ni]) + ' 2:' + str(feature_DA[ni]) + ' 3:' +
                                  str(feature_same[ni]) + list2str(feature_bm25[ni]))
            j += 1
            if j % 500 == 0:
                print(j)
            # if j == 5:
            #     break
        with open('AC/train.txt', 'w') as fw:
            for avec in answer_vec:
                fw.write(avec)
                fw.write('\n')

    def get_test(self):
        def adjust(l):
            c = [0,1,2,3,4]
            r_l = []
            for i in range(len(l)):
                i_l = l[i].split()
                lstr = ''
                for i in range(len(i_l)):
                    if i in c:
                        # if i == 1:
                        #     lstr += i_l[i][0:2] + str(float(i_l[i][2:]) * 100) + ' '
                        # elif i == 2:
                        #     lstr += i_l[i][0:2] + str(float(i_l[i][2:])) + ' '
                        # elif i == 3:
                        #     lstr += i_l[i][0:2] + str(float(i_l[i][2:])) + ' '
                        # else:
                        lstr += i_l[i] + ' '
                r_l.append(lstr)
            return r_l

        train_data = []
        test_data = []
        train_group = []
        test_group = []
        ft = open('AC/train.txt','r')
        for line in ft:
            train_data.append(line[:-1])
        fg = open('AC/group.txt', 'r')
        for line in fg:
            train_group.append(line[:-1])
        tg = int(len(train_group) * 4/5)
        tg_num = 0
        test_group = train_group[tg:]
        train_group = train_group[:tg]
        for i in train_group:
            tg_num += int(i)
        test_data = train_data[tg_num:]
        train_data = train_data[:tg_num]
        # test_data = adjust(test_data)
        # train_data = adjust(train_data)
        with open('l2r/train_group.txt','w') as ftrg:
            for i in train_group:
                ftrg.write(i)
                ftrg.write('\n')
        with open('l2r/test_group.txt','w') as fteg:
            for i in test_group:
                fteg.write(i)
                fteg.write('\n')
        with open('l2r/train.txt','w') as ftr:
            for i in train_data:
                ftr.write(i)
                ftr.write('\n')
        with open('l2r/test.txt','w') as fte:
            for i in test_data:
                fte.write(i)
                fte.write('\n')
    def get_precision(self):
        test_data = []
        test_group = []
        answer = []
        ft = open('l2r/test.txt', 'r')
        for line in ft:
            test_data.append(line[0])
        fg = open('l2r/test_group.txt', 'r')
        for line in fg:
            test_group.append(line[:-1])
        # fa = open('AC/answer.txt', 'r')
        fa = open('l2r/predictions.txt', 'r')
        for line in fa:
            answer.append(line[:-1])
        i = 0
        j= 0
        test_num = 0
        score = 0
        all_rank = []
        for g in test_group:
            answer_position = {}
            rank = []
            i = j
            j = j + int(g)
            test_list = test_data[i:j]
            answer_list = answer[i:j]
            if '1' not in test_list:
                continue
            test_num += 1
            # index = sorted(answer_list,reverse=True).index(answer_list[test_list.index('1')]) + 1
            # if index < 3:
            # score += 1 / index
            for na in range(len(answer_list)):
                answer_position[na] = answer_list[na]
            # print(sorted(answer_position.items(), key=lambda d: d[1], reverse=True))
            for pi in sorted(answer_position.items(), key=lambda d: d[1], reverse=True):
                rank.append(pi[0])
            # print(rank)
            # print(test_list.index('1'))
            all_rank.append(rank)
            score += 1 / (rank.index(test_list.index('1')) + 1)

        print(score)
        print('测试数量：',test_num)
        print('MRR：',score  / test_num)
        with open('l2r/result.txt','w') as f:
            for rank in all_rank:
                r_str = '['
                for r in rank:
                    r_str += str(r) + ', '
                r_str += ']\n'
                f.write(r_str)


def test():
    train_group = []
    test_group = []
    ftr = open('l2r/train_group.txt')
    for line in ftr:
        train_group.append(int(line[:-1]))
    fte = open('l2r/test_group.txt')
    for line in fte:
        test_group.append(int(line[:-1]))
    print(train_group)
    print(test_group)
    x_train, y_train = load_svmlight_file('l2r/train.txt')
    x_test, y_test = load_svmlight_file('l2r/test.txt')
    # print(x_train)
    # print(y_train)

    train_dmatrix = DMatrix(x_train, y_train)
    test_dmatrix = DMatrix(x_test)

    train_dmatrix.set_group(train_group)

    params = {'objective': 'rank:pairwise', 'eta': 0.1, 'gamma': 1.0,
              'min_child_weight': 0.1, 'max_depth': 6}
    xgb_model = xgb.train(params, train_dmatrix, num_boost_round=30)
    pred = xgb_model.predict(test_dmatrix)
    r_l = pred.tolist()
    with open('AC/answer.txt','w') as fa:
        for i in r_l:
            fa.write(str(i))
            fa.write('\n')

if __name__ == "__main__":
    my_l2r = L2R()
    # my_l2r.load_data('data/passages_multi_sentences.json','data/train.json')
    # my_l2r.get_vec('AC/data_pos.txt')
    # my_l2r.get_vec('AC/all_sentence.txt')
    my_l2r.get_test()
    test()
    my_l2r.get_precision()