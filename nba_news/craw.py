from urllib import request
import json
import time
import random
from bs4 import BeautifulSoup
import re
import os
import chardet
import ssl
ssl._create_default_https_context = ssl._create_unverified_context

class hupu_craw():
    def __init__(self,url):
        self.main_url = url
        self.urls = []
        self.data = {}
        self.path = os.path.dirname(__file__)
        self.num_news = 5000
        self.per_write = 100
        self.num_now = 0

    def run(self):
        # self.get_news_url()
        self.load_urls('url.txt')
        self.download_data()

    # 根据url获取数据
    def get_data(self,url):
        req = request.Request(url)
        response = request.urlopen(req)
        if response.getcode() == 200:
            return response.read()
        return None

    # 获取url
    def get_news_url(self):
        url = self.main_url
        pages = 1
        time_cost = 0
        while len(self.urls) < self.num_news:
            if len(self.urls) == 0:
                html = self.get_data(self.main_url)
            else:
                next_url = url[:-4]
                next_url_tail = url[-4:]
                html = self.get_data(next_url + '_' + str(pages) + next_url_tail)
            soup = BeautifulSoup(html, 'html.parser',from_encoding='iso-8859-1')
            soup.decode('GBK')
            for doc in soup.find_all('script'):
                doc_str = str(doc.string)
                if doc_str.find('ARTICLE_LIST') != -1:
                    find_url = re.search(r'\{title:.+\}',doc_str)
                    if find_url:
                        for n in find_url.group().split(','):
                            if n[:3] == 'url':
                                self.urls.append(n[4:])
            print('总用时为：' + str(time_cost) + '待抓取的网页总数为： ' + str(len(self.urls)))
            m_time = random.random() * 3
            time.sleep(m_time)
            time_cost += m_time
            pages += 1

        with open('url.txt','w',encoding='utf-8') as f:
            for u in self.urls:
                f.write(u)
                f.write('/n')

    def load_urls(self,filename):
        with open('url.txt','r',encoding='utf-8') as f:
            for u in f:
                self.urls.append(u[:-1])
        # for i in self.urls:
        #     print(i)

    def download_data(self):
        urls_num = len(self.urls)
        num_now = 0
        time_cost = 0
        while num_now < urls_num:
            img_list = []
            url = ''
            title = ''
            paragraphs = ''
            file_name = ''
            url = self.urls[num_now]
            # url = self.urls[4]
            html = self.get_data(url)
            charset = chardet.detect(html)
            # print(charset)
            if charset['encoding'] == None:
                m_time = random.random()
                time.sleep(m_time)
                time_cost += m_time
                continue
            soup = BeautifulSoup(html, 'html.parser',from_encoding=charset['encoding'])
            # soup.decode('GBK').encode('utf-8')
            # soup.decode('GBK')
            # print(soup)
            for doc in soup.find_all('title'):
                title = doc.string
            for doc in soup.find_all('p',style=re.compile(r'TEXT-INDENT:')):
                doc_str = doc.string
                if doc_str != None:
                    paragraphs += doc_str + '\n'
                # print(paragraphs)
            for doc in soup.find_all('p'):
                doc_str = str(doc)
                img = doc.find('img')
                if img:
                    img_url = img.attrs['src']
                    # print(img.attrs)
                    t_d = doc.next_sibling
                    if t_d:
                        img_doc = t_d.string
                        # print(img_doc)
                        if img_doc == None:
                            continue
                        img_name = img_url.split('/')[-2] + '-' + str(len(img_list)) + ':' + img_doc
                        if self.download_img(img_url,img_name):
                            img_list.append(img_name)
                        # print(img_name)
            self.data[len(self.data)] = {
                'url': url,
                'title': title,
                'parapraghs': paragraphs,
                'file_name': img_list
            }
            m_time = random.random() * 3
            time.sleep(m_time)
            time_cost += m_time
            # if paragraphs == '' and title == '':
            #     continue
            num_now += 1
            print('时间: ' + str(time_cost) + '抓取新闻数量为：' + str(num_now) + '   ' + '当前网页为: ' + url)
            if num_now % self.per_write == 0:
                self.write_json(self.data, num_now)
                print('写入json文件——————————总文件数为： ' + str(num_now))

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
    my_craw = hupu_craw('https://sports.qq.com/l/basket/nba/list20181018164449.htm')
    my_craw.run()