package com.shacomiro.jwt.provider;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.JwtMap;

public class JwtProvider {
	private final String secretKey;

	public JwtProvider(String secretKey) {
		this.secretKey = secretKey;
	}

	public String createToken(Claims claims, long validTime) {
		Date now = new Date();

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + validTime))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public Claims createClaims(Map<String, Object> map) {
		return Jwts.claims(new JwtMap(map));
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}

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
