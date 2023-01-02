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

	public Claims getVerifiedJwtClaims(String token) {
		jwtProvider.verifyToken(token);

		return jwtProvider.parseClaims(token);
	}

	public Authentication getAuthenticationFromJwt(String token) {
		return jwtProvider.getAuthentication(token);
	}

	public JwtDto issueJwt(String emailValue, String authScheme) {
		jwtRedisService.findByKeyAndType(emailValue, "refresh").ifPresent(jwtRedisService::delete);

		String accessToken = jwtProvider.createAccessToken(emailValue);
		String refreshToken = jwtProvider.createRefreshToken(emailValue);
		saveJwt(emailValue, refreshToken, 1000L * 60 * 60 * 2);

		return new JwtDto(HttpHeaders.AUTHORIZATION, authScheme, accessToken, refreshToken);
	}

	public JwtDto reissueJwt(String emailValue, String authScheme) {
		deleteExistRefreshJwt(emailValue);
		String accessToken = jwtProvider.createAccessToken(emailValue);
		String refreshToken = jwtProvider.createRefreshToken(emailValue);
		Jwt savedRefreshJwt = saveJwt(emailValue, refreshToken, 1000L * 60 * 60 * 2);

		return new JwtDto(HttpHeaders.AUTHORIZATION, authScheme, accessToken, savedRefreshJwt.getToken());
	}

	private void deleteExistRefreshJwt(String emailValue) {
		Jwt existRefreshToken = jwtRedisService.findByKeyAndType(emailValue, "refresh")
				.orElseThrow(() -> new JwtException("JWT refresh token already expired."));

		jwtRedisService.delete(existRefreshToken);
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
