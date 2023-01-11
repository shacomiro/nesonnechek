package com.shacomiro.jwt.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	private final SecretKey secretKey;
	private final SignatureAlgorithm signatureAlgorithm;
	private final Set<Deserializer<Map<String, ?>>> deserializers;

	public JwtProvider(String secretString) {
		this(secretString, SignatureAlgorithm.HS256, null);
	}

	public JwtProvider(String secretString, SignatureAlgorithm signatureAlgorithm) {
		this(secretString, signatureAlgorithm, null);
	}

	public JwtProvider(String secretString, SignatureAlgorithm signatureAlgorithm,
			Set<Deserializer<Map<String, ?>>> deserializers) {
		if (signatureAlgorithm.isHmac()) {
			this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
		} else {
			this.secretKey = null;
			throw new RuntimeException("ECDSA or RSA signing algorithm not supported.");
		}
		this.signatureAlgorithm = signatureAlgorithm;
		this.deserializers = deserializers;
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
		JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder()
				.setSigningKey(secretKey);
		if (deserializers != null && !deserializers.isEmpty()) {
			for (Deserializer<Map<String, ?>> deserializer : deserializers) {
				jwtParserBuilder.deserializeJsonWith(deserializer);
			}
		}

		return jwtParserBuilder
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public Claims parseClaims(String token, Map<String, Object> requires) throws JwtException, IllegalArgumentException {
		JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder()
				.setSigningKey(secretKey);
		if (deserializers != null && !deserializers.isEmpty()) {
			for (Deserializer<Map<String, ?>> deserializer : deserializers) {
				jwtParserBuilder.deserializeJsonWith(deserializer);
			}
		}
		for (Map.Entry<String, Object> require : requires.entrySet()) {
			jwtParserBuilder.require(require.getKey(), require.getValue());
		}

		return jwtParserBuilder
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
