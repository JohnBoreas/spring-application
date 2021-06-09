多模块项目依赖

一、在parent model中，使用dependencyManagement预定义所有模块需要用到的dependency(依赖)

```xml
<dependencyManagement>
    <dependencies>
        <!-- Feign是一种声明式、模板化的HTTP客户端:以HTTP接口的形式暴露自身服务 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
            <version>${spring-cloud-starter-feign.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

子model根据实际需要引入parent中预定义的依赖

当前Module需要用到的jar包，按自己需求添加，如果父类已经包含了，可以不用写版本号

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```



二、<scope>import</scope>

只使用在<dependencyManagement>中，表示从其它的pom中导入dependency的配置

