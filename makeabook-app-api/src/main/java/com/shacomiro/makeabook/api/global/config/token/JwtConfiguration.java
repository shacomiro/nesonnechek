package com.shacomiro.makeabook.api.global.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.jwt.provider.JwtProvider;

@Configuration
public class JwtConfiguration {
	private final String jwtIssuer;
	private final long accessTokenValidMilliseconds;
	private final long refreshTokenValidMilliseconds;

	public JwtConfiguration(@Value("${ext-config.jwt.issuer}") String jwtIssuer,
			@Value("${ext-config.jwt.valid-milliseconds.access}") long accessTokenValidMilliseconds,
			@Value("${ext-config.jwt.valid-milliseconds.refresh}") long refreshTokenValidMilliseconds) {
		this.jwtIssuer = jwtIssuer;
		this.accessTokenValidMilliseconds = accessTokenValidMilliseconds;
		this.refreshTokenValidMilliseconds = refreshTokenValidMilliseconds;
	}

	@Bean
	public JwtProvider jwtProvider(@Value("${ext-config.jwt.secret.key}") String secretKey) {
		return new JwtProvider(secretKey);
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
