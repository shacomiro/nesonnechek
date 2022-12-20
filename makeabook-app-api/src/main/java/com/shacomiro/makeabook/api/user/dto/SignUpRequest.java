package com.shacomiro.makeabook.api.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	@NotBlank(message = "Email value must be provided")
	@Size(min = 4, max = 50, message = "Email value length must be between 4 and 50 characters")
	@Email(message = "Email value format must be like [user@example.com]")
	private String email;
	@NotBlank(message = "Password must be provided")
	private String password;
	@NotBlank(message = "Username must be provided")
	private String username;
}
