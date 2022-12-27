package com.shacomiro.makeabook.domain.redis.token.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@RedisHash("refreshToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
	@Id
	private String id;
	private String token;
	@TimeToLive
	private Long expiration;

	@Builder(builderClassName = "ByAllParameter", builderMethodName = "byAllParameter")
	public RefreshToken(String id, String token, Long expiration) {
		this.id = id;
		this.token = token;
		this.expiration = expiration;
	}
}
