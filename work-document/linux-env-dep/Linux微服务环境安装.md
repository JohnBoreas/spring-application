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

