**redisTemplate和stringRedisTemplate对比**

```
1、两者的关系是StringRedisTemplate继承RedisTemplate。

2、可读性
redisTemplate存储对象，但是要实现Serializable接口。内容不可读
StringRedisTemplate当存入对象时，会报错 ：can not cast into String。可见性强，更易维护

3、序列化方式不一样
StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。

4、序列化方式：
​	redisTemplate使用的是JdkSerializationRedisSerializer
​	stringRedisTemplate使用的是StringRedisSerializer
```

注意：

​		存储键值出现乱码 \xac\xed\x00\x05t\x00，该问题是由于spring-data-redis的RedisTemplate<K, V>模板类在操作redis时默认使用JdkSerializationRedisSerializer来进行序列化

```java
// key，value的序列化方式需要不一样
1.在controller
@Autowired(required = false)
public void setRedisTemplate(RedisTemplate redisTemplate) {
    RedisSerializer stringSerializer = new StringRedisSerializer();
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    this.redisTemplate = redisTemplate;
}
2.配置RedisConfig
@Bean(name = "redisTemplate")
public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(new StringRedisSerializer()); // key的序列化类型
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer()); // value的序列化类型
    return redisTemplate;
}
```
spring-data-redis提供如下几种选择：

```shell
- GenericToStringSerializer: 可以将任何对象泛化为字符串并序列化
- Jackson2JsonRedisSerializer: 跟JacksonJsonRedisSerializer实际上是一样的
- JacksonJsonRedisSerializer: 序列化object对象为json字符串
- JdkSerializationRedisSerializer: 序列化java对象
- StringRedisSerializer: 简单的字符串序列化

## 性能对比：
JdkSerializationRedisSerializer, 	序列化时间：10ms, 序列化后的长度：1328
JdkSerializationRedisSerializer, 	反序列化时间：5
GenericJackson2JsonRedisSerializer, 序列化时间：135ms, 序列化后的长度：28625
GenericJackson2JsonRedisSerializer, 反序列化时间：140
Jackson2JsonRedisSerializer, 		序列化时间：6ms, 序列化后的长度：21401
Jackson2JsonRedisSerializer, 		反序列化时间：6
StringRedisSerializer, 				序列化时间：2ms, 序列化后的长度：21000
StringRedisSerializer, 				反序列化时间：1

JdkSerializationRedisSerializer序列化后长度最小，Jackson2JsonRedisSerializer效率最高。
推荐key使用StringRedisSerializer，保持的key简明易读；value可以使用Jackson2JsonRedisSerializer
```



```


```