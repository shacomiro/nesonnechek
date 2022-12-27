package com.shacomiro.makeabook.domain.redis.global.config.cache;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfiguration {

	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC))
				.computePrefixWith(CacheKeyPrefix.simple())
				.serializeKeysWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(cacheConfiguration)
				.build();
	}
}
