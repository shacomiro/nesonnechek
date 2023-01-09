package com.shacomiro.makeabook.api.global.security.provider;

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
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final JwtProvisionService jwtProvisionService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException, JwtException {
		String jwt = ((JwtAuthenticationToken)authentication).getJwt();
		Claims claims = jwtProvisionService.parseClaims(jwt);
		String purpose = claims.get(ClaimName.PURPOSE.getName(), String.class);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

		return JwtAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities(), jwt, purpose);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
