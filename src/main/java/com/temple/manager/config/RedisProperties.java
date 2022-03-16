package com.temple.manager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RedisProperties {
    private final int port;
    private final String host;

    public RedisProperties(@Value("${spring.redis.port}") int port, @Value("${spring.redis.host}") String host) {
        this.port = port;
        this.host = host;
    }
}
