import jieba
import jieba.analyse
from pyltp import Segmentor,NamedEntityRecognizer,Postagger,Parser,SementicRoleLabeller
from util import is_estr,is_num
from metric import *
import CRFPP
import os
class AS:
    def __init__(self):
        self.stop_word1 = self.load_stopwords('stopwords.txt')
        self.stop_word1.append('"')
        self.stop_word1.append(',')
        self.stop_word = ['"']
        # self.data = self.load_data('data/train.json')
        self.data = self.load_data('result/sentence.json')
        self.pos = ['：', '《', '》', '【', '】', '[', ']', '〈', '〉', '-', '（', '）', '.', '/', '—', '%', '～']
        self.q_word = []
        # self.train_num = int(5352 * 7/10)
        # self.test_num = 5352 - self.train_num
        self.train_num = 0
        self.test_num = 1978 - self.train_num
        # self.group = self.load_qa_group('AC/qa_group.txt')
        self.type = []
        self.question_word = self.load_stopwords('AS/ywc.txt')
        self.syn_dict = self.load_syn_dict('AC/syn.txt')
    def load_stopwords(self,filename):
        stop_word = []
        with open(filename, 'r') as f:
            for i in f:
                stop_word.append(i[:-1])
        return stop_word

    def load_syn_dict(self,filename):
        syn = {}
        with open(filename, 'r', encoding='GBK') as f:
            for line in f:
                line_list = line[:-1].split()
                for i in range(1,len(line_list)):
                    syn[line_list[i]] = line_list[0]
        # for k in syn.keys():
        #     print(k,syn[k])
        return syn

    def load_data(self,filename):
        # fc = open('qc_answer_M.txt', 'r')
        # type_list = []
        # for line in fc:
        #     type_list.append(line.split()[0])
        #
        # f = open(filename, 'r')
        # data = {}
        # i = -1
        # for line in f:
        #     i += 1
        #     line_list = line.replace('{"question": ', '').replace('"pid": ', 'cut_f').replace('"answer_sentence": [',
        #                                                                                       'cut_f').replace(
        #         '], "answer": ', 'cut_f').replace(' "qid": ', 'cut_f').replace('}', '').split('cut_f')
        #     line_list[4] = line_list[4][:-1]
        #     data[line_list[4]] = []
        #     for n in [0, 2, 3]:
        #         data[line_list[4]].append(line_list[n])
        #     data[line_list[4]].append(type_list[i])
        #     print(line_list[4], data[line_list[4]])
        # return data
        fc = open('result/right_answer.txt', 'r')
        type_list = []
        for line in fc:
            type_list.append(line.split()[0])

        f = open(filename, 'r')
        data = {}
        i = -1
        for line in f:
            "qid q did answer type"
            i += 1
            line_list = line.replace('{"qid": ', '').replace(', "question": ', 'cut_f').replace(' "pid": ','cut_f').replace(
                ', "answer_sentence": ', 'cut_f').replace('}', '').split('cut_f')
            # print(line_list)
            line_list[3] = line_list[3][:-1]
            data[line_list[0]] = []
            for n in [1,3,2]:
                data[line_list[0]].append(line_list[n])
            data[line_list[0]].append(type_list[i])
        #     print(line_list[0], data[line_list[0]])
        return data


    def add_feature(self):
        def getshape(word):
            r = ''
            for w in word:
                if w.isupper():
                    r = r + 'A'
                elif w.islower():
                    r = r + 'a'
                elif w.isdigit():
                    r = r + '0'
                elif w in self.pos:
                    r = r + 'p'
                elif w in ['”','、','“','。','；','，','？','！','','']:
                    r = r + 'b'
                else:
                    r = r + 'c'
                return r
        def path_cal(begin_idx, end_idx, arcs):
            # print(begin_idx, end_idx)
            begin_path_index = []
            flag = False
            while arcs[begin_idx].head != 0:
                if begin_idx == end_idx:
                    flag = True
                    break
                begin_path_index.append(begin_idx)
                begin_idx = arcs[begin_idx].head - 1
            begin_path_index.append(begin_idx)
            if flag:
                # print(begin_path_index)
                return begin_path_index
            else:
                end_path_index = []
                while arcs[end_idx].head != 0:
                    if end_idx == begin_idx:
                        flag = True
                        break
                    end_path_index.append(end_idx)
                    end_idx = arcs[end_idx].head - 1
                end_path_index.append(end_idx)
                if flag:
                    # print(end_path_index)
                    return end_path_index
                else:
                    end_path_index.reverse()
                    path_index = begin_path_index + end_path_index[1:]
                    # print(path_index)
                    return path_index
        def get_answer_pos(l, answer):
            r = [0 for n in range(len(l))]
            r_str = ''
            i = 0
            while r_str != answer[1:-2] and i < len(l):
                if l[i] in answer:
                    r_str += l[i]
                    r[i] = 1
                else:
                    r_str = ''
                    for j in range(i):
                        r[j] = 0
                i += 1
            if sum(r) == 0:
                # print(l)
                # print(answer)
                for j in range(len(r)):
                    # print(answer[1:-2],l[j])
                    if answer[1:-2] in l[j]:
                        r[j] = 1
                if sum(r) == 0:
                    i = 0
                    while r_str != answer[1:-2].replace(' ','') and i < len(l):
                        if l[i] in answer or l[i] in answer.replace(' ',''):
                            r_str += l[i]
                            r[i] = 1
                        else:
                            r_str = ''
                            for j in range(i):
                                r[j] = 0
                        i += 1
            if sum(r) == 1:
                for i in range(len(r)):
                    if r[i] == 1:
                        r[i] = 'S'
                        # r[i] = 'B'
                    else:
                        r[i] = 'O'
            else:
                for i in range(len(r)):
                    if r[i] == 1:
                        r[i] = 'I'
                    else:
                        r[i] = 'O'
                for i in range(len(r)):
                    if r[i] == 'I':
                        r[i] = 'B'
                        break
                for i in range(len(r)-1, 0, -1):
                    if r[i] == 'I':
                        r[i] = 'E'
                        break
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

        fti = open('AC/tf-idf.txt', 'r')
        all_word = []
        for line in fti:
            k = line[:-1].split('\t')[0]
            all_word.append(k)
        all_word = set(all_word)
        nj = 0
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
            # q_list = adjust_list(q_list, all_word)
            postags = postagger.postag(q_list)  # 词性标注
            pos_line = '\t'.join(postags)
            q_pos_list = pos_line.split('\t')
            netags = recognizer.recognize(q_list, postags)  # 命名实体识别
            ner_line = '\t'.join(netags)
            ner_list = ner_line.split('\t')
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
            q_arcs = parser.parse(q_list, q_pos_list)  # 句法分析
            arcs_line = "\t".join("%d %s" % (arc.head, arc.relation) for arc in q_arcs)
            arcs_list = arcs_line.split('\t')
            # print(q_list)
            # for i in range(len(arcs_list)):
            #     # print(arcs_list[i].split(' '))
            #     if int(arcs_list[i].split(' ')[0]) == 0:
            #         q_arcs.append( 'root_' +  q_list[i]  + '_' + arcs_list[i].split(' ')[1])
            #     else:
            #         q_arcs.append(q_list[int(arcs_list[i].split(' ')[0])-1]+ '_' +  q_list[i]  + '_' + arcs_list[i].split(' ')[1])
            # print(q_arcs)
            # roles = labeller.label(q_list, postags, arcs)
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
            # print(self.data[k][0])
            # print('sbv',q_sbv)
            # print('v',q_v)
            # print('vob',q_vob)
            # print('att1',q_att1)
            # print('att2',q_att2)
            q_key = []
            q_key_l =0.0
            for i in range(len(q_list)):
                # if q_pos_list[i][0] == 'n' or q_pos_list[i][0] == 'a' or q_pos_list[i][0] == 'v':
                if q_list[i] not in self.stop_word1 and q_pos_list[i] != 'r':
                    q_key.append(q_list[i])
            q_w = ''
            for q in q_list:
                if q in self.question_word:
                    q_w = q
            if q_w == '':
                q_w = q_list[-3]
            # print('q_k',q_key)
            a_list = []
            a_pos_list = []
            cut_line = '\t'.join(segmentor.segment(self.data[k][1]))
            word_list = cut_line.split('\t')  # 分词
            # print(word_list)
            for i in word_list:
                if i not in self.stop_word:
                    a_list.append(i)
            # a_list = adjust_list(a_list, all_word)
            postags = postagger.postag(a_list)  # 词性标注
            pos_line = '\t'.join(postags)
            a_pos_list = pos_line.split('\t')
            netags = recognizer.recognize(a_list, postags)  # 命名实体识别
            ner_line = '\t'.join(netags)
            ner_list = ner_line.split('\t')
            arcs = parser.parse(a_list, a_pos_list)  # 句法分析
            arcs_line = "\t".join("%d %s" % (arc.head, arc.relation) for arc in arcs)
            arcs_list = arcs_line.split('\t')
            a_arcs = []
            a_ci = 0
            for i in range(len(arcs_list)):
                if arcs_list[i].split(' ')[1] == 'HED':
                    aci = i
                    break
                # print(arcs_list[i].split(' '))
                # if int(arcs_list[i].split(' ')[0]) == 0:
                #     a_arcs.append('root_' + a_list[i] + '_' + arcs_list[i].split(' ')[1])
                # else:
                #     a_arcs.append(a_list[int(arcs_list[i].split(' ')[0]) - 1] + '_' + a_list[i] + '_' + arcs_list[i].split(' ')[
                #             1])
            # print(a_arcs)
            a_key = []
            a_key_l = 0.0
            for i in range(len(a_list)):
                # if a_pos_list[i][0] == 'n' or a_pos_list[i][0] == 'a' or a_pos_list[i][0] == 'v':
                if a_list[i] not in self.stop_word1 and a_list[i] in q_key:
                    a_key.append(a_list[i])
            if a_key == []:
                a_key_l = 5.0
            else:
                for qkw in a_key:
                    # print(path_cal(q_list.index(q_w),q_list.index(qkw),q_arcs))
                    q_key_l += len(path_cal(q_list.index(q_w),q_list.index(qkw),q_arcs))
                q_key_l /= len(a_key)
            r_pos = get_answer_pos(a_list,self.data[k][2])
            # print(a_list)
            # print(r_pos)
            for i in range(len(a_list)):
                str_f = a_list[i]
                w_l = 0.0
                a_l = 0.0
                for j in range(len(a_list)):
                    if a_list[j] in q_key:
                        w_l += 1 / (math.fabs(i-j) + 1)
                if w_l == 0.0:
                    w_l = 5.0
                # for j in range(len(a_list)):
                #     print(a_list[i],arcs_list[i])
                # print(a_l)
                if a_list[i] in set(q_list):
                    str_f += '\tin_q'
                else:
                    str_f += '\tnot_in_q'
                if a_list[i] in set(q_ner):
                    str_f += '\tin_qner'
                else:
                    str_f += '\tnot_in_qner'
                if a_list[i] in set(q_sbv):
                    str_f += '\tin_sbv'
                else:
                    str_f += '\tnot_in_sbv'
                if a_list[i] in set(q_v):
                    str_f += '\tin_qv'
                else:
                    str_f += '\tnot_in_qv'
                if a_list[i] in set(q_vob):
                    str_f += '\tin_qvob'
                else:
                    str_f += '\tnot_in_qvob'
                if a_list[i] in set(q_att1):
                    str_f += '\tin_att1'
                else:
                    str_f += '\tnot_in_att1'
                if a_list[i] in set(q_att2):
                    str_f += '\tin_att2'
                else:
                    str_f += '\tnot_in_att2'
                if a_list[i] in set(self.pos):
                    str_f += '\t' + a_list[i]
                else:
                    str_f += '\tnot_in_pos'
                str_f += '\t'+self.data[k][-1]+ '_' + a_pos_list[i]
                str_f += '\t' + a_pos_list[i]
                str_f += '\t' + str(round(w_l,1))
                # print(a_key)
                if a_key == []:
                    a_key_l = 5.0
                else:
                    for qkw in a_key:
                        a_key_l += len(path_cal(i,a_list.index(qkw), arcs))
                        # else:
                        #     a_key_l += 10.0
                    a_key_l /= len(a_key)
                    a_key_l -= q_key_l
                # print(a_key_l)
                str_f += '\t' + str(round(a_key_l,1))
                str_f += '\t' + str(len(a_list[i]))
                str_f += '\t' + getshape(a_list[i])
                if a_list[i] in self.syn_dict.keys():
                    str_f += '\t' + self.syn_dict[a_list[i]]
                else:
                    str_f += '\t' + 'N-syn'
                str_f += '\t' + str(arcs_list[i].split(' ')[1])
                # str_f += '\t' + str(i)
                str_f += '\t' + str(math.fabs(aci-i))
                str_f += '\t' + str(r_pos[i])
                # i_key = list(set(q_key) | set(a_key))
                # str_f += '\t' + str(get_l(a_list[i],a_arcs,i_key))
                # str_f += '\t' + str(get_l(a_list[i], a_arcs, a_key))
                word_feature.append(str_f)
            word_group.append(str(len(a_list)))
            nj += 1
            # if nj == 5:
            #     break
            if nj % 1000 == 0:
                print(nj)
        with open('AS/train.txt','w') as f1:
            for wf in word_feature[:]:
                f1.write(wf)
                f1.write('\n')
        with open('AS/group.txt','w') as f3:
            for wg in word_group:
                f3.write(str(wg))
                f3.write('\n')
    def get_test(self):
        train_data = []
        test_data = []
        train_group = []
        test_group = []
        ft = open('AS/train.txt', 'r')
        for line in ft:
            train_data.append(line[:-1])
        fg = open('AS/group.txt', 'r')
        for line in fg:
            train_group.append(line[:-1])
        trg = self.train_num
        teg = self.test_num
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
        with open('AS/train_group.txt', 'w') as ftrg:
            for i in train_group:
                ftrg.write(i)
                ftrg.write('\n')
        with open('AS/test_group.txt', 'w') as fteg:
            for i in test_group:
                fteg.write(i)
                fteg.write('\n')
        with open('AS/as_train.txt', 'w') as ftr:
            for i in train_data:
                ftr.write(i)
                ftr.write('\n')
        with open('AS/as_test.txt', 'w') as fte:
            for i in test_data:
                fte.write(i)
                fte.write('\n')
    def crf(self):
        # 训练模型
        # os.system("crf_learn -c 1 AS/template AS/as_train.txt AS/model.txt")
        # 根据模型进行实体识别
        os.system("crf_test -m AS/model.txt  AS/as_test.txt > AS/result.txt")

    def get_answer(self):
        all_words = []
        all_pos = []
        group = []
        words = open('AS/result.txt','r')
        # words = open('AS/as_test.txt', 'r')
        for line in words:
            all_words.append(line.split('\t')[0])
            all_pos.append(line.split('\t')[-1][:-1])
        train_gp = open('AS/test_group.txt', 'r')
        for line in train_gp:
            group.append(line[:-1])
        i = 0
        j = 0
        answer = []
        for n in group:
            a_str = ''
            i = j
            j += int(n)
            sent = all_words[i:j]
            sent_pos = all_pos[i:j]
            # print(sent)
            # print(sent_pos)
            for ni in range(len(sent)):
                if sent_pos[ni] == 'O':
                    continue
                else:
                    if sent_pos[ni] == 'S':
                        a_str += sent[ni] + ' '
                    elif sent_pos[ni] == 'E':
                        a_str += sent[ni] + ' '
                    # if sent_pos[ni] == 'B' and a_str != '':
                    #     a_str += sent[ni] + ' '
                    else:
                        a_str += sent[ni]
            if a_str == '':
                answer.append('none')
            else:
                if a_str[-1] == ' ':
                    answer.append(a_str[:-1])
                else:
                    answer.append(a_str)
        nn = 0
        for a in answer:
            if a == 'none':
                nn += 1
        print(nn)
        print(len(answer))
        with open('AS/my_answer.txt','w') as fw:
            for a in answer:
                fw.write(a)
                fw.write('\n')
    def get_precesion(self):
        answer_list = []
        right_answer = []
        fa = open('AS/my_answer.txt','r')
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
                pos_ni = []
                answer_list[i] = sent[1:-1]
                # print(sent)
                pos_n = answer_list[i].find('：')
                if pos_n != -1 and pos_n != len(answer_list[i])-1:
                    answer_list[i] = answer_list[i][pos_n+1:]
                else:
                    pos_n = answer_list[i].find(':')
                    if pos_n != -1 and pos_n != len(answer_list[i])-1:
                        answer_list[i] = answer_list[i][pos_n + 1:]
                for ni in range(len(answer_list[i])):
                    if answer_list[i][ni] >= '0' and answer_list[i][ni] <= '9':
                        answer_list[i] = answer_list[i][ni:]
                        break
                for ni in range(len(answer_list[i])):
                    if answer_list[i][ni] == '，' or answer_list[i][ni] == '。' :
                        answer_list[i] = answer_list[i][:ni]
                        break
                # if bleu1(answer_list[i],answer[1:-2]) < 0.5:
                #     print(sent)
                #     print(answer_list[i])
                #     print(answer[1:-2])
                answer_list[i] = answer_list[i].replace('"','')
                answer_list[i] = answer_list[i].replace(';', '')
                answer_list[i] = answer_list[i].replace(':', '')
                answer_list[i] = answer_list[i].replace('：', '')
            right_answer.append(answer[1:-2])
            blue += bleu1(answer_list[i],answer[1:-2])
            print(self.data[k][0])
            print(sent)
            print(answer_list[i])
            # print(right_answer[i])
        print('EM:',exact_match(answer_list,right_answer))
        print('BLEU',blue / self.test_num)
        with open('result/as.txt','w')  as f:
            for a in answer_list:
                f.write(a)
                f.write('\n')
if __name__ == '__main__':
    my_as = AS()
    # my_as.add_feature()
    # my_as.get_test()
    # my_as.crf()
    my_as.get_answer()
    my_as.get_precesion()


