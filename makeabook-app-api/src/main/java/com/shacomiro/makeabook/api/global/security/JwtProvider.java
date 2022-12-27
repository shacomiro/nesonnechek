package com.shacomiro.makeabook.api.global.security;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.shacomiro.makeabook.api.global.error.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {
	private final UserDetailsService userDetailsService;
	@Value("jwt.secret.key")
	private String secretKey;
	private long tempAccessTokenValidTime = 1000L * 60 * 30; //임시 액세스 토큰 유효시간(30분)
	private long tempRefreshTokenValidTime = 1000L * 60 * 60 * 2; //임시 리프레시 토큰 유효시간(2시간)

	public String createAccessToken(String uniqueKey) {
		return createToken(uniqueKey, "access", tempAccessTokenValidTime);
	}

	public String createRefreshToken(String uniqueKey) {
		return createToken(uniqueKey, "refresh", tempRefreshTokenValidTime);
	}

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

	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(parseClaims(token).getSubject());

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(HttpHeaders.AUTHORIZATION);
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
