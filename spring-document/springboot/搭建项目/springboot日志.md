application.yml

```yml
logging:
  level:
    com.kanon: 包名
```

logback.xml

```xml
<!-- 系统模块日志级别控制  -->
<logger name="包名" level="info" />
<!-- Spring日志级别控制  -->
<logger name="org.springframework" level="warn" />
```



application.yml优先级大于logback.xml