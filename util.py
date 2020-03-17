import math
from pyltp import Segmentor

def is_estr(mystr):
    iseng = True
    for i in mystr:
        if (i.upper() > 'Z' or i.upper() < 'A'):
            iseng = False
            break
    return iseng
def is_num(mystr):
    iseng = True
    for i in mystr:
        if (i < '0' or i >'9'):
            iseng = False
            break
    return iseng
def str2lower(mystr):
    estr = ''
    for i in mystr:
        estr += i.lower()
    # print(mystr,estr)
    return estr


class BM25(object):

    def __init__(self, docs, words):
        self.D = len(docs)
        self.avgdl = sum([len(doc) + 0.0 for doc in docs]) / self.D
        self.docs = docs
        self.f = []  # 列表的每一个元素是一个dict，dict存储着一个文档中每个词的出现次数
        self.df = {}  # 存储每个词及出现了该词的文档数量
        self.idf = {}  # 存储每个词的idf值
        self.k1 = 1.5
        self.b = 0.75
        self.words = words
        self.init()

    def init(self):
        for doc in self.docs:
            tmp = {}
            for word in doc:
                tmp[word] = tmp.get(word, 0) + 1  # 存储每个文档中每个词的出现次数
            self.f.append(tmp)
            # self.words.extend(tmp)
            for k in tmp.keys():
                self.df[k] = self.df.get(k, 0) + 1

        for k, v in self.df.items():
            self.idf[k] = math.log(self.D - v + 0.5) - math.log(v + 0.5)
        # self.words = list(set(self.words))
        # print(self.words)

    def sim(self, doc, index):
        score = 0
        r = {}
        result = []
        for word in doc:
            if word not in self.f[index]:
                continue
            # print(word)
            word_n = self.words.index(word)
            d = len(self.docs[index])
            score += (self.idf[word] * self.f[index][word] * (self.k1 + 1)
                          / (self.f[index][word] + self.k1 * (1 - self.b + self.b * d / self.avgdl)))
            if word_n in r.keys():
                r[word_n] += (self.idf[word] * self.f[index][word] * (self.k1 + 1)
                          / (self.f[index][word] + self.k1 * (1 - self.b + self.b * d / self.avgdl)))
            else:
                r[word_n] = (self.idf[word] * self.f[index][word] * (self.k1 + 1)
                              / (self.f[index][word] + self.k1 * (1 - self.b + self.b * d / self.avgdl)))
        r_asc = sorted(r,reverse=False)
        for k in r_asc:
            r_str = str(k) + ':'
            # print(r[k])
            r_str += str(round(r[k],4))
            result.append(r_str)
        result.append(str(len(self.words)) + ':' + str(score))
        # print(sorted(r,reverse=False))
        # print(result)
        return result

        #     score += (self.idf[word] * self.f[index][word] * (self.k1 + 1)
        #               / (self.f[index][word] + self.k1 * (1 - self.b + self.b * d / self.avgdl)))
        #
        # return score

    def simall(self, doc):
        scores = []
        for index in range(self.D):
            score = self.sim(doc, index)
            # print(score)
            scores.append(score)
        return scores


if __name__ == "__main__":
    # print(is_estr('保存'))
    # print(is_estr('kenzo'))
    # print(str2lower('afaHJJB'))
    # print(str2lower('JJJJHJJB'))
    # print(str2lower('afa'))
    text = '''
    自然语言处理是计算机科学领域与人工智能领域中的一个重要方向。
    它研究能实现人与计算机之间用自然语言进行有效通信的各种理论和方法。
    自然语言处理是一门融语言学、计算机科学、数学于一体的科学。
    因此，这一领域的研究将涉及自然语言，即人们日常使用的语言，
    所以它与语言学的研究有着密切的联系，但又有重要的区别。
    自然语言处理并不是一般地研究自然语言，
    而在于研制能有效地实现自然语言通信的计算机系统，
    特别是其中的软件系统。因而它是计算机科学的一部分。
    '''
    doc = text.split()
    print(doc)
    doc_list = []
    segmentor = Segmentor()
    segmentor.load('cws.model')
    for d in doc:
        cut_line = '\t'.join(segmentor.segment(d))
        words_list = cut_line.split('\t')  # 分词
        doc_list.append(words_list)
    print(doc_list)
    s = BM25(doc_list)
    print(s.f)
    print(s.idf)
    print(s.simall(['自然语言', '计算机科学', '领域', '人工智能', '领域']))


