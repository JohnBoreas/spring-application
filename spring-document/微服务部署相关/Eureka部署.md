1、@EnableEurekaServer与@EnableDiscoveryClient

@EnableEurekaServer可以打开控制台，http://localhost:8005/

@EnableDiscoveryClient会报以下错误错，打不开控制台

```java
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.

```



2、服务有时候会down掉，但是服务还能用



3、项目配置

application.properties

```properties
spring.application.name=equinox-discovery
# 端口
server.port=8005
# 地址
eureka.instance.hostname=localhost

# 设置服务注册中心的URL，用于client和server端通信
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:8003/eureka/,http://${eureka.instance.hostname}:8004/eureka/

# 是否将自己注册到Eureka Server, 默认为true，由于当前就是server，故而设置成false，表明该服务 不会向eureka注册自己的信息
eureka.client.register-with-eureka=true

# 是否从eureka server获取注册信息，由于单节点，不需要同步其他节点数据，用false
eureka.client.fetch-registry=true

```

