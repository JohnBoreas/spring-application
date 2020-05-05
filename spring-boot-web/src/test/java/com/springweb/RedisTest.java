package com.springweb;

import com.springweb.model.User;
import org.junit.Test;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boreas
 * @create 2020-02-03 下午 7:11
 */
public class RedisTest {
    /**
     * redis序列化性能测试
     */
    @Test
    public void testSerial(){
        User user = new User(1111L, "asd", "123456", "123@163.com","18808081919");
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(user);
        }
        JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();
        GenericJackson2JsonRedisSerializer gen = new GenericJackson2JsonRedisSerializer();
        Jackson2JsonRedisSerializer jack2 = new Jackson2JsonRedisSerializer(List.class);
        StringRedisSerializer str = new StringRedisSerializer();
        // JacksonJsonRedisSerializer jack = new JacksonJsonRedisSerializer();

        Long jdkStart = System.currentTimeMillis();
        byte[] bytesJdk = jdk.serialize(list);
        System.out.println("JdkSerializationRedisSerializer, 序列化时间：" + (System.currentTimeMillis() - jdkStart) + "ms, 序列化后的长度：" + bytesJdk.length);
        jdkStart = System.currentTimeMillis();
        jdk.deserialize(bytesJdk);
        System.out.println("JdkSerializationRedisSerializer, 反序列化时间：" + (System.currentTimeMillis() - jdkStart));

        Long genStart = System.currentTimeMillis();
        byte[] bytesGen = gen.serialize(list);
        System.out.println("GenericJackson2JsonRedisSerializer, 序列化时间：" + (System.currentTimeMillis() - genStart) + "ms, 序列化后的长度：" + bytesGen.length);
        genStart = System.currentTimeMillis();
        gen.deserialize(bytesGen);
        System.out.println("GenericJackson2JsonRedisSerializer, 反序列化时间：" + (System.currentTimeMillis() - genStart));

        Long jack2Start = System.currentTimeMillis();
        byte[] bytesJ2 = jack2.serialize(list);
        System.out.println("Jackson2JsonRedisSerializer, 序列化时间：" + (System.currentTimeMillis() - jack2Start) + "ms, 序列化后的长度：" + bytesJ2.length);
        jack2Start = System.currentTimeMillis();
        jack2.deserialize(bytesJ2);
        System.out.println("Jackson2JsonRedisSerializer, 反序列化时间：" + (System.currentTimeMillis() - jack2Start));

        Long strStart = System.currentTimeMillis();
        byte[] bytesStr = str.serialize(list.toString());
        System.out.println("StringRedisSerializer, 序列化时间：" + (System.currentTimeMillis() - strStart) + "ms, 序列化后的长度：" + bytesStr.length);
        strStart = System.currentTimeMillis();
        str.deserialize(bytesStr);
        System.out.println("StringRedisSerializer, 反序列化时间：" + (System.currentTimeMillis() - strStart));
    }
}
