package com.shacomiro.nesonnechek.api.user.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
	@Size(min = 10, max = 60, message = "password must be between 10 and 60 characters")
	private String password;
	@Size(min = 1, max = 20, message = "Username length must be between 1 and 10 characters")
	private String username;
}
