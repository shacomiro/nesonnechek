package com.shacomiro.nesonnechek.domain.user.exception;

import com.shacomiro.nesonnechek.domain.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
