package com.shacomiro.makeabook.cache.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
	private String reqHeaders;
	private String authScheme;
	private String accessToken;
	private String refreshToken;
}
