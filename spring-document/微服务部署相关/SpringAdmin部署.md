1、依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.3.0</version>
</dependency>
```

2、配置

```properties
#展示全部细节信息
management.endpoints.web.exposure.include=*
#显示健康具体信息  默认不会显示详细信息
management.endpoint.health.show-details=always
#允许admin工程远程停止本应用
management.endpoint.shutdown.enabled=true

#admin管理的端点(actuator)
spring.boot.admin.routes.endpoints=env,metrics,dump,jolokia,info,configprops,trace,logfile,refresh,flyway,liquibase,heapdump,loggers,auditevents,hystrix.stream

# 登录账号和密码
spring.security.user.name=admin
spring.security.user.password=admin
```