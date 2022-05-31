

Eureka的基本架构，由3个角色组成

1、**Eureka Server** 提供服务注册和发现

2、**Service Provider** 服务提供方 将自身服务注册到Eureka，从而使服务消费方能够找到

3、**Service Consumer** 服务消费方 从Eureka获取注册服务列表，从而能够消费服务



Eureka原理

1、Application Service 相当于服务提供者，Application Client相当于服务消费者；

2、Make Remote Call，可以简单理解为调用RESTful API；

3、us-east-1c、us-east-1d等都是zone，它们都属于us-east-1这个region；

![在这里插入图片描述](..\resource\eureka.png)

region：可以简单理解为地理上的分区

zone：可以简单理解为region内的具体机房



Eureka Client：一个Java客户端，用于简化与Eureka Server的交互；Eureka Server提供服务发现

的能力，各个微服务启动时，会通过Eureka Client向Eureka Server进行注册自己的信息（例如网

络信息），Eureka Server会存储该服务的信息



Eureka Server ：每个Eureka Server同时也是Eureka Client，多个Eureka Server之间通过复制的方式完成服务注

册表的同步；



心跳检测：Eureka Client会周期性地向Eureka Server发送心跳（默认周期为30秒）以续约自己的信息

健康检查：Eureka Server在一定时间内没有接收到某个微服务节点的心跳，Eureka Server将会注销该微服

务节点（默认90秒）;

客户端缓存：Eureka Client会缓存Eureka Server中的信息。即使所有的Eureka Server节点都宕掉，服务消费

者依然可以使用缓存中的信息找到服务提供者