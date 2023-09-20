package com.shacomiro.nesonnechek.cache.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtCacheDto {
	String id;
	String key;
	String purpose;
	String token;
	long expiration;
}
