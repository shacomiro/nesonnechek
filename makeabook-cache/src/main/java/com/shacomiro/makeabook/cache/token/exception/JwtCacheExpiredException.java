package com.shacomiro.makeabook.cache.token.exception;

public class JwtCacheExpiredException extends RuntimeException {

	public JwtCacheExpiredException(String message) {
		super(message);
	}

	public JwtCacheExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
