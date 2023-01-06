package com.shacomiro.makeabook.api.global.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.shacomiro.jwt.provider.JwtProvider;
import com.shacomiro.makeabook.api.global.security.token.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final JwtProvider jwtProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String jwt = ((JwtAuthenticationToken)authentication).getJwt();
		jwtProvider.verifyToken(jwt);
		Claims claims = jwtProvider.parseClaims(jwt);
		String type = claims.get("typ", String.class);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

		return JwtAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities(), jwt, type);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
