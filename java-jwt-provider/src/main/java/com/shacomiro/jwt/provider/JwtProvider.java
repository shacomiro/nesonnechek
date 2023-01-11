package com.shacomiro.jwt.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	private final SecretKey secretKey;
	private final SignatureAlgorithm signatureAlgorithm;

	public JwtProvider(String secretString) {
		this(secretString, SignatureAlgorithm.HS256);
	}

	public JwtProvider(String secretString, SignatureAlgorithm signatureAlgorithm) {
		this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public JwtBuilder initializeJwtBuilder(Claims claims) {
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.addClaims(claims);
	}

	public JwtBuilder initializeJwtBuilder(Claims claims, Map<String, Object> header) {
		return initializeJwtBuilder(claims)
				.setHeader(header);
	}

	public String buildToken(JwtBuilder jwtBuilder) {
		return jwtBuilder
				.signWith(secretKey, signatureAlgorithm)
				.compact();
	}

	public Claims createClaims(Map<String, Object> map) {
		return Jwts.claims(map);
	}

	public long getSecondsFromDate(Date date) {
		return date.getTime() / 1000;
	}

	public Claims parseClaims(String token) throws JwtException, IllegalArgumentException {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public Claims parseClaims(String token, Map<String, Object> requires) throws JwtException, IllegalArgumentException {
		JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder()
				.setSigningKey(secretKey);
		for (Map.Entry<String, Object> require : requires.entrySet()) {
			jwtParserBuilder.require(require.getKey(), require.getValue());
		}

		return jwtParserBuilder
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
