package com.shacomiro.jwt.policy;

public enum TokenPurpose {
	ACCESS("access"),
	REFRESH("refresh");

	private final String value;

	TokenPurpose(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
