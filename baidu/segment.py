import time
import os
import json


def ltp_seg():
    """
    使用LTP 对json文件中的标题和正文进行分词
    """
    LTP_DATA_DIR = 'D:\BaiduNetdiskDownload\ltp_data_v3.4.0'  # ltp模型目录的路径
    cws_model_path = os.path.join(LTP_DATA_DIR, 'cws.model')  # 分词模型路径，模型名称为`cws.model`

    from pyltp import Segmentor
    segmentor = Segmentor()  # 初始化实例
    segmentor.load(cws_model_path)  # 加载模型
    # 读入json文件
    with open('data/data.json', encoding='utf-8') as fin:
        read_results = [json.loads(line.strip()) for line in fin.readlines()]
    # 读取停用词
    stopwords = read_stop_word()
    # 对标题和正文进行分词
    result = []
    start = time.time()  # 计时
    for res in read_results:
        tmp = {}
        tmp['url'] = res['url']
        # 标题分词
        title_words = segmentor.segment(res['title'])
        tmp['segmented_title'] = ' '.join(title_words)
        # 正文分词
        words = segmentor.segment(res['paragraphs'])
        # 去除正文中的停用词
        remove_stop_words = [word for word in words if word not in stopwords]
        tmp['segmented_paragraphs'] = ' '.join(remove_stop_words)
        tmp['file_name'] = res['file_name']
        result.append(tmp)
        # print(res['paragraphs'])
    end = time.time()  # 计时
    # 写回json文件
    with open('data/preprocessed.json', 'w', encoding='utf-8') as fout:
        for sample in result:
            fout.write(json.dumps(sample, ensure_ascii=False) + '\n')
    segmentor.release()  # 释放模型
    print("LTP seg done, use time: {}s".format(end - start))


def read_stop_word():
    """
    读取停用词
    :return:
    """
    stopwords = set()
    with open('data/stopwords(new).txt', 'r', encoding='utf-8') as f:
        for line in f:
            stopwords.add(line.strip())
    print("stopwords num:{}".format(len(stopwords)))
    return stopwords


if __name__ == '__main__':
    ltp_seg()
