Spring Boot Actuator 模块提供了生产级别的功能，比如健康检查，审计，指标收集，HTTP 跟踪等，

是一个采集应用内部信息暴露给外部的模块



提供HTTP与JMX两种方式



默认情况，只放开info，health，其它访问



#### 开放配置

```yml
management:
  server:
    port: 8081              # 自定义管理服务器端口, http://localhost:8081/path/health
  endpoints:
    web:
      base-path: /path      # 自定义端点根路径, http://localhost:8080/path/health
      exposure:
        include: "*"        # #开放所有web端点  默认只开启了health、info两个节点
        exclude: env,info   # #禁用部分端点
    health:
      show-details: always  # #设置always显示健康具体信息  默认never不会显示详细信息
```



原理：

相当于不停的执行一个命令去尝试链接

```java
private Mono<Health> doHealthCheck(Health.Builder builder, ReactiveRedisConnection connection) {
   boolean isClusterConnection = connection instanceof ReactiveRedisClusterConnection;
   return connection.serverCommands().info("server").map((info) -> up(builder, info, isClusterConnection))
         .onErrorResume((ex) -> Mono.just(down(builder, ex)))
         .flatMap((health) -> connection.closeLater().thenReturn(health));
}
```



#### metrics监控

主要是系统运行的状态值进行监控。

监控对象：

​		》jvm【垃圾收集器、内存、堆】

​		》系统层面【运行时间，平均负载。。】

​		》线程池的状态



#### loggers监控

主要用来展示不同包路径设置的日志级别

http://localhost:8080/actuator/loggers

它的作用有：

如果在线上想去排查问题，要查看日志的详细信息，提供一种方式可以修改，

可以对特定节点的日志级别进行修改





### JMX

是Java Management Extensions(Java管理扩展)的缩写，是一个为应用程序植入管理功能的框架。

用户可以在任何Java应用程序中使用这些代理和服务实现管理。使用jmx可以做一些监控

#### 配置

```xml
#启用对外提供基于JMX的endpoints
spring.jmx.enabled=true
#http.enable=true
#开发所有jmx端点
management.endpoints.jmx.exposure.include=*
```



连接：

jconsole