**json接口开发**

```shell
1.类添加 @RestController 即可，默认类中的方法都会以 json 的格式返回

## 对比之前的spring：
添加 jackjson 等相关 jar 包
配置 Spring Controller 扫描
对接的方法添加 @ResponseBody

2.页面开发只要使用@Controller注解即可
```

**Filter**

```shell
Filter包括：
1）、在servlet被调用之前截获;
2）、在servlet被调用之前检查servlet request;
3）、根据需要修改request头和request数据;
4）、根据需要修改response头和response数据;
5）、在servlet被调用之后截获.

1.Spring Boot 自动添加了 OrderedCharacterEncodingFilter 和 HiddenHttpMethodFilter

2.自定义filter
    两个步骤：
    ①实现 Filter 接口，实现 Filter 方法
    ②加载Filter 配置，添加@Configuration 注解，将自定义Filter加入过滤链
    或者在Application 启动类 添加 @ServletComponentScan 注解
```

**延迟初始化**

```
1.可以使用lazyInitializationon方法SpringApplicationBuilder或setLazyInitializationon 方法以编程方式启用延迟初始化SpringApplication。

2.配置属性来启用它：
spring.main.lazy-initialization=true

3.禁用某些bean的延迟初始化，则可以使用@Lazy(false)批注将它们的延迟属性显式设置为false 。
```

**使用redis**

```shell
	Spring Boot 提供了对 Redis 集成的组件包：`spring-boot-starter-data-redis`，`spring-boot-starter-data-redis`依赖于`spring-data-redis` 和 `lettuce` 。Spring Boot 1.0 默认使用的是 Jedis 客户端，2.0 替换成 Lettuce


```

