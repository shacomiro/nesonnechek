package com.shacomiro.makeabook.api.global.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.jwt.provider.JwtProvider;

import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtConfiguration {
	private final String jwtIssuer;
	private final long accessTokenValidMilliseconds;
	private final long refreshTokenValidMilliseconds;

	public JwtConfiguration(@Value("${jwt.issuer}") String jwtIssuer,
			@Value("${jwt.valid-milliseconds.access}") long accessTokenValidMilliseconds,
			@Value("${jwt.valid-milliseconds.refresh}") long refreshTokenValidMilliseconds) {
		this.jwtIssuer = jwtIssuer;
		this.accessTokenValidMilliseconds = accessTokenValidMilliseconds;
		this.refreshTokenValidMilliseconds = refreshTokenValidMilliseconds;
	}

	@Bean
	public JwtProvider jwtProvider(@Value("${jwt.secret.key}") String secretKey) {
		return new JwtProvider(secretKey, SignatureAlgorithm.HS256);
	}

	public String getJwtIssuer() {
		return jwtIssuer;
	}

	public long getAccessTokenValidMilliseconds() {
		return accessTokenValidMilliseconds;
	}

	public long getRefreshTokenValidMilliseconds() {
		return refreshTokenValidMilliseconds;
	}
}
