from pyltp import Segmentor,Postagger,NamedEntityRecognizer,SementicRoleLabeller,Parser
from liblin.liblinearutil import *
from liblin.commonutil import *
import math
import  random
class QC():
    def __init__(self,filename):
        # self.stop_word = self.load_stopwords('stopwords.txt')
        # self.stop_word = ['是','什么','的','？','有','哪','一','哪个','个','啊','在','、','于','“','”','》','《']
        # self.stop_word = ['？','哪','哪个','啊']
        self.stop_word = []
        self.MC_dict = self.load_data(filename,True)
        self.SC_dict = self.load_data(filename,False)
        self.N = 0
        self.types = {}
    def load_stopwords(self,filename):
        stop_word = []
        with open(filename, 'r') as f:
            for i in f:
                stop_word.append(i[:-1])
        return stop_word
    def load_data(self,filename,M_class):
        train_dict = {}
        f = open(filename,'r')
        for line in f:
            l_list = line[:-1].split()
            if M_class:
                l_list[0] = l_list[0].split('_')[0]
            if l_list[0] not in train_dict:
                train_dict[l_list[0]] = []
                train_dict[l_list[0]].append(l_list[1])
            else:
                train_dict[l_list[0]].append(l_list[1])
        return train_dict

    def get_vec(self,M_class):
        segmentor = Segmentor()
        segmentor.load('cws.model')
        postagger = Postagger()  # 初始化实例
        postagger.load('pos.model')  # 加载模型
        recognizer = NamedEntityRecognizer()  # 初始化实例
        recognizer.load('ner.model')  # 加载模型
        labeller = SementicRoleLabeller()  # 初始化实例
        labeller.load('pisrl.model')  # 加载模型
        parser = Parser()
        parser.load('parser.model')


        def merge_base(cut,pos,ner,arcs_list):
            base_list = []
            cw = []
            dr  = []
            cw_w = []
            for i in range(len(cut)):
                base_list.append(cut[i] + '/' + pos[i])
            for i in range(len(arcs_list)):
                cw.append(arcs_list[i].split(':')[0])
                dr.append(arcs_list[i].split(':')[1])
            # for i in range(len(cw)):
            #     if cw[i] != '0':
            #         base_list.append(cut[i] + '/' + cut[int(cw[i]) - 1])
            #     else:
            #         base_list.append(cut[i] + '/')
            cw = list(set(cw))
            dr = list(set(dr))
            for ci in cw:
                base_list.append(cut[int(ci)-1])
            for di in dr:
                base_list.append(di)
            for i in range(len(cut)):
                base_list.append(cut[i] + '/' + ner[i])

            # print(base_list)
                # base_list.append(cut[i])
            # print(base_list)
            return base_list

        def cut_segment(data,stop_words):
            all_words = []
            type_dict =  {} # 类-词
            type_dict_freq = {} # 类-词-词频
            type_dict_num = {} # 类-文档数量
            type_dict_doc = {}  # 类-词-文档数
            all_num = 0
            for k in data.keys():
                words = []
                type_dict[k] = []
                words_freq = {}
                for line in data[k]:
                    cut_line = '\t'.join(segmentor.segment(line))
                    word_list = cut_line.split('\t') # 分词
                    # print(word_list)
                    words_list = []
                    for word in word_list:
                        if word not in self.stop_word:
                            words_list.append(word)
                    # print(words_list)
                    postags = postagger.postag(words_list)  # 词性标注
                    pos_line = '\t'.join(postags)
                    pos_list = pos_line.split('\t')

                    netags = recognizer.recognize(words_list, pos_list)  # 命名实体识别
                    ner_line = '\t'.join(netags)
                    ner_list = ner_line.split('\t')

                    arcs = parser.parse(words_list, pos_list)  # 句法分析
                    arcs_line = "\t".join("%d:%s" % (arc.head, arc.relation) for arc in arcs)
                    arcs_list = arcs_line.split('\t')

                    words_list = merge_base(words_list,pos_list,ner_list,arcs_list)
                    # print(words_list)
                    for word in words_list:
                        is_adddoc = True
                        if word not in stop_words:
                            words.append(word)
                            if word not in words_freq.keys():
                                words_freq[word] = 1
                            else:
                                words_freq[word] += 1
                            if is_adddoc: # 词出现的问题树
                                if word not in type_dict_doc.keys():
                                    type_dict_doc[word] = 1
                                else:
                                    type_dict_doc[word] += 1
                                is_adddoc = False
                words = list(set(words))
                type_dict[k] = words
                type_dict_freq[k] = words_freq
                type_dict_num[k] = len(data[k])
                all_num += len(data[k])
                print('类' + str(k)+ '\t问题数量为：' + str(type_dict_num[k]) + '\t单词数量为：',len(words))
                all_words = list(set(words) | set(all_words))
                # for wd in type_dict_doc.keys():
                #     print(wd,'出现文档数为：',type_dict_doc[wd])
            print('所有单词数量为：',len(all_words))
            print('所有问题数量为：', all_num)
            self.N = all_num
            # for i in type_dict.keys():
            #     print(i,type_dict[i])
            # for i in type_dict_freq.keys():
            #     print(i,type_dict_freq[i])
            # print(sorted(type_dict_doc.items(),key=lambda d: d[1], reverse=True))
            return type_dict_num, type_dict, type_dict_freq, type_dict_doc


        def choose_features(type_dict_freq):
            def word_freq(): # 词频选取特征
                word_features = []
                for k in type_dict_freq.keys():
                    # print(k,type_dict_freq[k])
                    result = sorted(type_dict_freq[k].items(), key=lambda d: d[1], reverse=True)
                    # print(k,result[:200])
                    if M_class:
                        # for r in result[:300]:
                        for r in result:
                            word_features.append(r[0])
                    else:
                        # for r in result[:30]:
                        for r in result:
                            word_features.append(r[0])
                word_features = list(set(word_features))
                word_features = sorted(word_features)
                # print(word_features)
                return word_features
            word_features = []
            word_features = word_freq()
            # word_features = word_chi()
            return word_features

        def get_tf_idf(word_features,type_dict_freq,type_dict_doc):
            tf_idf = {}
            for k in type_dict_freq.keys():
                tf_idf[k] = [0 for i in range(len(word_features))]
                # print(type_dict_freq[k].keys())
                for w_f in word_features:
                    if w_f in type_dict_freq[k]:
                        # print(w_f,word_features.index(w_f),type_dict_freq[k][w_f])
                        tf_idf[k][word_features.index(w_f)] = type_dict_freq[k][w_f]
                # print(word_features)
                # print(tf_idf[k])

            k_d = {}
            for k in tf_idf.keys():
                k_d[k] = 0
                for i in tf_idf[k]:
                    k_d[k] += i

            for w_f in word_features: # 调整idf
                for k in tf_idf.keys():
                    tf_idf[k][word_features.index(w_f)] /= k_d[k]
                    idf = math.log(self.N / type_dict_doc[w_f])
                    tf_idf[k][word_features.index(w_f)] *= float(idf)

                    # tf_idf[k][word_features.index(w_f)] = (tf_idf[k][word_features.index(w_f)]>0)
                    # if tf_idf[k][word_features.index(w_f)]:
                    #     tf_idf[k][word_features.index(w_f)]  = 1
                    # else:
                    #     tf_idf[k][word_features.index(w_f)] = 0
            return tf_idf
        def write_vec(type_dict_freq,word_features,train_or_test,M_class):
            w_dict = {}
            types = {}
            trains = []
            ti = 0
            for i in type_dict_freq.keys(): # 类别编码
                ti += 1
                types[i] = ti
            if not M_class:
                types['OBJ_ADDRESS'] = len(types.keys())+1
                with open('types_S.txt','w') as fr:
                    for k in types.keys():
                        fr.write(str(types[k])+ '\t' + str(k))
                        fr.write('\n')
            else:
                with open('types_M.txt','w') as fr:
                    for k in types.keys():
                        fr.write(str(types[k]) + '\t' + str(k))
                        fr.write('\n')
            if train_or_test:
                if M_class:
                    w_dict = self.MC_dict
                else:
                    w_dict = self.SC_dict
            else:
                # w_dict =  self.load_data('question_classification/test_questions.txt',M_class)
                w_dict = {}
                type = ['DES','HUM','LOC','NUM','OBJ','TIME','UNKNOWN']
                for t in type:
                    w_dict[t] = []
                # ftra = open('data/train.json','r')
                ftra = open('train_test.txt', 'r')
                for line in ftra:
                    line_list = line.replace('"question":"', 'cut_f').replace(', "answer_pid":', 'cut_f').split('cut_f')
                    w_dict['DES'].append(line_list[1][:-2])
                # for line in ftra:
                #     line_list = line.replace('{"question": ', '').replace('"pid": ', 'cut_f').replace(
                #         '"answer_sentence": [',
                #         'cut_f').replace(
                #         '], "answer": ', 'cut_f').replace(' "qid": ', 'cut_f').replace('}', '').split('cut_f')
                #
                #     w_dict['DES'].append(line_list[0])
            for k in w_dict.keys():
                print(w_dict[k])
                for train in w_dict[k]:
                    # print(train)
                    cut_line = '\t'.join(segmentor.segment(train))
                    words_list = cut_line.split('\t')  # 分词
                    postags = postagger.postag(words_list)  # 词性标注
                    pos_line = '\t'.join(postags)
                    pos_list = pos_line.split('\t')

                    netags = recognizer.recognize(words_list, pos_list)  # 命名实体识别
                    ner_line = '\t'.join(netags)
                    ner_list = ner_line.split('\t')

                    arcs = parser.parse(words_list, pos_list)  # 句法分析
                    arcs_line = "\t".join("%d:%s" % (arc.head, arc.relation) for arc in arcs)
                    arcs_list = arcs_line.split('\t')
                    # print(arcs_list)
                    words_list = merge_base(words_list, pos_list, ner_list, arcs_list)

                    train_vec = str(types[k]) + ' '
                    for i in words_list:
                        if i in word_features:
                            if train_vec.find(str(word_features.index(i)) + ':1') == -1:
                                train_vec  += str(word_features.index(i)) + ':1 '
                    trains.append(train_vec)
            if M_class:
                if train_or_test:
                    with open('train_M.txt','w') as fr:
                        print('向量数：',len(trains))
                        for tv in trains:
                            fr.write(tv)
                            fr.write('\n')
                else:
                    with open('test_M.txt','w') as fr:
                        print('向量数：',len(trains))
                        for tv in trains:
                            fr.write(tv)
                            fr.write('\n')
            else:
                if train_or_test:
                    with open('train_S.txt','w') as fr:
                        print('向量数：',len(trains))
                        for tv in trains:
                            fr.write(tv)
                            fr.write('\n')
                else:
                    with open('test_S.txt','w') as fr:
                        print('向量数：',len(trains))
                        for tv in trains:
                            fr.write(tv)
                            fr.write('\n')

        if M_class:
            type_dict_num, type_dict, type_dict_freq,type_dict_doc = cut_segment(self.MC_dict,self.stop_word)
        else:
            type_dict_num, type_dict, type_dict_freq, type_dict_doc = cut_segment(self.SC_dict, self.stop_word)

        word_features = choose_features(type_dict_freq)
        print(word_features)
        print('特征数量：', len(word_features))
        # tf_idf = get_tf_idf(word_features,type_dict_freq,type_dict_doc)
        # return tf_idf,type_dict_num,word_features
        # write_vec(type_dict_freq, word_features, True, M_class)
        write_vec(type_dict_freq,word_features,False,M_class)

    def svm(self,M_class):
        if M_class:
            y, x = svm_read_problem('train_M.txt')
            yt, xt = svm_read_problem('test_M.txt')
            options = '-c 0.4 -q'
            model = train(y, x, options)  # 利用训练数据生产模型
            p_label, p_acc, p_val = predict(yt, xt, model, '-q')  # 利用模型预测测试数据
            types = {}
            fr = open('types_M.txt', 'r')
            for line in fr:
                k, v = line[:-1].split('\t')[0], line[:-1].split('\t')[1]
                types[int(k)] = v
            qs = []
            # fd = open('question_classification/test_questions.txt')
            # for line in fd:
            #     qs.append(line[:-1].split()[1])
            # fd = open('train_test.json','r')
            fd = open('train_test.txt','r')
            for line in fd:
                line_list = line.replace('"question":"', 'cut_f').replace(', "answer_pid":', 'cut_f').split('cut_f')
                qs.append(line_list[1][:-1])
            # for line in fd:
            #     line_list = line.replace('{"question": ', '').replace('"pid": ', 'cut_f').replace(
            #         '"answer_sentence": [',
            #         'cut_f').replace(
            #         '], "answer": ', 'cut_f').replace(' "qid": ', 'cut_f').replace('}', '').split('cut_f')
            #     qs.append(line_list[0])
            with open('qc_answer_M.txt', 'w') as fw:
                for i in range(len(p_label)):
                    fw.write(types[int(p_label[i])]+ '\t' + qs[i] + '\n')

        else:
            y, x = svm_read_problem('train_S.txt')
            yt, xt = svm_read_problem('test_S.txt')
            options = '-c 0.25 -q'
            model = train(y, x, options)  # 利用训练数据生产模型
            p_label, p_acc, p_val = predict(yt, xt, model, '-q')  # 利用模型预测测试数据
            types = {}
            fr = open('types_S.txt','r')
            for line in fr:
                k,v = line[:-1].split('\t')[0],line[:-1].split('\t')[1]
                types[int(k)] = v
            qs = []
            fd = open('question_classification/test_questions.txt')
            for line in fd:
                qs.append(line[:-1].split()[1])
            with open('qc_answer_S.txt', 'w') as fw:
                for i in range(len(p_label)):
                    fw.write(types[int(p_label[i])]+ '\t' + qs[i] + '\n')

    def bayes(self,M_class):
        def merge_base(cut,pos,ner):
            base_list = []
            for i in range(len(cut)):
                base_list.append(cut[i] + '+' + pos[i] + '+' + ner[i])
            # print(base_list)
            return base_list

        def each_q(words_q):
            # print(words_q)
            q_a = {}
            for t in types:
                q_a[t] = types_f[t]
            for word in words_q:
                if word not in self.stop_word:
                    if word in word_features:
                        word_index = word_features.index(word)
                        # print(word, word_index)
                        for t in types:
                            q_a[t] *= tf_idf[t][word_index]
            result_type = sorted(q_a.items(), key=lambda d: d[1], reverse=True)[0]
            # print(result_type[0])
            return result_type[0]
        all_qa = []
        tf_idf, type_dict_num, word_features = self.get_vec(M_class)
        types = list(type_dict_num.keys())
        types_f = {}
        for t in types:
            types_f[t] = type_dict_num[t] / self.N
            # print(types_f[t])
        segmentor = Segmentor()
        segmentor.load('cws.model')
        postagger = Postagger()  # 初始化实例
        postagger.load('pos.model')  # 加载模型
        recognizer = NamedEntityRecognizer()  # 初始化实例
        recognizer.load('ner.model')  # 加载模型
        # q = 'API的全称是什么？'
        test_dict = self.load_data('question_classification/test_questions.txt',True)

        for i in test_dict.keys():
            for j in range(len(test_dict[i])):
                q = test_dict[i][j]
                # cut_q = '\t'.join(segmentor.segment(q))
                # words_q = cut_q.split('\t')
                cut_line = '\t'.join(segmentor.segment(q))
                words_list = cut_line.split('\t')  # 分词

                postags = postagger.postag(words_list)  # 词性标注
                pos_line = '\t'.join(postags)
                pos_list = pos_line.split('\t')

                netags = recognizer.recognize(words_list, pos_list)  # 命名实体识别
                ner_line = '\t'.join(netags)
                ner_list = ner_line.split('\t')

                words_list = merge_base(words_list, pos_list, ner_list)
                type = each_q(words_list)
                # type = each_q(words_q)
                all_qa.append(type + '\t' + q)
        with open('qc_answer.txt','w') as fr:
            for i in all_qa:
                fr.write(i)
                fr.write('\n')

        self.get_precesion('question_classification/test_questions.txt', 'qc_answer.txt',M_class)

    def get_precesion(self,filename_test,filename_myanser,M_class):
        ft = open(filename_test,'r')
        ft_a  = []
        for lt in ft:
            if M_class:
                ft_a.append(lt.split()[0].split('_')[0])
            else:
                ft_a.append(lt.split()[0])

        fa = open(filename_myanser, 'r')
        fa_a = []
        for lt in fa:
            fa_a.append(lt.split()[0])

        pn = len(ft_a)
        pr = 0
        for i in range(len(ft_a)):
            if ft_a[i] == fa_a[i]:
                pr += 1

        print('测试样本数：',pr)
        print('正确样本数：',pn)
        print('准确率：', 100 * pr / pn)

    def precesion(self):
        self.get_precesion('question_classification/test_questions.txt', 'qc_answer_M.txt', True)
        self.get_precesion('question_classification/test_questions.txt', 'qc_answer_S.txt', False)


if __name__ == "__main__":
    my_qc = QC('question_classification/trian_questions.txt')
    my_qc.get_vec(True)
    my_qc.svm(True)
    # my_qc.get_vec(False)
    # my_qc.svm(False)
    my_qc.precesion()
    # my_qc.bayes(True)
    # my_qc.bayes(False)
