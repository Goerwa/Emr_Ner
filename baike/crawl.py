from urllib import request
import json
import time
import random
from bs4 import BeautifulSoup
import re
import os
import chardet
# import ssl
# ssl._create_default_https_context = ssl._create_unverified_context

all_urls = {
    'head':'https://www.baikemy.com',
    'disease':'https://www.baikemy.com/disease/list/0/0?diseaseContentType=A',
    'check':'https://www.baikemy.com/disease/list/0/0?diseaseContentType=C',
    'treat':'https://www.baikemy.com/disease/list/0/0?diseaseContentType=E',
    'prevent':'https://www.baikemy.com/disease/list/0/0?diseaseContentType=K'
}

all_info = {
    'disease':['概述','病因','临床表现','检查','诊断','治疗','预防','子词条']
}
class my_craw():
    def __init__(self,type):
        self.name = ''
        self.type = type
        self.main_url = all_urls[type]
        self.urls = []
        self.data = {}
        self.data_num = 0
        self.path = os.path.dirname(__file__)
        # 待抓取的网页数量
        self.num_news = 2
        self.per_write = 30
        self.num_now = 0

    def run(self):
        # self.get_urls()
        # self.load_urls()
        self.download_data()

    # 根据url获取数据
    def get_data(self,url):
        req = request.Request(url)
        response = request.urlopen(req)
        if response.getcode() == 200:
            return response.read()
        return None

    # 获取url
    def get_urls(self):
        url = self.main_url
        pages = 0
        time_cost = 0
        url_dict = {}
        html = self.get_data(self.main_url)
        soup = BeautifulSoup(html, 'html.parser')
        for doc in soup.find_all('a'):
            if re.match(r'/disease/detail/\d+',doc.attrs['href']) != None:
                # print(doc)
                # print(doc.string)
                # print(doc.attrs['href'])
                url_dict[pages] = {
                    'type':self.type,
                    'name':doc.string,
                    'url':all_urls['head'] + doc.attrs['href']
                }
                pages += 1
        with open(self.path+'/json_url/' + self.type + '_url.json','a',encoding='utf-8') as f:
            for i in range(len(url_dict)):
                f.write(json.dumps(url_dict[i],ensure_ascii=False))
                f.write('\n')

    def load_urls(self):
        with open(self.path+'/json_url/' + self.type + '_url.json','r') as f:
            for u in f:
                # self.urls.append(u[:-1])
                self.urls.append(json.loads(u))

    def set_load_url(self,urls):
        self.urls = urls

    def set_news_num(self,num):
        self.num_news = num

    def set_name(self,num):
        self.name = num

    def download_data(self):
        while self.num_now < self.num_news:
            test_url = self.urls[self.num_now]['url']
            name =  self.urls[self.num_now]['name']
            html = self.get_data(test_url)
            soup = BeautifulSoup(html, 'html.parser')
            if self.type == 'disease':
                self.get_disease_info(soup,name)
            self.num_now += 1
            m_time = random.random() * 3
            time.sleep(m_time)
            if self.num_now % 10 == 0:
                print(self.name + ',当前访问网页数量:',self.num_now)
            if self.num_now % self.per_write == 0:
                with open(self.path+'/json_data/' + self.type + '_data.json','a',encoding='utf-8') as f:
                    for i in range(self.per_write):
                        f.write(json.dumps(self.data[i+self.num_now - self.per_write],ensure_ascii=False))
                        f.write('\n')
        if self.num_now % self.per_write != 0:
            num_w = self.num_now % self.per_write
            with open(self.path + '/json_data/' + self.type + '_data.json', 'a', encoding='utf-8') as f:
                for i in range(num_w):
                    f.write(json.dumps(self.data[i + self.num_now - num_w], ensure_ascii=False))
                    f.write('\n')

    def get_disease_info(self,soup,name):
        dr = re.compile(r'<[^>]+>', re.S)
        self.data[self.data_num] = {
            '名称':'',
            '概述':'',
            '病因':'',
            '临床表现':'',
            '检查':'',
            '诊断':'',
            '治疗':'',
            '预防':'',
            '子词条':'',
        }
        self.data[self.data_num]['名称'] = name
        for doc in soup.find_all('h1'):
            # print(doc)
            # print(doc.string)
            if doc.string in all_info['disease']:
                for i in doc.next_siblings:
                    if i != '\n':
                        # print(0,dr.sub('',str(i)))
                        self.data[self.data_num][doc.string] = dr.sub('',str(i))
        self.data_num += 1

    def download_img(self,img_url,img_name):
        r = request.Request('https:'+img_url)
        with open('imags/' + img_name.split(':')[0] +'.jpg', 'wb') as f:
            f.write(request.urlopen(r).read())
            return True

    def write_json(self,data,now_num):
        with open(self.path+'/json_data/data.json','a',encoding='utf-8') as f:
            for i in range(now_num-self.per_write,now_num):
                f.write(json.dumps(data[i],ensure_ascii=False))
                f.write('\n')

if __name__ == '__main__':
    my_craw = my_craw('disease')
    my_craw.run()

    # test = '/disease/detail/18434478678785'
    # test1 = '/disease/detail/avx'
    # test2 = '/diseaetail/18434478678785'
    # flag = re.match(r'/disease/detail/\d+', test2)
    # print(flag)