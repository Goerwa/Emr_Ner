# from libsvm.svmutil import *
# from libsvm.svm import *
# from libsvm.commonutil import *
from liblin.liblinearutil import *
from liblin.commonutil import *
from pyltp import Segmentor,Postagger,NamedEntityRecognizer,SementicRoleLabeller,Parser
import os
from L2R import *
def merge_base(cut, pos, ner, arcs_list):
    base_list = []
    cw = []
    dr = []
    cw_w = []
    for i in range(len(cut)):
        base_list.append(cut[i] + '/' + pos[i])
    for i in range(len(arcs_list)):
        cw.append(arcs_list[i].split(':')[0])
        dr.append(arcs_list[i].split(':')[1])
    for i in range(len(cw)):
        if cw[i] != '0':
            base_list.append(cut[i] + '/' + cut[int(cw[i]) - 1])
        else:
            base_list.append(cut[i]+ '/' )
    cw = list(set(cw))
    dr = list(set(dr))
    for ci in cw:
        base_list.append(cut[int(ci) - 1])
    for di in dr:
        base_list.append(di)

    # print(base_list)
    # base_list.append(cut[i])
    # print(base_list)
    return base_list

def ltp(t_str):
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

    cut_line = '\t'.join(segmentor.segment(t_str))
    words_list = cut_line.split('\t')  # 分词

    postags = postagger.postag(words_list)  # 词性标注
    pos_line = '\t'.join(postags)
    pos_list = pos_line.split('\t')

    netags = recognizer.recognize(words_list, pos_list)  # 命名实体识别
    ner_line = '\t'.join(netags)
    ner_list = ner_line.split('\t')

    arcs = parser.parse(words_list, pos_list)  # 句法分析
    arcs_line = "\t".join("%d:%s" % (arc.head,arc.relation) for arc in arcs)
    arcs_list = arcs_line.split('\t')
    i = 0
    for word, arc in zip(words_list, arcs):
        i = i + 1
        print(str(i) + '/' + word + '/' + str(arc.head) + '/' + str(arc.relation))

    # roles = labeller.label(words_list, pos_list, arcs)  # 语义角色标注
    # for role in roles:
    #     print(role.index, "".join(
    #         ["%s:(%d,%d)" % (arg.name, arg.range.start, arg.range.end) for arg in role.arguments]))

    words_list = merge_base(words_list, pos_list, ner_list, arcs_list)
    print(words_list)

def list2str(l):
    str = ' '
    for i in l:
        str += i + ' '
    return str
def adjust(l):
    r_l = []
    for i in range(len(l)):
        i_l = l[i].split()
        lstr = ''
        for i in range(len(i_l)):
            # print(i_l[i].split(':')[0])
            if i_l[i].split(':')[0] == '174027':
                lstr += i_l[i].split(':')[0] + ':' + str(round(float(i_l[i].split(':')[1]) * 4,3)) + ' '
            elif i_l[i].split(':')[0] == '1' and len(i_l[i]) > 1:
                lstr += i_l[i].split(':')[0] + ':' + str(round(float(i_l[i].split(':')[1]) / 3,3)) + ' '
                # pass
            elif i_l[i].split(':')[0] == '2' and len(i_l[i]) > 1:
                lstr += i_l[i].split(':')[0] + ':' + str(round(float(i_l[i].split(':')[1]) / 3,3)) + ' '
                # pass
            elif i_l[i].split(':')[0] == '3' and len(i_l[i]) > 1:
                lstr += i_l[i].split(':')[0] + ':' + str(round(float(i_l[i].split(':')[1]) / 3,3)) + ' '
                # pass
            elif len(i_l[i]) > 1:
                lstr += i_l[i].split(':')[0] + ':' + str(round(float(i_l[i].split(':')[1]) * 2,3)) + ' '
            else:
                lstr += i_l[i] + ' '
        r_l.append(lstr)
    return r_l
def get_test():
    train_data = []
    test_data = []
    train_group = []
    test_group = []
    train_data1 = []
    test_data1 = []
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
    train_data = adjust(train_data)
    test_data = adjust(test_data)
    i = 0
    j = 0
    n = 0
    for g in train_group:
        n += 1
        i = j
        j = j + int(g)
        for t in train_data[i:j]:
            train_data1.append(t.split()[0] + ' qid:'+ str(n) +list2str(t.split()[1:]))
    i = 0
    j = 0
    n = 0
    for g in test_group:
        n += 1
        i = j
        j = j + int(g)
        for t in test_data[i:j]:
            test_data1.append(t.split()[0] + ' qid:' + str(n) + list2str(t.split()[1:]))
    with open('l2r/train.txt','w') as ftr:
        for i in train_data1:
            ftr.write(i)
            ftr.write('\n')
    with open('l2r/test.txt','w') as fte:
        for i in test_data1:
            fte.write(i)
            fte.write('\n')

if __name__ == "__main__":
    # t_str = '黑龙江的省会在哪个城市？'
    # ltp(t_str)
    # svm(False)
    get_test()
    os.system('svm_rank/svm_rank_learn -c 30 -t 0 l2r/train.txt l2r/model.dat')
    os.system('svm_rank/svm_rank_classify l2r/test.txt l2r/model.dat l2r/predictions.txt')
    my_l2r = L2R()
    my_l2r.get_precision()