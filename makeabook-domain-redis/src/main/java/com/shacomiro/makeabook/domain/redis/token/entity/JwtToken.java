package com.shacomiro.makeabook.domain.redis.token.entity;

import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("jwtToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtToken {
	@Id
	private String id;
	@Indexed
	private String key;
	@Indexed
	private String type;
	private String token;
	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;

	@Builder(builderClassName = "ByAllParameter", builderMethodName = "byAllParameter")
	public JwtToken(String id, String key, String type, String token, Long expiration) {
		this.id = id;
		this.key = key;
		this.type = type;
		this.token = token;
		this.expiration = expiration;
	}
}
