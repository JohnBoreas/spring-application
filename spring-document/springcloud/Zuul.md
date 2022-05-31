使用：

1、配置maven

```xml
<dependency> 
    <groupId>org.springframework.cloud</groupId> 
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId> 
</dependency>
```

2、application.properties

```yml
#自定义路由转发
zuul:
  routes:
  ## http://127.0.0.1:6666 == http://service-order/
  ## http://127.0.0.1:8888/api-order/order --> http://127.0.0.1:6666/order --> http://127.0.0.1:6666/storage
    service-order: /api-order/** ## 写法1
    storage: ## 写法2
      path: /api-storage/**
      service-id: service-storage
  #zuul.sensitive-headers=Cookie,Set-Cookie
  sensitive-headers:
  ignored-headers: name
```

3、修改SpringBoot启动类

添加注解@EnableZuulProxy 

4、自定义Zuul过滤器

```java
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() { return "pre";}
    @Override
    public int filterOrder() {return 1;}
    @Override
    public boolean shouldFilter() {
        //true表示过滤器生效
        return true;
    }
    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest req = context.getRequest();
        String token = req.getParameter("access-token");
        if (token == null || "".equals(token.trim())) {
            //没有token，鉴权校验失败，拦截
            context.setSendZuulResponse(false);
            context.getResponse().setContentType("text/html;charset=UTF-8");
            context.setResponseBody("鉴权失败，access-token为空");
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
```



**Zuul 的常规配置**

1、设置访问路径，默认值为zuul 

　　zuul.servlet-path=/zuul

2、关闭通过微服务名称路访问（避免暴露服务名称）

　　全部设置：zuul.ignored-services=* 

　　分服务设置：zuul.ignored-services=ms-provider-order,ms-consumer-user

3、通过服务实例指定映射路径

　　zuul.routes.ms-provider-order=/order-service/**

　　访问：http://localhost:9004/zuul/order-service/userController/getUserInfo/{loginName}

4、通过服务的serviceId指定映射路径

　　zuul.routes.use-routing.serviceId=ms-provider-order

　　zuul.routes.use-routing.path=/order-service/**

5、通过url指定映射路径(路由不会作为HystrixCommand执行，同时也不能使用Ribbon来负载均衡多个URL)

　　zuul.routes.use-routing.url=http://localhost:8004/

　　zuul.routes.use-routing.path=/order-service/**

6、统一设置路由前缀

　　全局设置：zuul.prefix=/order-api

　　　　　　　zuul.strip-prefix=true（是否剥离前缀，默认是true）

　　　　　　　zuul.routes.use-routing.serviceId=ms-provider-order

　　　　　　　zuul.routes.use-routing.path=/order-service/**

　　　　　　　访问：http://localhost:9004/order-api/order-service/userController/getUserInfo/{loginName}

　　分服务设置：zuul.strip-prefix=true

　　　　　　　　zuul.routes.use-routing.serviceId=ms-provider-order

　　　　　　　　zuul.routes.use-routing.path=/order-service/**

　　　　　　　　zuul.routes.use-routing.stripPrefix=true

　　　　　　　　访问：http://localhost:9004/zuul/order-service/userController/getUserInfo/{loginName}

7、过滤敏感路径

　　zuul.ignored-patterns=/**/admin/**

8、过滤敏感头信息（通过zuul网关的时候，会过滤掉敏感的头信息，比如cookie等其他的）

　　全局设置：zuul.sensitive-headers=

　　分模块设置：zuul.routes.use-routing.serviceId=ms-provider-order

　　　　　　　　zuul.routes.use-routing.path=/order-service/**

　　　　　　　　zuul.routes.use-routing.sensitiveHeaders=