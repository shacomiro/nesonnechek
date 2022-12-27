package com.shacomiro.makeabook.domain.redis.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories({"com.shacomiro.makeabook.domain.redis"})
public class RedisConfiguration {
}
