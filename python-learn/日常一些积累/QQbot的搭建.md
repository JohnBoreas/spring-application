一、go-cqhttp

1、关于go-cqhttp

相当于一个小的qq，内部原理是通过调用腾讯的开放接口实现的qq登录，发送消息等功能

内嵌有定时任务，能一直获取消息，然后通过接口形式将消息发送给其他的端

主要作用是用于监控获取QQ消息



2、地址

https://docs.go-cqhttp.org/guide/



3、安装

windows安装

1）双击`go-cqhttp_*.exe`，根据提示生成运行脚本

2）双击运行脚本，生成config.yml文件，然后修改文件中的信息

3）参照[config.md (opens new window)](https://github.com/Mrs4s/go-cqhttp/blob/master/docs/config.md)和你所用到的插件的 `README` 填入参数



4、调试

使用powershell运行（powershell安装闪退问题）不行就用cmd运行

.\go-cqhttp.exe faststart 或者点击go-cqhttp.bat运行

能调用接口发送消息就说明成功了，使用postman调用接口

```http
http://127.0.0.1:5700/send_group_msg?group_id=390788021&message=有没有人发点涩图呀，rabbit酱想看涩图
```



二、nonebot

1、关于nonebot

就是机器人的实现，内置有定时器线程池，不断的监听端口，获取QQ信息，并根据规则处理这些消息，调用接口发送消息

2、安装（其实就是下载python包）

```bash
D:\Python\python.exe -m pip install --upgrade pip
```

后面就是







https://blog.csdn.net/u011897679/article/details/114607381