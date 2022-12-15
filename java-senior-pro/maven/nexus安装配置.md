1、官网下载

https://help.sonatype.com/repomanager3/product-information/download/download-archives---repository-manager-3

2、解压文件

```shell
tar -zxvf nexus.tar
```

3、文件配置

```shell
cd nexus-3.29.0-01
## 配置java路径
vim bin/nexus
INSTALL4J_JAVA_HOME_OVERRIDE=/data/search/jdk1.8.0_111

## 配置系统参数
vim bin/nexus.vmoptions
-Dkaraf.data=../sonatype-work/nexus3
-Djava.io.tmpdir=../sonatype-work/nexus3/tmp
-XX:LogFile=../sonatype-work/nexus3/log/jvm.log

## 服务端口路径
vim etc/nexus-default.properties
application-port=8081
application-host=0.0.0.0
```



4、默认密码在一个文件里

```shell
cd sonatype/nexus3

admin.password
```

