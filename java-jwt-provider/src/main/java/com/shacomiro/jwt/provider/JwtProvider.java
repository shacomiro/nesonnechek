package com.shacomiro.jwt.provider;

import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.JwtMap;

public class JwtProvider {
	private final String base64EncodedSecretKey;

	public JwtProvider(String secretKey) {
		this.base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(Claims claims) {
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
				.compact();
	}

	public long getSecondsFromDate(Date date) {
		return date.getTime() / 1000;
	}

	public Claims createClaims(Map<String, Object> map) {
		return Jwts.claims(new JwtMap(map));
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(base64EncodedSecretKey)
				.parseClaimsJws(token)
				.getBody();
	}

	public void verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token);
		} catch (SecurityException | SignatureException e) {
			throw new JwtException("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			throw new JwtException("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new JwtException("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new JwtException("Unsupported JWT token");
		} catch (PrematureJwtException e) {
			throw new JwtException("JWT not accepted before " + e.getClaims()
					.getNotBefore()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime() + ".");
		} catch (IllegalArgumentException e) {
			throw new JwtException("JWT token compact of handler are invalid");
		}
	}
}
