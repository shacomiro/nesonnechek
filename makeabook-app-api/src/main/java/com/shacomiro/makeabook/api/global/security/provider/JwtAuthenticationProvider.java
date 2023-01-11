package com.shacomiro.makeabook.api.global.security.provider;

import java.time.ZoneId;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.shacomiro.jwt.policy.ClaimName;
import com.shacomiro.makeabook.api.global.security.service.JwtProvisionService;
import com.shacomiro.makeabook.api.global.security.token.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final JwtProvisionService jwtProvisionService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException, JwtException {
		String jwt = ((JwtAuthenticationToken)authentication).getJwt();
		Claims claims = getVerifiedClaimsFromJwt(jwt);
		String purpose = claims.get(ClaimName.PURPOSE.getName(), String.class);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

		return JwtAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities(), jwt, purpose);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private Claims getVerifiedClaimsFromJwt(String jwt) {
		Claims claims;
		try {
			claims = jwtProvisionService.parseClaims(jwt);
		} catch (SignatureException e) {
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

		return claims;
	}
}
