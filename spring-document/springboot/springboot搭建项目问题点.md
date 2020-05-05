**spring-boot-web搭建**

​		项目搭建只需要建好一个空的maven工程即可，剩下的全部靠依赖完成，java项目就jar，web项目就是war，多模块就建好一个父pom

一、引入数据库模块

```java
1.不需要操作数据库的模块由于引用其他公共模块，公共模块中依赖mybatis后，导致运行报错Failed to configure a DataSource。

2.引入数据库模块后，需要在application.properties里配置相应的数据库，不然会报错

// 解决办法：
1、在@SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration。
    
@EnableEurekaServer
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
2、在parent项目的pom.xml文件中保存所有子模块的共有jar依赖，非共有的依赖则在各模块自身的pom.xml文件中进行申明。建议采用此方法，好处在于各模块的依赖不会相互产生干扰。
```



二、项目启动运行

1、直接执行被@SpringBootApplication注解的类

2、maven运行，通过`mvn spring-boot:run`启动项目，idea中配置maven命令spring-boot:run启动



三、引用依赖

```xml
(1)
<dependencyManagement>
    <dependencies>
        <dependency>
            <!-- Import dependency management from Spring Boot -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.2.4.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
(2)
<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
	</parent>
```



四、关于配置文件

```shell
## application.yml 和 application.properties 两个文件的优先级
application.yml 文件会被优先加载，

而如果同时存在 application.properties 文件，并且存在相同的配置，

那么则会用 application.properties 文件中的配置覆盖之前的配置；

也就是说哪个文件被最后加载，哪个才具有最高级别
```

