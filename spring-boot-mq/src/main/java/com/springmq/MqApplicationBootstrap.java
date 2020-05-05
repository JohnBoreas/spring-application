package com.springmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author boreas
 * @create 2020-02-01 下午 5:59
 */
// same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)去除数据库配置
public class MqApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(MqApplicationBootstrap.class, args);
    }
}
