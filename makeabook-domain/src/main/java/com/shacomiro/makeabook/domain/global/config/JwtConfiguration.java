package com.shacomiro.makeabook.domain.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.jwt.provider.JwtProvider;

@Configuration
public class JwtConfiguration {
	@Value("${jwt.secret.key}")
	private String secretKey;
	@Value("${jwt.secret.valid-milliseconds.access}")
	private long accessTokenValidMilliseconds;
	@Value("${jwt.secret.valid-milliseconds.refresh}")
	private long refreshTokenValidMilliseconds;

	@Bean
	public JwtProvider jwtProvider() {
		return new JwtProvider(secretKey);
	}

	public long getAccessTokenValidMilliseconds() {
		return accessTokenValidMilliseconds;
	}

	public long getRefreshTokenValidMilliseconds() {
		return refreshTokenValidMilliseconds;
	}
}
