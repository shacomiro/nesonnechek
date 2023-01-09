package com.shacomiro.jwt.policy;

import io.jsonwebtoken.Claims;

public enum ClaimName {
	ISSUER(Claims.ISSUER),
	SUBJECT(Claims.SUBJECT),
	AUDIENCE(Claims.AUDIENCE),
	EXPIRATION(Claims.EXPIRATION),
	NOT_BEFORE(Claims.NOT_BEFORE),
	ISSUED_AT(Claims.ISSUED_AT),
	ID(Claims.ID),
	PURPOSE("pur");

	private final String name;

	ClaimName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
