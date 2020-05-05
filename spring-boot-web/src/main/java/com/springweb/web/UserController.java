package com.springweb.web;

import com.springweb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author boreas
 * @create 2020-02-03 下午 2:57
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
        User user=new User(1111L, "asd", "123456", "123@163.com","18808081919");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
    @RequestMapping("/cacheUser")
    public void cacheUser() {
        User user = new User(1111L, "asd", "123456", "123@163.com","18808081919");
        // 操作字符串
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("cache.user", user);
        operations.set("cache.user.1111", user,1000001, TimeUnit.SECONDS);
        //redisTemplate.delete("com.neo.f");
        boolean exists = redisTemplate.hasKey("com.neo.1111");
        if(exists) {
            System.out.println("exists is true");
        } else {
            System.out.println("exists is false");
        }
    }

    /**
     * 存储键值出现乱码 \xac\xed\x00\x05t\x00
     * 该问题是由于
     * spring-data-redis的RedisTemplate<K, V>模板类在操作redis时默认使用JdkSerializationRedisSerializer来进行序列化
     * @param redisTemplate
     */
//    @Autowired(required = false)
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);
//        this.redisTemplate = redisTemplate;
//    }
}
