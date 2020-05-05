微服务架构：是一种架构模式或者一种架构风格，提倡将单一的应用程序划分成一组小的服务，每个服务独立运行于自己的进程之中

服务之间基于http的restful api调用

```
拆分
独立进程
```

优缺点

```
服务内聚，小，聚焦到一个指定的业务功能或需求
开发简单，效率提高
能被小团队单独开发
松耦合，开发部署独立
可以使用不同语言开发
微服务只是业务逻辑代码，不会和html，css或其他节目组件混合

运维成本增加
通信成本增加
性能监控
数据一致性
部署依赖
```

技术栈

| 微服务条目          | 技术                               |
| ------------------- | ---------------------------------- |
| 服务开发            | springboot、spring、springmvc      |
| 服务配置与管理      | netflix的archaius、阿里的diamond   |
| 服务注册与发现      | eureka、consul、zookeeper          |
| 服务调用            | restrpc、grpc                      |
| 服务熔断器          | hystrix、envoy                     |
| 负载均衡            | nginx、ribbon                      |
| 服务接口调研        | feign                              |
| 消息队列            | kafka、rabbitmq、activemq          |
| 服务配置中心管理    | springcloudconfig、chef            |
| 服务路由（api网关） | zuul                               |
| 服务监控            | zabbut、nagios、metrics、spectator |
| 服务部署            | docker、openstack、kubernetes      |
| 数据流操作开发包    | sprngcloudstream                   |
| 事件消息总线        | spring cloud bus                   |



spring cloud是基于spring boot的一整套的微服务解决方案

```
https://www.springcloud.cc/
https://www.springcloud.cn/
```

