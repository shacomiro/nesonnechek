package com.shacomiro.makeabook.api.global.security.service;

import java.util.Date;
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
import com.shacomiro.makeabook.api.global.config.token.JwtConfiguration;
import com.shacomiro.makeabook.api.global.security.dto.JwtDto;
import com.shacomiro.makeabook.cache.token.dto.JwtCacheDto;
import com.shacomiro.makeabook.cache.token.exception.JwtCacheExpiredException;
import com.shacomiro.makeabook.cache.token.service.JwtCacheService;
import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;

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
		return jwtProvider.parseClaims(jwt);
	}

	public JwtDto issueJwt(String key) throws JwtException {
		jwtCacheService.deleteRefreshJwtIfKeyExist(key, TokenPurpose.REFRESH.getValue());
		Date now = new Date();
		Claims accessClaims = createClaims(key, TokenPurpose.ACCESS.getValue(), now,
				jwtConfiguration.getAccessTokenValidMilliseconds());
		Claims refreshClaims = createClaims(key, TokenPurpose.REFRESH.getValue(), now,
				jwtConfiguration.getRefreshTokenValidMilliseconds());
		String accessToken = jwtProvider.createToken(accessClaims);
		String refreshToken = jwtProvider.createToken(refreshClaims);

		JwtCacheDto jwtCacheDto = new JwtCacheDto(refreshClaims.getId(), key,
				refreshClaims.get(ClaimName.PURPOSE.getName(), String.class), refreshToken,
				refreshClaims.getExpiration().getTime());
		Jwt savedRefreshJwt = jwtCacheService.saveJwt(jwtCacheDto);

		return new JwtDto(HttpHeaders.AUTHORIZATION, SecurityScheme.BEARER_AUTH.getScheme(),
				accessToken, savedRefreshJwt.getToken());
	}

	public JwtDto reissueJwt(String key, String jwt) throws JwtException, JwtCacheExpiredException {
		jwtCacheService.verifyJwt(key, jwt, TokenPurpose.REFRESH.getValue());
		return issueJwt(key);
	}

	private Claims createClaims(String subject, String type, Date now, long validTime) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		map.put(ClaimName.ISSUER.getName(), "makeabook");
		map.put(ClaimName.SUBJECT.getName(), subject);
		map.put(ClaimName.PURPOSE.getName(), type);
		map.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(now));
		map.put(ClaimName.EXPIRATION.getName(), jwtProvider.getSecondsFromDate(new Date(now.getTime() + validTime)));
		if (type.equals(TokenPurpose.REFRESH.getValue())) {
			map.put(ClaimName.NOT_BEFORE.getName(),
					jwtProvider.getSecondsFromDate(
							new Date(now.getTime() + jwtConfiguration.getAccessTokenValidMilliseconds())));
		}

		return jwtProvider.createClaims(map);
	}
}
