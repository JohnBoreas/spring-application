package com.springweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author boreas
 * @create 2020-02-03 下午 3:12
 */
@Configuration
// maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效。
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}