package com.shacomiro.makeabook.api.global.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final Object principal;
	private final Object credentials;
	private final String jwt;
	private final String jwtType;

	public JwtAuthenticationToken(String jwt) {
		super(null);
		this.principal = null;
		this.credentials = null;
		this.setAuthenticated(false);
		this.jwt = jwt;
		this.jwtType = null;
	}

	public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
			String jwt, String jwtType) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
		this.jwt = jwt;
		this.jwtType = jwtType;
	}

	public static JwtAuthenticationToken unauthenticated(String jwt) {
		return new JwtAuthenticationToken(jwt);
	}

	public static JwtAuthenticationToken authenticated(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String jwt, String jwtType) {
		return new JwtAuthenticationToken(principal, credentials, authorities, jwt, jwtType);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getJwt() {
		return jwt;
	}

	public String getType() {
		return jwtType;
	}
}
