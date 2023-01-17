package com.shacomiro.makeabook.api.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRequest {
	@NotBlank(message = "Password must be provided")
	private String password;
}
