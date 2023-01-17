package com.shacomiro.makeabook.domain.redis.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
// @EntityScan({"com.shacomiro.makeabook.domain.redis"}) //필요 여부 확인 요망
@EnableRedisRepositories(value = {"com.shacomiro.makeabook.domain.redis"},
		enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RedisConfiguration {
}
