spring cloud提供的服务调用方式：

1、RestTemplate

2、Feign



#### RestTemplate

一、使用

```java
String result = restTemplate.getForEntity("http://service-storage/storage", 	String.class).getBody();

@Bean 
public IRule setRule(){ 
    //默认轮询：ZoneAvoidanceRule()，复合判断Server所在区域的性能和Server的可用性选 择服务器 //可以设置随机调用 RandomRule() 
    return new ZoneAvoidanceRule(); 
}
```

二、底层

使用了RibbonLoadBalancerClient

```java
ClientHttpRequest request = createRequest(url, method);

InterceptingClientHttpRequest.execute(url, method)
    
使用了LoadBalancerClient
balancerClient.execute(serviceName, this.requestFactory.createRequest(request, body, execution));

RibbonLoadBalancerClient	执行以下execute
public <T> T execute(String serviceId, LoadBalancerRequest<T> request, Object hint)
			throws IOException {
    // 获取负载均衡器ZoneAwareLoadBalancer  
    // DynamicServerListLoadBalancer.restOfInit 更新实例 NacosServerList
		ILoadBalancer loadBalancer = getLoadBalancer(serviceId);
    // 根据负载均衡器挑选一个server  IRule接口  默认ZoneAvoidanceRule  
		Server server = getServer(loadBalancer, hint);
		if (server == null) {
			throw new IllegalStateException("No instances available for " + serviceId);
		}
		RibbonServer ribbonServer = new RibbonServer(serviceId, server,
				isSecure(server, serviceId),
				serverIntrospector(serviceId).getMetadata(server));

		return execute(serviceId, ribbonServer, request);
	}
```



三、Ribbon

Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。



#### Feign

一、使用

```xml
// 引用
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-feign</artifactId>
</dependency>


@RequestMapping("/")
@FeignClient(value = "service-storage")
public interface FeignStorageService {

    @RequestMapping(value = "/storage")
    String storage();
}

配置注解
@EnableFeignClients
public class Application {
}
```



Feign是一种负载均衡的HTTP客户端，采用的是基于接口的注解，整合了ribbon，

使用Feign调用API就像调用本地方法一样，从避免了 调用目标微服务时，需要不断的解析/封装json 数据的繁琐