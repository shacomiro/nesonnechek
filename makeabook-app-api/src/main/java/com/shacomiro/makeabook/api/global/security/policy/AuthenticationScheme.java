package com.shacomiro.makeabook.api.global.security.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationScheme {
	BEARER("Bearer");

	private final String type;
}
