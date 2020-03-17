import jieba
import jieba.analyse
from pyltp import Segmentor,NamedEntityRecognizer,Postagger,Parser,SementicRoleLabeller
from liblin.liblinearutil import *
from liblin.commonutil import *
# from libsvm.svmutil import  *
# from libsvm.commonutil import *
# from libsvm.svm import *
from util import is_estr,is_num
from metric import *
class QA:
    def __init__(self):
        # self.stop_word = self.load_stopwords('stopwords.txt')
        # self.stop_word.append('"')
        self.stop_word = ['"']
        self.data = self.load_data('data/train.json')
        self.pos = ['：','《','》','【','】','[',']','〈','〉','-','（','）','.','/','—','%','～']
        # self.train_num = 5352-1200
        # self.test_num = 1200
        self.train_num = 80
        self.test_num = 20
        # self.group = self.load_qa_group('AC/qa_group.txt')

    def load_stopwords(self,filename):
        stop_word = []
        with open(filename, 'r') as f:
            for i in f:
                stop_word.append(i[:-1])
        return stop_word

    def load_qa_group(self,filename):
        group = {}
        with open(filename, 'r') as f:
            for i in f:
                group[i.split()[0]] = i.split()[1]
        return group

    def load_data(self,filename):
        fc = open('qc_answer_M.txt','r')
        type_list = []
        for line in fc:
            type_list.append(line.split()[0])

        f = open(filename,'r')
        data = {}
        i = -1
        for line in f:
            i += 1
            line_list = line.replace('{"question": ', '').replace('"pid": ', 'cut_f').replace('"answer_sentence": [',
                                                                                              'cut_f').replace(
                '], "answer": ', 'cut_f').replace(' "qid": ', 'cut_f').replace('}', '').split('cut_f')
            line_list[4] = line_list[4][:-1]
            data[line_list[4]] = []
            for n in [0,2,3]:
                data[line_list[4]].append(line_list[n])
            data[line_list[4]].append(type_list[i])
            # print(data[line_list[4]])
            # print(line_list[4],data[line_list[4]])
        return data
    def get_ner(self):

        def is_in(word,word_set):
            for i in word_set:
                if i.find(i) != -1:
                    return True
            return False
        def get_feature(type,pos):
            r_str = ''
            r_str += str(100 + all_types.index(pos + '_' + type)) + ':1 '
            r_str += str(100 + all_types.index(pos)) + ':1 '
            return r_str

        def get_answer_pos(l, answer):
            r = [0 for n in range(len(l))]
            # print(answer[1:-2])
            r_str = ''
            i = 0
            while r_str != answer[1:-2] and i < len(l):
                # print(r_str)
                if l[i] in answer:
                    r_str += l[i]
                    r[i] = 1
                else:
                    r_str = ''
                    for j in range(i):
                        r[j] = 0
                i += 1
            return r


        def adjust_list(l,words):
            l.insert(0,'"')
            n = len(l)
            cut_list = []
            cut_list.append(l[0])
            for i in range(1,n):
                a = ''
                b = l[i]
                if cut_list[-1]+l[i] in words:
                    cut_list[-1] = cut_list[-1]+l[i]
                    continue
                else:
                    cut_list.append(l[i])
                # while l[i] not in words and len(l[i]) > 1:
                #     a = l[i][-1] + a
                #     l[i] = l[i][:-1]
                # print(b)
                # if l[i] in words:
                #     cut_list.append(l[i])
                # if a != '':
                #     if a in words:
                #         cut_list.append(a)
                # else:
                #     cut_list.append(b)
            # print(cut_list)
            cut_list = cut_list[1:]
            return cut_list
        segmentor = Segmentor()
        segmentor.load('cws.model')
        postagger = Postagger()  # 初始化实例
        postagger.load('pos.model')  # 加载模型
        recognizer = NamedEntityRecognizer()  # 初始化实例
        recognizer.load('ner.model')  # 加载模型
        parser = Parser()
        parser.load('parser.model')
        labeller = SementicRoleLabeller()  # 初始化实例
        labeller.load('pisrl.model')  # 加载模型
        all_types = []
        ftype = open('AC/qa_types1.txt', 'r')
        for line in ftype:
            all_types.append(line[:-1])
        fti = open('AC/tf-idf.txt', 'r')
        all_word = []
        for line in fti:
            k = line[:-1].split('\t')[0]
            all_word.append(k)
        all_word = set(all_word)
        j = 0
        word_feature = []
        word_group = []
        word_all = []
        for k in self.data.keys():
            q_list = []
            q_pos_list = []
            q_sbv = []
            q_vob = []
            q_v = []
            q_att1 = []
            q_att2 = []
            cut_line = '\t'.join(segmentor.segment(self.data[k][0]))
            word_list = cut_line.split('\t')  # 分词
            # print(word_list)
            for i in word_list:
                if i not in self.stop_word:
                    q_list.append(i)
            q_list = adjust_list(q_list, all_word)
            postags = postagger.postag(q_list)  # 词性标注
            pos_line = '\t'.join(postags)
            q_pos_list = pos_line.split('\t')
            netags = recognizer.recognize(q_list, postags)  # 命名实体识别
            ner_line = '\t'.join(netags)
            ner_list = ner_line.split('\t')
            # print(ner_list)
            q_ner = []
            ner_str = ''
            for nr in range(len(ner_list)):
                if ner_list[nr][0] != 'O':
                    if ner_list[nr][0] == 'S' or ner_list[nr][0] == 'E':
                        ner_str += q_list[nr]
                        q_ner.append(ner_str)
                        ner_str = ''
                    else:
                        ner_str += q_list[nr]
            arcs = parser.parse(q_list, q_pos_list)  # 句法分析
            arcs_line = "\t".join("%d %s" % (arc.head, arc.relation) for arc in arcs)
            arcs_list = arcs_line.split('\t')
            roles = labeller.label(q_list, postags, arcs)
            # print(q_list)
            for n in range(len(arcs_list)):
                # print(q_list[int(arcs_list[n].split()[0])-1],q_list[n],arcs_list[n].split()[1])
                if arcs_list[n].split()[1] == 'SBV':
                    q_v.append(q_list[int(arcs_list[n].split()[0]) - 1])
                    q_sbv.append(q_list[n])
                elif arcs_list[n].split()[1] == 'VOB':
                    q_v.append(q_list[int(arcs_list[n].split()[0]) - 1])
                    q_vob.append(q_list[n])
                elif arcs_list[n].split()[1] == 'IOB':
                    q_v.append(q_list[int(arcs_list[n].split()[0]) - 1])
                    q_vob.append(q_list[n])
                elif arcs_list[n].split()[1] == 'FOB':
                    q_vob.append(q_list[int(arcs_list[n].split()[0]) - 1])
                    q_v.append(q_list[n])
                elif arcs_list[n].split()[1] == 'ATT':
                    q_att1.append(q_list[int(arcs_list[n].split()[0]) - 1])
                    q_att2.append(q_list[n])
                # print(q_list[int(arcs_list[n].split()[0]) - 1], q_list[n], arcs_list[n].split()[1])

            a_list = []
            a_pos_list = []
            cut_line = '\t'.join(segmentor.segment(self.data[k][1]))
            word_list = cut_line.split('\t')  # 分词
            # print(word_list)
            for i in word_list:
                if i not in self.stop_word:
                    a_list.append(i)
            a_list = adjust_list(a_list, all_word)
            postags = postagger.postag(a_list)  # 词性标注
            pos_line = '\t'.join(postags)
            a_pos_list = pos_line.split('\t')
            netags = recognizer.recognize(a_list, postags)  # 命名实体识别
            ner_line = '\t'.join(netags)
            ner_list = ner_line.split('\t')
            # print(self.data[k][0],self.data[k][2],self.data[k][-1])
            # print(q_list)
            # print(q_pos_list)
            # print(a_list)
            # print(a_pos_list)
            ner_type = ['O','S-Nh','S-Ni','S-Ns','B-Nh','B-Ni','B-Ns','I-Nh','I-Ni','I-Ns','E-Nh','E-Ni','E-Ns']
            # for i in range(len(a_list)):
            r_pos = get_answer_pos(a_list,self.data[k][2])
            for i in range(len(a_list)):
                str_f = ''
                # if a_list[i] == self.data[k][2]:
                #     str_f += '1 '
                # else:
                #     str_f += '0 '
                str_f += str(r_pos[i])  + ' '
                if a_list[i] in set(q_list):
                    str_f += '1:1 '
                if a_list[i] in set(q_ner):
                    str_f += '2:1 '
                if a_list[i] in set(q_sbv):
                    str_f += '3:1 '
                if a_list[i] in set(q_v):
                    str_f += '4:1 '
                if a_list[i] in set(q_vob):
                    str_f += '5:1 '
                if a_list[i] in set(q_att1):
                    str_f += '6:1 '
                if a_list[i] in set(q_att2):
                    str_f += '7:1 '
                if a_list[i] in set(self.pos):
                    str_f += '8:1 '
                if i > 1 and a_list[i-1] in set(self.pos):
                    str_f += '9:1 '
                str_f += get_feature(self.data[k][-1], a_pos_list[i])
                if len(word_feature) != 0:
                    last_word = word_feature[-1].split()
                    # str_f += str(500 + int(last_word[-2][:-2])) + ':1 '
                    if int(last_word[-1][:-2]) < 500:
                        str_f += str(500 + int(last_word[-1][:-2])) + ':1 '
                    else:
                        str_f += str(500 + int(last_word[-2][:-2])) + ':1 '
                else:
                    last_word = ''
                # str_f += str(9 +(ner_type.index(ner_list[i]))) + ':1 '
                # word_feature.append(a_list[i] + ' ' + str_f)
                word_feature.append(str_f)
                # word_all.append(str_f[0] + ' ' + a_list[i])
                word_all.append(a_list[i])
            # print(self.data[k])
            word_group.append(str(len(a_list)))
            j += 1
            if j == 100:
                break
            if j % 1000 == 0:
                print(j)
            #     break
        with open('AC/qa_train.txt','w') as f1:
            for wf in word_feature:
                f1.write(wf)
                f1.write('\n')
        with open('AC/qa_group.txt','w') as f2:
            for wg in word_group:
                f2.write(str(wg))
                f2.write('\n')
        with open('AC/qa_words.txt','w') as f3:
            for wa in word_all:
                f3.write(str(wa))
                f3.write('\n')
    def get_test(self):
        train_data = []
        test_data = []
        train_group = []
        test_group = []
        ft = open('AC/qa_train.txt', 'r')
        for line in ft:
            train_data.append(line[:-1])
        fg = open('AC/qa_group.txt', 'r')
        for line in fg:
            train_group.append(line[:-1])
        # for k in sorted(self.group.keys()):
        #     train_group.append(self.group[k])
        # trg = int(len(train_group) * 1 / 50)
        # teg = int(len(train_group) * 4 / 5)
        trg = self.train_num
        teg = self.test_num
        # print(teg)
        tg_num = 0
        te_num = 0
        test_group = train_group[-teg:]
        train_group = train_group[:trg]
        # print('tr',train_group)
        # print('te',test_group)
        for i in train_group:
            tg_num += int(i)
        for i in test_group:
            te_num += int(i)
        test_data = train_data[-te_num:]
        train_data = train_data[0:tg_num]
        # print('te',len(test_data))
        # print('tr',len(train_data))
        with open('AC/train_group.txt', 'w') as ftrg:
            for i in train_group:
                ftrg.write(i)
                ftrg.write('\n')
        with open('AC/test_group.txt', 'w') as fteg:
            for i in test_group:
                fteg.write(i)
                fteg.write('\n')
        with open('AC/qa_train1.txt', 'w') as ftr:
            for i in train_data:
                ftr.write(i)
                ftr.write('\n')
        with open('AC/qa_test1.txt', 'w') as fte:
            for i in test_data:
                fte.write(i)
                fte.write('\n')
    def get_r(self):
        f = open('AC/qa_train1.txt','r')
        right = []
        neg = []
        for line in f:
            if line[0] == '1':
                right.append(line[:-1])
            else:
                neg.append(line[:-1])
        with open('AC/qa_train2.txt','w') as fw:
            for l in right:
                fw.write(l)
                fw.write('\n')
            for i in range(int(len(neg) * 1/3)):
                fw.write(neg[i])
                fw.write('\n')
    def svm(self):
        y, x = svm_read_problem('AC/qa_train2.txt')
        yt, xt = svm_read_problem('AC/qa_test1.txt')
        options = '-c 0.8 -q'
        model = train(y, x, options)  # 利用训练数据生产模型
        p_label, p_acc, p_val = predict(yt, xt, model)  # 利用模型预测测试数据
        print(p_label)
        print(sum(p_label))
        test1 = []
        score = 0
        f1 = open('AC/qa_test1.txt','r')
        for line in f1:
            test1.append(line[0])
        for i in range(len(test1)):
            if test1[i] == '1' and p_label[i] == 1:
                score += 1
        print(score / len(test1))
        with open('AC/qa_test2.txt', 'w') as ft1:
            for i in p_label:
                ft1.write(str(i))
                ft1.write('\n')
    def get_answer(self):
        all_words = []
        train_group = []
        test_group = []
        group = []
        words = open('AC/qa_words.txt','r')
        for line in words:
            all_words.append(line[:-1])
        train_gp = open('AC/qa_train1.txt', 'r')
        for line in train_gp:
            train_group.append(line[:-1])
        test_gp = open('AC/qa_test2.txt','r')
        for line in test_gp:
            test_group.append(line[:-1])
        gp = open('AC/test_group.txt', 'r')
        for line in gp:
            group.append(line[:-1])
        # print(len(train_group))
        # print(len(test_group))
        # print(all_words)
        all_words = all_words[-len(test_group):]
        i = 0
        j = 0
        answer = []
        for n in group:
            a_str = ''
            i = j
            j += int(n)
            # print(i,j)
            # print(all_words[i:j])
            sent = all_words[i:j]
            test_sent = test_group[i:j]
            # for ni in range(j-i):
            #     # print(test_sent[ni])
            #     if test_sent[ni][0] == '1':
            #         a_str += sent[ni] + ' '
            # print(a_str)
            ns = 0
            ne = 0
            for ni in range(j-i):
                # print(test_sent[ni][0])
                if test_sent[ni][0] == '1':
                    ns = ni
                    break
            for ni in range(j-i):
                ni = j-i - ni-1
                # print(ni)
                if test_sent[ni][0] == '1':
                    ne = ni
                    break
            # print(ns,ne)
            # print(sent[ns-1:ne])
            a_str = ''
            if ns == ne == 0:
                answer.append('none')
            else:
                r_list = sent[ns:ne + 2]
                for i in range(len(r_list)):
                    # a_str = a_str + ni
                    if is_estr(r_list[i]) and a_str != '' and is_estr(r_list[i-1]):
                        a_str = a_str + ' '+ r_list[i]
                    # if is_num(a_str):
                    #     a_str = a_str + ' '+ ni
                    else:
                        a_str = a_str + r_list[i]
                answer.append(a_str)
        with open('AC/my_answer.txt','w') as fw:
            for a in answer:
                fw.write(a)
                fw.write('\n')

        # print(len(all_words[len(train_group):len(train_group) + len(test_group)]))


    def get_precesion(self):
        answer_list = []
        right_answer = []
        fa = open('AC/my_answer.txt','r')
        for line in fa:
            answer_list.append(line[:-1])
        key_list = list(self.data.keys())
        key_list = key_list[self.train_num:self.test_num + self.train_num]
        # print(len(answer_list))
        # print(len(key_list))
        # for k in key_list:
        #     print(self.data[k])
        blue = 0
        for i in range(len(key_list)):
            k = key_list[i]
            sent = self.data[k][1]
            answer = self.data[k][2]
            if answer_list[i] == 'none':
                answer_list[i] = sent[1:-1]
            pos_n = answer_list[i].find('：')
            if pos_n != -1:
                answer_list[i] = answer_list[i][pos_n+1:]
            for ni in range(len(answer_list[i])):
                if answer_list[i][ni] >= '0' and answer_list[i][ni] <= '9':
                    answer_list[i] = answer_list[i][ni:]
                    break
            for ni in range(len(answer_list[i])):
                if answer_list[i][ni] == '，' or answer_list[i][ni] == '。':
                    answer_list[i] = answer_list[i][:ni]
                    break
            answer_list[i].replace('"','')
            right_answer.append(answer[1:-2])
            blue += bleu1(answer_list[i],answer[1:-2])
        # for na in answer_list:
        #     print(na)
            print(sent)
            print(answer_list[i])
            print(right_answer[i])
        print('EM:',exact_match(answer_list,right_answer))
        print('BLEU',blue / self.test_num)

def keywords_extract(question,stopwords):
    # jieba.analyse.set_stop_words(stopwords)
    rv = jieba.analyse.extract_tags(question, topK=10, withWeight=True)
    return rv

if __name__ == '__main__':
    # k_words = keywords_extract("清华大学的副校长是谁？",stop_word)
    # print("jieba:")
    # for w, v in k_words:
    #     print("{0}: {1}".format(w, v))
    my_qa = QA()
    # my_qa.get_ner()
    my_qa.get_test()
    my_qa.get_r()
    my_qa.svm()
    my_qa.get_answer()
    my_qa.get_precesion()
    '''U110:%x[-2,11]
U111:%x[-1,11]
U112:%x[0,11]
U113:%x[1,11]
U114:%x[2,11]'''
