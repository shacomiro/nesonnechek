package com.shacomiro.makeabook.domain.token.provider;

import java.util.Date;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.shacomiro.makeabook.domain.token.exception.JwtException;

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
	//TODO 임시 유효시간을 YAML 구성 파일에서 불러오도록 수정하기
	private long tempAccessTokenValidTime = 1000L * 60 * 30; //임시 액세스 토큰 유효시간(30분)
	private long tempRefreshTokenValidTime = 1000L * 60 * 60 * 2; //임시 리프레시 토큰 유효시간(2시간)

	//JWT 액세스 토큰 생성
	public String createAccessToken(String uniqueKey) {
		return createToken(uniqueKey, "access", tempAccessTokenValidTime);
	}

	//JWT 리프래시 토큰 생성
	public String createRefreshToken(String uniqueKey) {
		return createToken(uniqueKey, "refresh", tempRefreshTokenValidTime);
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

	//JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(parseClaims(token).getSubject());

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	//HTTP 요청의 헤더에서 토큰 파싱 -> "X-AUTH-TOKEN: jwt" -> Authorization 속성으로 변경
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(HttpHeaders.AUTHORIZATION);
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

			Claims claims = parseClaims(token);
			//TODO 뭐 바꾸려고 했는데 뭐였더라

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
