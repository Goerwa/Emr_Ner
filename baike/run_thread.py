import threading
from crawl import my_craw
import os
import json



class MyThread(threading.Thread):
    def __init__(self, threadID, name, type, url):
        super(MyThread, self).__init__()
        self.threadID = threadID
        self.name = name
        self.type = type
        self.url = url

    def run(self):
        craw = my_craw(type)
        craw.set_name(self.name)
        craw.set_load_url(self.url)
        # print(len(self.url))
        craw.set_news_num(len(self.url))
        craw.run()

def load_urls(type):
    urls = []
    with open(os.path.dirname(__file__) + '/json_url/' + type + '_url.json', 'r') as f:
        for u in f:
            # self.urls.append(u[:-1])
            urls.append(json.loads(u))
    return urls

if __name__ == '__main__':
    type = 'disease'
    urls = load_urls(type)
    thread_num = 10 # 线程数量
    news_num = len(urls) # 抓取总数
    per_num = int(news_num / thread_num)
    threads = []

    for i in range(thread_num):
        name = 'Thread-' + str(i)
        # MyThread(i, name, per_num * i).start()
        if i == thread_num - 1:
            cut_list = urls[per_num * i:]
            t = MyThread(i, name, type, cut_list)
        else:
            cut_list = urls[per_num * i:per_num * (i+1)]
            t = MyThread(i, name, type, cut_list)
        t.start()
        threads.append(t)
    print('线程数量为:',len(threads))
    print('每个线程抓取数量为：',per_num)
    for t in threads:
        t.join()
    print('所有数据爬取完成')