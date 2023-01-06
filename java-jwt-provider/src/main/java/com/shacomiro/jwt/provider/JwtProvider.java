package com.shacomiro.jwt.provider;

import java.util.Date;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtProvider {
	private final String secretKey;
	private final long accessTokenValidMilleSeconds;
	private final long refreshTokenValidMilleSeconds;

	public JwtProvider(String secretKey, long accessTokenValidMilleSeconds, long refreshTokenValidMilleSeconds) {
		this.secretKey = secretKey;
		this.accessTokenValidMilleSeconds = accessTokenValidMilleSeconds;
		this.refreshTokenValidMilleSeconds = refreshTokenValidMilleSeconds;
	}

	public long getAccessTokenValidMilleSeconds() {
		return accessTokenValidMilleSeconds;
	}

	public long getRefreshTokenValidMilleSeconds() {
		return refreshTokenValidMilleSeconds;
	}

	//JWT 액세스 토큰 생성
	public String createAccessToken(String uniqueKey) {
		return createToken(uniqueKey, "access", accessTokenValidMilleSeconds);
	}

	//JWT 리프래시 토큰 생성
	public String createRefreshToken(String uniqueKey) {
		return createToken(uniqueKey, "refresh", refreshTokenValidMilleSeconds);
	}

	//JWT 토큰 생성
	public String createToken(String uniqueKey, String tokenType, long validTime) {
		Claims claims = Jwts.claims()
				.setId(UUID.randomUUID().toString())
				.setIssuer("makeabook")
				.setSubject(uniqueKey);

		Date now = new Date();

		return Jwts.builder()
				.setClaims(claims)
				.claim("typ", tokenType)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + validTime))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	//JWT 토큰 복호화
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}

	public String removeAuthenticationScheme(String token, String scheme) {
		try {
			return token.replaceFirst(scheme, "").trim();
		} catch (PatternSyntaxException e) {
			throw new JwtException("Authentication scheme '" + scheme + "' not found from current request");
		}
	}

	//JWT 토큰의 유효성 및 만료일자 검증
	public void verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch (SecurityException | SignatureException e) {
			throw new JwtException("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			throw new JwtException("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new JwtException("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new JwtException("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new JwtException("JWT token compact of handler are invalid");
		}
	}
}
