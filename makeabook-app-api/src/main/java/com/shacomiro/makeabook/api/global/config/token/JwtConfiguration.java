package com.shacomiro.makeabook.api.global.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.jwt.provider.JwtProvider;

@Configuration
public class JwtConfiguration {
	private final long accessTokenValidMilliseconds;
	private final long refreshTokenValidMilliseconds;

	public JwtConfiguration(@Value("${jwt.secret.valid-milliseconds.access}") long accessTokenValidMilliseconds,
			@Value("${jwt.secret.valid-milliseconds.refresh}") long refreshTokenValidMilliseconds) {
		this.accessTokenValidMilliseconds = accessTokenValidMilliseconds;
		this.refreshTokenValidMilliseconds = refreshTokenValidMilliseconds;
	}

	@Bean
	public JwtProvider jwtProvider(@Value("${jwt.secret.key}") String secretKey) {
		return new JwtProvider(secretKey);
	}

	public long getAccessTokenValidMilliseconds() {
		return accessTokenValidMilliseconds;
	}

	public long getRefreshTokenValidMilliseconds() {
		return refreshTokenValidMilliseconds;
	}
}
