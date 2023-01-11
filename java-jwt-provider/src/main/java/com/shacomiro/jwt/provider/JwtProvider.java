package com.shacomiro.jwt.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	private final SecretKey secretKey;

	public JwtProvider(String secretString) {
		this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Claims claims) {
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}

	public long getSecondsFromDate(Date date) {
		return date.getTime() / 1000;
	}

	public Claims createClaims(Map<String, Object> map) {
		return Jwts.claims(map);
	}

	public Claims parseClaims(String token) throws JwtException, IllegalArgumentException {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
