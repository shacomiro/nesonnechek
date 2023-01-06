package com.shacomiro.makeabook.domain.token.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.shacomiro.jwt.policy.ClaimName;
import com.shacomiro.jwt.policy.SecurityScheme;
import com.shacomiro.jwt.provider.JwtProvider;
import com.shacomiro.makeabook.domain.global.config.JwtConfiguration;
import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;
import com.shacomiro.makeabook.domain.redis.token.service.JwtRedisService;
import com.shacomiro.makeabook.domain.token.dto.JwtDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
	private final JwtRedisService jwtRedisService;
	private final JwtConfiguration jwtConfiguration;
	private final JwtProvider jwtProvider;

	public JwtDto issueJwt(String key) {
		jwtRedisService.findByKeyAndType(key, "refresh").ifPresent(jwtRedisService::delete);

		String accessToken = jwtProvider.createToken(createClaims(key, "access"),
				jwtConfiguration.getAccessTokenValidMilleSeconds());
		String refreshToken = jwtProvider.createToken(createClaims(key, "refresh"),
				jwtConfiguration.getRefreshTokenValidMilleSeconds());
		saveRefreshJwt(key, refreshToken);

		return new JwtDto(HttpHeaders.AUTHORIZATION, SecurityScheme.BEARER_AUTH.getScheme(), accessToken, refreshToken);
	}

	public JwtDto reissueJwt(String key) {
		jwtRedisService.findByKeyAndType(key, "refresh").ifPresent(jwtRedisService::delete);

		String accessToken = jwtProvider.createToken(createClaims(key, "access"),
				jwtConfiguration.getAccessTokenValidMilleSeconds());
		String refreshToken = jwtProvider.createToken(createClaims(key, "refresh"),
				jwtConfiguration.getRefreshTokenValidMilleSeconds());
		Jwt savedRefreshJwt = saveRefreshJwt(key, refreshToken);

		return new JwtDto(HttpHeaders.AUTHORIZATION, SecurityScheme.BEARER_AUTH.getScheme(),
				accessToken, savedRefreshJwt.getToken());
	}

	public void verifyRefreshJwt(String key, String reqToken) {
		jwtRedisService.findByKeyAndType(key, "refresh")
				.ifPresent(refreshJwt -> {
					if (!refreshJwt.getToken().equals(reqToken)) {
						throw new JwtException("Expired refresh token.");
					}
				});
	}

	private Jwt saveRefreshJwt(String key, String token) {
		return saveJwt(key, token, jwtConfiguration.getRefreshTokenValidMilleSeconds());
	}

	private Jwt saveJwt(String key, String token, long expiration) {
		Claims claims = jwtProvider.parseClaims(token);

		return jwtRedisService.save(
				Jwt.byAllParameter()
						.id(claims.getId())
						.key(key)
						.type(claims.get("typ", String.class))
						.token(token)
						.expiration(expiration)
						.build()
		);
	}

	private Claims createClaims(String subject, String type) {
		Map<String, Object> jwtMap = new LinkedHashMap<>();
		jwtMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		jwtMap.put(ClaimName.ISSUER.getName(), "makeabook");
		jwtMap.put(ClaimName.SUBJECT.getName(), subject);
		jwtMap.put(ClaimName.TYPE.getName(), type);

		return jwtProvider.createClaims(jwtMap);
	}
}
