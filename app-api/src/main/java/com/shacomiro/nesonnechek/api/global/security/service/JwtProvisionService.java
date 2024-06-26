package com.shacomiro.nesonnechek.api.global.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shacomiro.jwt.policy.ClaimName;
import com.shacomiro.jwt.policy.SecurityScheme;
import com.shacomiro.jwt.policy.TokenPurpose;
import com.shacomiro.jwt.provider.JwtProvider;
import com.shacomiro.nesonnechek.api.global.config.token.JwtConfiguration;
import com.shacomiro.nesonnechek.api.global.security.dto.JwtDto;
import com.shacomiro.nesonnechek.cache.token.dto.JwtCacheDto;
import com.shacomiro.nesonnechek.cache.token.exception.JwtCacheExpiredException;
import com.shacomiro.nesonnechek.cache.token.service.JwtCacheService;
import com.shacomiro.nesonnechek.domain.redis.token.entity.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtProvisionService {
	private final JwtConfiguration jwtConfiguration;
	private final JwtCacheService jwtCacheService;
	private final JwtProvider jwtProvider;

	public Claims parseClaims(String jwt) throws JwtException, IllegalArgumentException {
		Map<String, Object> requires = new HashMap<>();
		requires.put(ClaimName.ISSUER.getName(), jwtConfiguration.getJwtIssuer());

		return jwtProvider.parseClaims(jwt, requires);
	}

	public JwtDto issueJwt(String key) throws JwtException {
		jwtCacheService.deleteRefreshJwtIfKeyExist(key, TokenPurpose.REFRESH.getValue());
		Date now = new Date();
		Claims accessClaims = createClaims(key, TokenPurpose.ACCESS, now, jwtConfiguration.getAccessTokenValidMilliseconds());
		Claims refreshClaims = createClaims(key, TokenPurpose.REFRESH, now, jwtConfiguration.getRefreshTokenValidMilliseconds());
		String accessToken = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(accessClaims));
		String refreshToken = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(refreshClaims));

		JwtCacheDto jwtCacheDto = new JwtCacheDto(refreshClaims.getId(), key,
				refreshClaims.get(ClaimName.PURPOSE.getName(), String.class), refreshToken,
				jwtConfiguration.getRefreshTokenValidMilliseconds());
		Jwt savedRefreshJwt = jwtCacheService.saveJwt(jwtCacheDto);

		return new JwtDto(HttpHeaders.AUTHORIZATION, SecurityScheme.BEARER_AUTH.getScheme(),
				accessToken, savedRefreshJwt.getToken());
	}

	public JwtDto reissueJwt(String key, String jwt) throws JwtException, JwtCacheExpiredException {
		jwtCacheService.verifyJwt(key, jwt, TokenPurpose.REFRESH.getValue());
		return issueJwt(key);
	}

	private Claims createClaims(String subject, TokenPurpose purpose, Date now, long validTime) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		map.put(ClaimName.ISSUER.getName(), jwtConfiguration.getJwtIssuer());
		map.put(ClaimName.SUBJECT.getName(), subject);
		map.put(ClaimName.PURPOSE.getName(), purpose.getValue());
		map.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(now));
		map.put(ClaimName.EXPIRATION.getName(), jwtProvider.getSecondsFromDate(new Date(now.getTime() + validTime)));
		if (purpose.equals(TokenPurpose.REFRESH)) {
			map.put(ClaimName.NOT_BEFORE.getName(),
					jwtProvider.getSecondsFromDate(
							new Date(now.getTime() + jwtConfiguration.getAccessTokenValidMilliseconds())));
		}

		return jwtProvider.createClaims(map);
	}
}
