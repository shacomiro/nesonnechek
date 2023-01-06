package com.shacomiro.jwt.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityScheme {
	BEARER_AUTH("Bearer", "Bearer ");

	private final String scheme;
	private final String prefix;
}

