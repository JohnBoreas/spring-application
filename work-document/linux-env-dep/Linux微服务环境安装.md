Nacos

```shell
# java
tar -zxvf jdk-8u161-linux-x64.tar.gz -C /usr/local/ 
ln -s /usr/local/jdk1.8.0_161/ /usr/local/java 
vim /etc/profile

## 添加环境变量
export JAVA_HOME=/usr/local/java/ 
export JRE_HOME=${JAVA_HOME}/jre 
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin

source /etc/profile

iptables -I IN_public_allow -p tcp --dport 8848 -j ACCEPT
iptables -I IN_public_allow -p tcp --dport 9849 -j ACCEPT
```





LInux永久开放端口

```
1、开启防火墙 
    systemctl start firewalld

2、开放指定端口
    firewall-cmd --zone=public --add-port=9849/tcp --permanent
 命令含义：
--zone #作用域
--add-port=80/tcp  #添加端口，格式为：端口/通讯协议
--permanent  #永久生效，没有此参数重启后失效

重新载入
firewall-cmd --reload

查看
firewall-cmd --zone= public --query-port=80/tcp

删除
firewall-cmd --zone= public --remove-port=80/tcp --permanent
```



