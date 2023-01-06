package com.shacomiro.jwt.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationScheme {
	BEARER("Bearer");

	private final String type;
}

