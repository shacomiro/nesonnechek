package com.shacomiro.makeabook.domain.redis.global.config;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import redis.embedded.RedisServer;

@Profile("domain-redis-local")
@Configuration
public class EmbeddedRedisConfiguration {
	private final RedisServer redisServer;

	public EmbeddedRedisConfiguration(@Value("${spring.redis.port}") int redisPort) {
		redisServer = new RedisServer(redisPort);
	}

	@PostConstruct
	public void startRedisServer() {
		redisServer.start();
	}

	@PreDestroy
	public void stopRedisServer() {
		Optional.of(redisServer).ifPresent(RedisServer::stop);
	}
}
