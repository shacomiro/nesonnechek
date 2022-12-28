package com.shacomiro.makeabook.domain.redis.global.config.cache;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CacheKey {
	public static final long DEFAULT_EXPIRE_MILLIS_SEC = 1000L * 60 * 3;
}
