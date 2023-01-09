package com.shacomiro.makeabook.domain.token.service;

import java.util.Date;
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

		Date now = new Date();
		Claims accessClaims = createClaims(key, "access", now, jwtConfiguration.getAccessTokenValidMilliseconds());
		Claims refreshClaims = createClaims(key, "refresh", now, jwtConfiguration.getRefreshTokenValidMilliseconds());
		String accessToken = jwtProvider.createToken(accessClaims);
		String refreshToken = jwtProvider.createToken(refreshClaims);
		saveRefreshJwt(key, refreshToken, refreshClaims);

		return new JwtDto(HttpHeaders.AUTHORIZATION, SecurityScheme.BEARER_AUTH.getScheme(), accessToken, refreshToken);
	}

	public JwtDto reissueJwt(String key) {
		jwtRedisService.findByKeyAndType(key, "refresh").ifPresent(jwtRedisService::delete);

		Date now = new Date();
		Claims accessClaims = createClaims(key, "access", now, jwtConfiguration.getAccessTokenValidMilliseconds());
		Claims refreshClaims = createClaims(key, "refresh", now, jwtConfiguration.getRefreshTokenValidMilliseconds());
		String accessToken = jwtProvider.createToken(accessClaims);
		String refreshToken = jwtProvider.createToken(refreshClaims);
		Jwt savedRefreshJwt = saveRefreshJwt(key, refreshToken, refreshClaims);

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

	private Jwt saveRefreshJwt(String key, String token, Claims claims) {
		return saveJwt(key, token, claims);
	}

	private Jwt saveJwt(String key, String token, Claims claims) {
		return jwtRedisService.save(
				Jwt.byAllParameter()
						.id(claims.getId())
						.key(key)
						.type(claims.get("typ", String.class))
						.token(token)
						.expiration(claims.getExpiration().getTime())
						.build()
		);
	}

	private Claims createClaims(String subject, String type, Date now, long validTime) {
		Map<String, Object> jwtMap = new LinkedHashMap<>();
		jwtMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		jwtMap.put(ClaimName.ISSUER.getName(), "makeabook");
		jwtMap.put(ClaimName.SUBJECT.getName(), subject);
		jwtMap.put(ClaimName.TYPE.getName(), type);
		jwtMap.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(now));
		jwtMap.put(ClaimName.EXPIRATION.getName(), jwtProvider.getSecondsFromDate(new Date(now.getTime() + validTime)));
		if (type.equals("refresh")) {
			jwtMap.put(ClaimName.NOT_BEFORE.getName(),
					jwtProvider.getSecondsFromDate(
							new Date(now.getTime() + jwtConfiguration.getAccessTokenValidMilliseconds())));
		}

		return jwtProvider.createClaims(jwtMap);
	}
}
