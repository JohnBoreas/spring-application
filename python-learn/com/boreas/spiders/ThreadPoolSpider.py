from concurrent.futures import ThreadPoolExecutor
import requests

def fetch_async(url):
    response = requests.request("GET", url)

    return response


def callback(future):
    print(future.result().text)


url_list = [
    'https://www.baidu.com',
    'https://www.bing.com',
    'https://www.cnblogs.com/'
]

pool = ThreadPoolExecutor(5)
# pool = ProcessPoolExecutor(10) 线程池
for url in url_list:
    v = pool.submit(fetch_async, url)
    #这里调用回调函数
    v.add_done_callback(callback)

pool.shutdown()
