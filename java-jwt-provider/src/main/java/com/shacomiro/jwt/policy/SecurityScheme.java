package com.shacomiro.jwt.policy;

public enum SecurityScheme {
	BEARER_AUTH("Bearer", "Bearer ");

	private final String scheme;
	private final String prefix;

	SecurityScheme(String scheme, String prefix) {
		this.scheme = scheme;
		this.prefix = prefix;
	}

	public String getScheme() {
		return scheme;
	}

	public String getPrefix() {
		return prefix;
	}
}
