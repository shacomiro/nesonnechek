package com.shacomiro.makeabook.domain.user.exception;

import com.shacomiro.makeabook.domain.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
