package com.shacomiro.makeabook.api.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
	private String reqHeaders;
	private String authScheme;
	private String accessToken;
	private String refreshToken;
}
