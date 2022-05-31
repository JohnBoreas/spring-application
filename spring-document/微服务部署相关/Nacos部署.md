一、数据库

nacos支持配置多个数据库，通过 *db.num* 和 *db.url.index*的配置来控制。



二、java安装

```shell
yum -y list java*
yum install java-1.8.0-openjdk.x86_64

export JAVA_HOME=/usr/local/java/ 
export JRE_HOME=${JAVA_HOME}/jre 
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
```

三、集群部署

1、配置cluster.conf

```shell
#it is ip
192.168.223.5:8848
192.168.223.6:8848
192.168.223.7:8848
```

2、配置application.properties

```shell
### Count of DB:
db.num=1
db.url.0=jdbc:mysql://192.168.16.102:3306/equinox_nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=123456
```



四、注意事项：

使用spring.cloud的依赖会设置成默认分组

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
#服务分组   这个配置会失效
spring.cloud.nacos.discovery.group=discovery-group
改成
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```



五、把springboot项目改nacos

只能作为配置中心

