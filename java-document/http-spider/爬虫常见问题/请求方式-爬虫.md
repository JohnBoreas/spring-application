#### 前言

```
工作中会常用ping来检测是否能链接某个域名，检测可达性
通过telnet检测某个端口是否可用，通常用于数据库，接口等是否可用的测试
crul通常是请求方式之一
```



#### tcpdump



####  traceroute

知道信息从你的计算机到互联网另一端的主机是走的什么路径

linux为traceroute,在MS Windows中为tracert

安装： yum -y install traceroute 命令安装下（root权限在线安装）

```shell
## -n 显示IP地址，不查主机名，  -m 设置跳数  
## -q 4每个网关发送4个数据包    -w 把对外发探测包的等待响应时间设置为3秒
traceroute -n -m 5 -q 4 -w 3 www.baidu.com
## 探测包使用的基本UDP端口设置6888
traceroute -p 6888 www.baidu.com
## 绕过正常的路由表，直接发送到网络相连的主机
traceroute -r www.baidu.com
```

#### Ping

​	PING （Packet Internet Groper），因特网包探索器，用于测试网络连接量的程序 。Ping是工作在 TCP/IP网络体系结构中应用层的一个服务命令， 主要是向特定的目的主机发送 ICMP（Iternet Control Message Protocol 因特网报文控制协议）Echo 请求报文，测试目的站是否可达及了解其有关状态

作用：

（1）用来检测网络的连通情况和分析网络速度；

（2）根据域名得到服务器IP；

（3）根据ping返回的TTL值来判断对方所使用的操作系统及数据包经过路由器数量。

```powershell
## -l size：发送size指定大小的到目标主机的数据包, windows默认发送数据包为32byt，最大发送65500byt
ping -l 65500 -t 211.84.7.46
## ping -r count,记录传出和返回数据包的路由，探测经过的路由个数,最多跟踪9个路由
ping -n 1 -r 9 202.102.224.25 
## 批量ping网段检测,检测网段10.168.1.1到10.168.1.255之间的所有的ip地址，每次逐增1
for /L %D in (1,1,255) do ping 10.168.1.%D
```

#### curl

​	curl是一种命令行工具，作用是发出网络请求，然后得到和提取数据，显示在"标准输出"（stdout）上面。

```powershell
例子：
curl -x 192.168.0.1:31280 -L -v https://item.taobao.com/item.htm?ft=t&id=37267511838

## 查看网页源码
curl www.sina.com
curl -o [文件名] www.sina.com
## 自动跳转，允许重定向
curl -L www.sina.com
## 使用代理
curl -x 192.168.0.1:31280 https://www.example.com
## 参数指定 HTTP 请求的方法
curl -X POST https://www.example.com

显示通信过程
## 显示头信息
curl -i www.sina.com
## `-v`参数可以显示一次http通信的整个过程，包括端口连接和http request头信息
curl -v www.sina.com
curl --trace output.txt www.sina.com
curl --trace-ascii output.txt www.sina.com

## header
curl --referer http://www.example.com [URL]
curl --user-agent "[User Agent]" [URL]
curl --cookie "name=xxx" [URL]
curl --header "Content-Type:application/json" http://example.com
curl -H 'Accept-Language: en-US' -H 'Secret-Message: xyzzy' https://google.com
```

#### telnet

​	telnet命令通常用来远程登录。telnet程序是基于TELNET协议的远程登录客户端程序。Telnet协议是TCP/IP协议族中的一员，是Internet远程登陆服务的标准协议和主要方式。它为用户提供了在本地计算机上完成远程主机工作的能力。

```powershell
telnet就是查看某个端口是否可访问。
# 安装服务
yum install telnet –y
# 启动服务
service xinetd restart<!--EndFragment-->
# 查看远方服务器ssh端口是否开放：
如果端口关闭或者无法连接，则显示不能打开到主机的链接，链接失败；端口打开的情况下，链接成功，则进入telnet页面（全黑的），证明端口可用。
telnet 192.168.25.133 22

```

​	telnet用于远程登录到网络中的计算机，并以命令行的方式远程管理计算机。需要注意的是，远程机器必须启动telnet服务器，否则无法打开telnet命令。