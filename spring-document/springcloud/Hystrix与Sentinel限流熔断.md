### 一、基础

Hystrix 

关注点在于以 隔离 和 熔断 为主的容错机制，超时或被熔断的调用将会快速失败，并可以提供 fallback 机制。



Sentinel 

侧重点在于：多样化的流量控制、熔断降级、系统负载保护、实时监控和控制台



### 二、配置

#### Hystrix

1、配置

（1）pom.xml

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

（2）application.properties

```properties
feign.hystrix.enabled=true
```

（3）启动类

@EnableCircuitBreaker，启用断路器

@EnableFeignClients，扫描@FeignClient注解



其底层的执行是基于 RxJava 实现的

隔离策略（线程池隔离 or 信号量隔离）

线程池隔离，需要配置线程池对应的参数（线程池名称、容量、排队超时等）

信号量隔离，需要配置最大并发数，执行 Command 时 Hystrix 就会限制其并发调用



#### Sentinel

先通过 Sentinel API 给对应的业务逻辑定义资源（埋点），然后可以在需要的时候配置规则。

埋点方式有两种：

```
try-catch 方式（通过 SphU.entry(...) ），用户在 catch 块中执行异常处理 / fallback
if-else 方式（通过 SphO.entry(...) ），当返回 false 时执行异常处理 / fallback
```

##### （1）pom.xml

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

##### （2）application.properties

```properties
feign.sentinel.enabled=true
#禁用Sentinel 的赖加载
spring.cloud.sentinel.eager=true
# 配置Sentinel
spring.cloud.sentinel.transport.dashboard=localhost:10001
```

##### （3）使用

代码方式

```java
Entry entry = null;
try {
    entry = SphU.entry(SentinelConstants.Resource.EXCEPTION_SENTINEL);
} catch (BlockException e) {
    //如果抛异常了，则表示被限流了
} finally {
    if (entry != null) {  entry.exit(); }// 退出资源
}
```

注解方式

```java
// 设置资源，以及限流方法
@SentinelResource(value = SentinelConstants.Resource.ANNOTATION_SENTINEL, 
                  blockHandler = "blockHandler")
```

##### （4）FlowRule配置（限流）

```java
// resource：资源名，即限流规则的作用对象
// limitApp：流控针对的调用来源，若为default则不区分调用来源
// grade：限流阈值类型，即QPS还是并发线程数，0代表线程数，1代表QPS
// count：限流阈值
// strategy：调用关系限流策略
// controlBehavior：流量控制效果（快速失败，Warm Up，排队等待）
// clusterMode：是否集群
```

##### （5）DegradeRule（熔断降级）

1）RT（平均响应时间）( DEGRADE_GRADE_RT )

当 1s 内持续进入 N 个请求，对应时刻的平均响应时间（秒级）
均超过阈值（ count ，以 ms 为单位），那么在接下的时间窗口（ DegradeRule 中的 timeWindow ， 以 s 为单位）之内，对这个方法的调用都会自动地熔断（抛出 DegradeException ）。

2）异常比例 ( DEGRADE_GRADE_EXCEPTION_RATIO )

当资源的每秒请求量 >= N（可配置），并且每秒异
常总数占通过量的比值超过阈值（ DegradeRule 中的 count ）之后，资源进入降级状态，即在接下的
时间窗口（ DegradeRule 中的 timeWindow ，以 s 为单位）之内，对这个方法的调用都会自动地返
回。异常比率的阈值范围是 [0.0, 1.0] ，代表 0% - 100%。

3）异常数( DEGRADE_GRADE_EXCEPTION_COUNT )

当资源近 1 分钟的异常数目超过阈值之后会进行熔断。
注意由于统计时间窗口是分钟级别的，若 timeWindow 小于 60s，则结束熔断状态后仍可能再进入熔断状态。



区别：

| 功能           | Sentinel                                                   | Hystrix                 | resilience4j                     |
| :------------- | :--------------------------------------------------------- | :---------------------- | :------------------------------- |
| 隔离策略       | 信号量隔离（并发线程数限流）                               | 线程池隔离/信号量隔离   | 信号量隔离                       |
| 熔断降级策略   | 基于响应时间、异常比率、异常数                             | 基于异常比率            | 基于异常比率、响应时间           |
| 实时统计实现   | 滑动窗口（LeapArray）                                      | 滑动窗口（基于 RxJava） | Ring Bit Buffer                  |
| 动态规则配置   | 支持多种数据源                                             | 支持多种数据源          | 有限支持                         |
| 扩展性         | 多个扩展点                                                 | 插件的形式              | 接口的形式                       |
| 基于注解的支持 | 支持                                                       | 支持                    | 支持                             |
| 限流           | 基于 QPS，支持基于调用关系的限流                           | 有限的支持              | Rate Limiter                     |
| 流量整形       | 支持预热模式、匀速器模式、预热排队模式(流量规则处可配置)   | 不支持                  | 简单的 Rate Limiter 模式         |
| 系统自适应保护 | 支持                                                       | 不支持                  | 不支持                           |
| 控制台         | 提供开箱即用的控制台，可配置规则、查看秒级监控、机器发现等 | 简单的监控查看          | 不提供控制台，可对接其它监控系统 |