

 配置文件

vim config/server.properties

```shell
# The id of the broker. This must be set to a unique integer for each broker.
broker.id=0
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#指定服务的端口
listeners=PLAINTEXT://192.168.0.175:9092

#如果要提供外网访问 必须配置此项producer或consumer将在此端口建立连接
advertised.listeners=PLAINTEXT://192.168.0.175:9092

zookeeper.connect=192.168.0.175:2181

hostname=192.168.0.175
```



启动 

./bin/kafka-server-start.sh config/server.properties #控制台进程启动 

./bin/kafka-server-start.sh -daemon config/server.properties #后台守护进程启动 





集群部署

```shell
#129配置 
broker.id=2 
listeners=PLAINTEXT://ydt2:9092 
advertised.listeners=PLAINTEXT://ydt2:9092 zookeeper.connect=ydt1:2181,ydt2:2181,ydt3:2181 

#130配置 
broker.id=3 
listeners=PLAINTEXT://ydt3:9092 
advertised.listeners=PLAINTEXT://ydt3:9092 zookeeper.connect=ydt1:2181,ydt2:2181,ydt3:2181
```





```xml
2.12，2.13是Scala的版本
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka_2.12</artifactId>
    <version>2.5.0</version>
</dependency>

<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>2.5.0</version>
</dependency>

<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-streams</artifactId>
    <version>2.5.0</version>
</dependency>
```