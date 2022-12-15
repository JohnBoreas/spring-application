1、下载zookeeper

2、解压

tar -zxvf apache-zookeeper-3.6.3-bin.tar.gz

3、配置文件

cp conf/zoo_sample.cfg conf/zoo.cfg

mv conf/zoo_sample.cfg conf/zoo_sample.cfg.bak

```shell
## 修改配置
#数据持久化目录
dataDir=/data/apache-zookeeper-3.6.3/data
#日志目录
dataLogDir=/data/apache-zookeeper-3.6.3/logs

#主机名 心跳端口 数据端口
server.1=192.168.223.5:4000:5000 
server.2=192.168.223.6:4000:5000 
server.3=192.168.223.7:4000:5000

#增加服务器号myid文件到data目录 
vim data/myid #192.168.223.5服务器写入：1 #192.168.223.6服务器写入：2 #192.168.223.7服务
```



合法服务器声明示例：

```shell
server.5 = 125.23.63.23:1234:1235;1236
server.5 = 125.23.63.23:1234:1235:participant;1236
server.5 = 125.23.63.23:1234:1235:observer;1236
server.5 = 125.23.63.23:1234:1235;125.23.63.24:1236
server.5 = 125.23.63.23:1234:1235:participant;125.23.63.23:1236
```



4、配置端口

```shell
#三台机器分别开启4000,5000和2181端口或者关闭防火墙 
#开启端口 
firewall-cmd --zone=public --add-port=2888/tcp --permanent 
firewall-cmd --zone=public --add-port=3888/tcp --permanent 
firewall-cmd --zone=public --add-port=2181/tcp --permanent 
#重启防火墙 
firewall-cmd --reload 
#分别启动三个服务 
./bin/zkServer.sh start 
#查看zookeeper状态 
./bin/zkServer.sh status
```



5、查看

```shell
## 连接
./bin/zkCli.sh -server 192.168.223.7:2181
## 查看节点
config

```

