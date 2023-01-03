package com.shacomiro.makeabook.domain.token.service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;
import com.shacomiro.makeabook.domain.redis.token.service.JwtRedisService;
import com.shacomiro.makeabook.domain.token.dto.JwtDto;
import com.shacomiro.makeabook.domain.token.exception.JwtException;
import com.shacomiro.makeabook.domain.token.policy.AuthenticationScheme;
import com.shacomiro.makeabook.domain.token.provider.JwtProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
	private final JwtRedisService jwtRedisService;
	private final JwtProvider jwtProvider;

	public String resolveJwtFromRequest(HttpServletRequest request) {
		return jwtProvider.resolveToken(request);
	}

	public String getBearerToken(String token) {
		return jwtProvider.removeAuthenticationScheme(token, AuthenticationScheme.BEARER.getType());
	}

	public Claims getVerifiedJwtClaims(String token) {
		jwtProvider.verifyToken(token);

		return jwtProvider.parseClaims(token);
	}

	public Authentication getAuthenticationFromJwt(String token) {
		return jwtProvider.getAuthentication(token);
	}

	public JwtDto issueJwt(String key) {
		jwtRedisService.findByKeyAndType(key, "refresh").ifPresent(jwtRedisService::delete);

		String accessToken = jwtProvider.createAccessToken(key);
		String refreshToken = jwtProvider.createRefreshToken(key);
		saveRefreshJwt(key, refreshToken);

		return new JwtDto(HttpHeaders.AUTHORIZATION, AuthenticationScheme.BEARER.getType(), accessToken, refreshToken);
	}

	public JwtDto reissueJwt(String key) {
		jwtRedisService.findByKeyAndType(key, "refresh").ifPresent(jwtRedisService::delete);

		String accessToken = jwtProvider.createAccessToken(key);
		String refreshToken = jwtProvider.createRefreshToken(key);
		Jwt savedRefreshJwt = saveRefreshJwt(key, refreshToken);

		return new JwtDto(HttpHeaders.AUTHORIZATION, AuthenticationScheme.BEARER.getType(),
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
		return saveJwt(key, token, 1000L * 60 * 60 * 2);
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
}
