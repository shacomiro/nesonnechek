package com.shacomiro.nesonnechek.cache.global.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CacheKey {
	public static final long DEFAULT_EXPIRE_MILLIS_SEC = 1000L * 60 * 3;
	public static final String SIGN_IN_USER = "signInUser";
}
