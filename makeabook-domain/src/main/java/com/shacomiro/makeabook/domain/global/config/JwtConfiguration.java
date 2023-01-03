package com.shacomiro.makeabook.domain.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.makeabook.domain.token.provider.JwtProvider;

@Configuration
public class JwtConfiguration {
	@Value("${jwt.secret.key}")
	private String secretKey;
	@Value("${jwt.secret.valid-milliseconds.access}")
	private long accessTokenValidMilleSeconds;
	@Value("${jwt.secret.valid-milliseconds.refresh}")
	private long refreshTokenValidMilleSeconds;

	@Bean
	public JwtProvider jwtProvider() {
		return new JwtProvider(secretKey, accessTokenValidMilleSeconds, refreshTokenValidMilleSeconds);
	}
}
