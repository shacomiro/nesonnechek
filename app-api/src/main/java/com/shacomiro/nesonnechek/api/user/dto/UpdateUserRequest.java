package com.shacomiro.nesonnechek.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
	private String password;
	private String username;
}
