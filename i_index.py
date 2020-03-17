from pyltp import Segmentor,Postagger,NamedEntityRecognizer
import math
from FMM import FMM
from util import *

class index():
    def __init__(self):
        self.index_dict = {}
        self.N = 14768
        self.stop_word = self.load_stopwords('stopwords.txt')
        self.word_dict = self.load_dict('words_dict.txt')
        print(len(self.word_dict))
        self.word_dict = set(self.word_dict) | set(self.load_dict('ner_word.txt'))
        print(len(set(self.word_dict)))
        self.k1 = 1.5
        self.b = 0.75
        self.avgl = 0
        self.l_dict = self.load_word_length('length.txt')
        # print(self.avgl)

    def load_stopwords(self,filename):
        stop_word = []
        with open(filename, 'r') as f:
            for i in f:
                stop_word.append(i[:-1])
        return stop_word
    def load_word_length(self,filename):
        f = open(filename,'r')
        l_dict = {}
        i = 0
        for line in f:
            if i == 0:
                self.avgl = int(line[:-1])
                i = 1
            else:
                l_dict[line.split('\t')[0]] = int(line.split('\t')[1])
        return l_dict
    def get_length(self,filename):
        segmentor = Segmentor()
        segmentor.load('cws.model')
        postagger = Postagger()  # 初始化实例
        postagger.load('pos.model')  # 加载模型
        recognizer = NamedEntityRecognizer()  # 初始化实例
        recognizer.load('ner.model')  # 加载模型
        f = open(filename, 'r')
        l_dict = {}
        doc = []
        q_ner = []
        all_sum = 0
        for i in f:
            doc.append(i)
        all_doc_n = len(doc)
        for k in range(all_doc_n):
            sum = 0
            doc_list = doc[k].replace('{"pid": ', '').replace('"document": [', '').replace(']}', '').split(',')
            # print(doc_list)
            doc_n = int(doc_list[0])
            sentense_num = len(doc_list)
            # for d in doc_list[1:]:
            #     d = d[2:-2]
            for i in range(1, sentense_num):
                d = doc_list[i][2:-1]
                words = []
                cut_words = '\t'.join(segmentor.segment(d))
                words_list = cut_words.split('\t')
                if words_list == ['']:
                    continue
                postags = postagger.postag(words_list)  # 词性标注
                pos_line = '\t'.join(postags)
                q_pos_list = pos_line.split('\t')
                netags = recognizer.recognize(words_list, postags)  # 命名实体识别
                ner_line = '\t'.join(netags)
                ner_list = ner_line.split('\t')
                sum += len(words_list)
                ner_str = ''
                # print(ner_list)
                for nr in range(len(ner_list)):
                    if ner_list[nr][0] != 'O':
                        if ner_list[nr][0] == 'S' or ner_list[nr][0] == 'E':
                            ner_str += words_list[nr]
                            q_ner.append(ner_str)
                            ner_str = ''
                        else:
                            ner_str += words_list[nr]
            all_sum += sum
            l_dict[doc_n] = sum
            # print(q_ner)
        q_ner = list(set(q_ner))
        with open('ner_word.txt','w') as f:
            for qn in q_ner:
                f.write(qn)
                f.write('\n')
        # with open('length.txt','w') as f:
        #     f.write(str(int(all_sum / len(l_dict.keys()))))
        #     for k in l_dict.keys():
        #         f.write(str(k))
        #         f.write('\t')
        #         f.write(str(l_dict[k]))
        #         f.write('\n')


    def load_dict(self,filename):
        word_dict = []
        file = open(filename, 'r')
        try:
            b = file.read()
        finally:
            file.close()
        dic = b.split("\n")
        for word in dic:
            word_dict.append(word)
        return set(word_dict)

    def load_index(self,filename,type):
        f = open(filename, 'r')
        for line in f:
            cut_line = line.split(' ')
            cut_line_n = len(cut_line)
            # print(cut_line)
            for i in range(cut_line_n-1):
                if i == 0:
                    self.index_dict[cut_line[0][:-1]] = []
                else:
                    info = cut_line[i].replace('(','').replace(')','').split(',')
                    if type == True:
                        self.index_dict[cut_line[0][:-1]].append((info[0],info[1]))
                    else:
                        self.index_dict[cut_line[0][:-1]].append((info[0], info[1], info[2]))
        print('索引加载完成。')


    def get_index(self,filename):
        def adjust_list(l):
            n = len(l)
            cut_list = []
            if is_estr(l[0]):
                l[0] = str2lower(l[0])
            cut_list.append(l[0])
            for i in range(1, n):
                if is_estr(l[i]):
                    l[i] = str2lower(l[i])
                # cut_list.append(l[i])
                if cut_list[-1] + l[i] in self.word_dict:
                    cut_list[-1] = cut_list[-1] + l[i]
                else:
                    cut_list.append(l[i])
            return cut_list

        def add_word(index_dict, word, doc_num, p):
            if word not in index_dict.keys():
                index_dict[word] = []
            index_dict[word].append((doc_num,p))

        def show(inverted_index):
            for i in inverted_index.keys():
                print(i,':',inverted_index[i])

        def list2str(l):
            r_str = ''
            for i in l:
                r_str += '(' + str(i[0]) + ',' +str(i[1]) + ') '
            return r_str

        def write_index(filename,inverted_index):
            with open(filename, 'w', encoding='utf-8') as f:
                for i in inverted_index.keys():
                    f.write(str(i) + ': ' + list2str(inverted_index[i]))
                    f.write('\n')

        inverted_index = {}
        segmentor = Segmentor()
        segmentor.load('cws.model')

        f = open(filename,'r')
        doc = []
        for i in f:
            doc.append(i)
        all_doc_n = len(doc)
        for k in range(all_doc_n):
            # print(k)
            doc_list = doc[k].replace('{"pid": ','').replace('"document": [','').replace(']}','').split(',')
            # for d in doc_list:
            #     print(d)
            doc_n = int(doc_list[0])
            sentense_num = len(doc_list)
            # for d in doc_list[1:]:
            #     d = d[2:-2]
            for i in range(1,sentense_num):
                d = doc_list[i][2:-1]
                words = []
                cut_words = '\t'.join(segmentor.segment(d))
                words_list = cut_words.split('\t')
                words_list = adjust_list(words_list)
                for w in words_list:
                    if w not in self.stop_word:
                        add_word(inverted_index,w,doc_n,i)
                        if is_estr(w):
                            w = str2lower(w)
                        add_word(inverted_index, w, doc_n, i)
        # show(inverted_index)
        write_index('index.txt',inverted_index)

    def search(self,info_list,diff_info):
        def show(word_dict):
            # for i in dict_key:
            #     print('[' +'('+str(i[0]) + ',' + str(i[1]) +')' + str(word_dict[i]) + ']',end='')
            result = sorted(word_dict.items(), key=lambda d: d[1], reverse=True)
            print(result)
            r_str = ''
            for i in result[:10]:
                r_str += '(' + str(i[0]) + ',' +str(round(i[1],2)) + ') '
            return r_str

        def add_doc(word_doc, positions, first, word):
            # print(i_tuple)
            # tuple_index = (i_tuple[0],i_tuple[1])
            # if i_tuple not in word_doc.keys():
            #     word_doc[tuple_index] = i_tuple[2]
            # else:
            #     word_doc[tuple_index] += i_tuple[2]
            k = 8
            new_word_doc = {}
            if first:
                for position in positions:
                    tuple_index = (position[0])
                    new_word_doc[tuple_index] = (float(position[1]) * (1+math.log(len(word),k)))
                    # new_word_doc[tuple_index] = (float(position[1]))
            else:
                for position in positions:
                    tuple_index = (position[0])
                    if tuple_index in word_doc.keys():
                        # 连续出现
                        # new_word_doc[tuple_index] = word_doc[tuple_index] + float(position[1])
                        new_word_doc[tuple_index] = word_doc[tuple_index] + (float(position[1]) * (1+math.log(len(word),k)))
                        # new_word_doc[tuple_index] = word_doc[tuple_index] + (
                        #             float(position[1]) * ( 1 + 0.82*len(word)))
                    else:
                        # 新出现
                        new_word_doc[tuple_index] = (float(position[1]) * (1 + math.log(len(word),k)))
                        # new_word_doc[tuple_index] = (float(position[1]))
                old = list(word_doc.keys())
                now = list(new_word_doc)
                add = list(set(old) - set(now))
                for key in add:
                    # 原有未出现
                    new_word_doc[key] = word_doc[key]
            return new_word_doc


        if diff_info:
            diff_w = open('diff_info.txt','a')
            info_str = ''
            for i in info_list:
                info_str += i
            diff_w.write(info_str)
            diff_w.write('\n')

        # print('q:',info)
        first = True
        old_dict = {}
        new_dict = {}
        for word in info_list:
            if diff_info:
                diff_w.write(word)
                diff_w.write('\n')
            if word in self.stop_word or word == '"':
                continue
            # while len(word) > 0:
            #     if word not in self.index_dict.keys():
            #         word = word[:]
            #     new_dict = add_doc(old_dict, self.index_dict[word], first, word)
            #     old_dict = new_dict
            if word not in self.index_dict.keys():
                while word not in self.index_dict.keys() and len(word) > 0:
                    word = word[:-1]
                if len(word) < 1:
                    continue

            new_dict = add_doc(old_dict, self.index_dict[word], first, word)
            old_dict = new_dict

            if diff_info:
                # diff_w.write(word)
                # diff_w.write('\n')
                diff_w.write(show(old_dict))
                diff_w.write('\n')
                diff_w.write(show(new_dict))
                diff_w.write('\n')
            first = False
        for k in new_dict.keys():
            # print(new_dict[k])
            new_dict[k] /= 2
            # print(new_dict[k])

        result = sorted(new_dict.items(), key=lambda d: d[1], reverse=True)
        # print(result)

        return result[0:3]

    def tf_idf(self):
        def list2str(l):
            r_str = ''
            for i in l:
                # r_str += '(' + str(i[0]) + ',' +str(i[1]) + ',' +str(round(i[2],2)) + ') '
                r_str += '(' + str(i[0]) + ',' + str(round(i[1], 3)) + ') '
            return r_str

        tf_idf_index = {}

        for i in self.index_dict.keys():
            tf_idf_index[i] = []
            index_list = []
            tf_dict = {}
            idf_set = set()
            p_list = self.index_dict[i]
            for position in p_list:
                position = position[0]
                if position not in tf_dict.keys():
                    tf_dict[position] = 1
                    idf_set.add(position)
                else:
                    tf_dict[position] += 0.3 #0.14 83.94 0.01 83.9 0.016 838 0.012 84.024
            n_idf = len(idf_set)
            idf = math.log(self.N / n_idf,25)
            for j in tf_dict.keys():
                tf_dict[j] = 1 + math.log(tf_dict[j],10)
                tf_dict[j] *= idf
                # tf_dict[j] *= 1
                # index_list.append((j[0],j[1],tf_dict[j]))
                # print((j[0], tf_dict[j]))
                index_list.append((j, tf_dict[j]))
            tf_idf_index[i] = index_list

        with open('idf.txt', 'w', encoding='utf-8') as f:
            for i in tf_idf_index.keys():
                f.write(str(i) + ': ' + list2str(tf_idf_index[i]))
                f.write('\n')
    def bm25(self):
        def list2str(l):
            r_str = ''
            for i in l:
                # r_str += '(' + str(i[0]) + ',' +str(i[1]) + ',' +str(round(i[2],2)) + ') '
                r_str += '(' + str(i[0]) + ',' + str(round(i[1], 3)) + ') '
            return r_str
        tf_idf_index = {}
        for i in self.index_dict.keys():
            tf_idf_index[i] = []
            index_list = []
            tf_dict = {}
            idf_set = set()
            p_list = self.index_dict[i]
            for position in p_list:
                position = position[0]
                if position not in tf_dict.keys():
                    tf_dict[position] = 1
                    idf_set.add(position)
                else:
                    tf_dict[position] += 0.6 #0.14 83.94 0.01 83.9 0.016 838 0.012 84.024
            n_idf = len(idf_set)
            # idf = math.log((self.N - n_idf + 0.5) / (n_idf + 0.5))
            idf = math.log(self.N / n_idf)
            for j in tf_dict.keys():
                # tf_dict[j] = 1 + math.log(tf_dict[j],10)
                # tf_dict[j] *= idf
                tf_dict[j] = (idf * tf_dict[j] * (self.k1 + 1)
                 / (tf_dict[j] + self.k1 * (1 - self.b + self.b * self.l_dict[j] / self.avgl)))
                index_list.append((j, tf_dict[j]))
            tf_idf_index[i] = index_list
        with open('bm25.txt', 'w', encoding='utf-8') as f:
            for i in tf_idf_index.keys():
                f.write(str(i) + ': ' + list2str(tf_idf_index[i]))
                f.write('\n')

    def questions_search(self,filename):
        def adjust_list(l):
            n = len(l)
            cut_list = []
            if is_estr(l[0]):
                l[0] = str2lower(l[0])
            cut_list.append(l[0])
            for i in range(1, n):
                if is_estr(l[i]):
                    l[i] = str2lower(l[i])
                # cut_list.append(l[i])
                if cut_list[-1] + l[i] in self.word_dict:
                    cut_list[-1] = cut_list[-1] + l[i]
                else:
                    cut_list.append(l[i])
                # print(cut_list)
            return cut_list
        def list2str(l):
            r_str = '"answer_pid": ['
            for i in l:
                # r_str += '(' + str(i[0]) + ',' +str(i[1]) + ') '
                r_str += str(i[0]) + ','
            r_str += ']}'
            return r_str
        questions_id = {}
        questions = []
        questions_answer = {}
        with open(filename, 'r') as f:
            for line in f:
                # print(line.split('"question": ')[1].replace(' ', '')[1:-3])
                questions.append(line.split('"question": ')[1].replace(' ', '')[1:-3])
                questions_id[line.split('"question": ')[1].replace(' ', '')[1:-3]] = \
                    line.split(':')[1].replace(', "question"','').replace(' ','')
                # questions.append(line.split(':')[1].replace('"question"','').replace(' ',''))

        segmentor = Segmentor()
        segmentor.load('cws.model')
        for q in questions:
            # print(q)
            cut_info = '\t'.join(segmentor.segment(q))
            info_list = cut_info.split('\t')
            infos_list = adjust_list(info_list)
            questions_answer[q] = self.search(infos_list, False)
        with open('train_test.txt','w') as fr:
            for qi in questions_answer.keys():
                fr.write('{"qid":' + questions_id[qi] + ', "question":"' + qi + '", ' + list2str(questions_answer[qi]))
                fr.write('\n')



    def get_precesion(self,train_file,my_test_file):
        def get_answer(filename,type):
            answer = []
            f = open(filename,'r')
            if type:
                for line in f:
                    # print(line.split(',')[1].replace(' "pid": ',''))
                    answer.append(line.split(',')[1].replace(' "pid": ',''))
            else:
                for line in f:
                    q_answer = []

                    # p_list = line.split(':')[-1].replace(' [','').replace(',]}\n','').replace('(','').replace(')','').split(',')
                    # if len(p_list) == 1:
                    #     print(len(p_list),line)
                    #     answer.append(q_answer)
                    #     continue
                    # for i in range(int(len(p_list)/2)):
                    #     q_answer.append(p_list[2*i].replace('\'',''))
                    # answer.append(q_answer)
                    q_answer = line.split(':')[-1].replace(' [','').replace(',]}\n','').split(',')
                    # if len(q_answer) != 3:
                    #     print(line)
                    # print(line.split(':')[1][1:])
                    if line.split(':')[1][1:] == '"樱英文叫什么？","pid"':
                        print(line)
                    if q_answer == [']}\n']:
                        # print(line)
                        answer.append(['0','0','0'])
                    else:
                        while len(q_answer) < 3:
                            q_answer.append(0)
                        answer.append(q_answer)

            return answer

        answer = get_answer(train_file,True)
        my_answer = get_answer(my_test_file,False)
        pn = len(my_answer)
        pr1 = 0
        pr2 = 0
        pr3 = 0
        diff = []
        for i in range(pn):
            if my_answer[i] == []:
                continue
            if answer[i] in my_answer[i][:1]:
                # print(answer[i])
                # print(my_answer[i])
                pr1 += 1
                pr2 += 1
                pr3 += 1
            elif answer[i] in my_answer[i][:2]:
                pr2 += 1
                pr3 += 1
            elif answer[i] in my_answer[i][:3]:
                pr3 += 1
            else:
                diff.append(i)
        print('测试个数：', pn)
        print('top-1:正确个数：',pr1,'准确率为：', round(100 * pr1 / pn,2))
        print('top-2:正确个数：', pr2, '准确率为：', round(100 * pr2 / pn,2))
        print('top-3:正确个数：', pr3, '准确率为：', round(100 *pr3 / pn,2))

        with open('diff.txt','w') as f:
            for d in diff:
                f.write(str(d) +'\n')


    def load_my_segment(self,filename):
        def get_word_dict(filename,write):
            word_dict = []
            f = open(filename, 'r')
            for line in f:
                # print(line.split(':')[0])
                word_dict.append(line.split(':')[0])
            if write:
                with open('words_dict.txt', 'w') as fr:
                    for w in word_dict:
                        fr.write(w + '\n')
            return word_dict

        self.word_dict = get_word_dict(filename,True)

    def lookup_diff(self,filename):
        def adjust_list(l):
            n = len(l)
            cut_list = []
            if is_estr(l[0]):
                l[0] = str2lower(l[0])
            cut_list.append(l[0])
            for i in range(1, n):
                if is_estr(l[i]):
                    l[i] = str2lower(l[i])
                # cut_list.append(l[i])
                if cut_list[-1] + l[i] in self.word_dict:
                    cut_list[-1] = cut_list[-1] + l[i]
                else:
                    cut_list.append(l[i])
            return cut_list
        def list2str(l):
            r_str = '"pid": ['
            for i in l:
                # r_str += '(' + str(i[0]) + ',' +str(i[1]) + ') '
                r_str += str(i[0]) + ','
            r_str += ']}'
            return r_str

        questions = []
        questions_answer = {}
        with open(filename, 'r') as f:
            for line in f:
                questions.append(line.split(':')[1].replace(', "pid"', '').replace(' ', ''))
        segmentor = Segmentor()
        segmentor.load('cws.model')
        i = 0

        diff = []
        fdiff = open('diff.txt','r')
        for line in fdiff:
            diff.append(int(line[:-1]))

        my_fmm = FMM()
        my_fmm.load_dict('words_dict.txt')
        fr = open('diff_info.txt','w')
        fr.write('\n')
        fr.close()
        for d in diff:
            q = questions[d]
            # print(q)
            cut_info = '\t'.join(segmentor.segment(q))
            info_list = cut_info.split('\t')
            infos_list = adjust_list(info_list)
            print(infos_list)
            questions_answer[q] = self.search(infos_list,True)
            i += 1
            if i % 1000 == 0:
                print(i)
            if i == 200:
                break


if __name__ == "__main__":
    my_index = index()
    # my_index.get_length('data/passages_multi_sentences.json')
    # my_index.get_index('data/passages_multi_sentences.json')
    # my_index.load_index('index.txt',True)
    # my_index.tf_idf()
    # my_index.load_my_segment('index.txt')
    # my_index.load_index('idf.txt', True)
    # my_index.bm25()
    my_index.load_index('bm25.txt', True)
    # my_index.questions_search('data/train.json')
    my_index.questions_search('data/new_test.json')
    # my_index.get_precesion('data/train.json','train_test.txt')

