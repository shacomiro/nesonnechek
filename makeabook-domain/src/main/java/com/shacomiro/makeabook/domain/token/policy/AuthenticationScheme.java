package com.shacomiro.makeabook.domain.token.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationScheme {
	BEARER("Bearer");

	private final String type;
}
