package com.shacomiro.makeabook.cache.token.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shacomiro.makeabook.cache.token.dto.JwtCacheDto;
import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtRedisRepository;

@ExtendWith(MockitoExtension.class)
class JwtCacheServiceTest {
	private static final String EXAMPLE_EMAIL = "userB@email.com";
	private static final String EXAMPLE_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzIzZDIwNC1hZDFjLTRjODctYTI5OC1kNzI4NmQ2ZDc3Y2QiLCJpc3MiOiJtYWtlYWJvb2siLCJzdWIiOiJ1c2VyMUBlbWFpbC5jb20iLCJwdXIiOiJyZWZyZXNoIiwiaWF0IjoxNjc2OTA2OTI0LCJleHAiOjE2NzY5MDY5NTQsIm5iZiI6MTY3NjkwNjkzOX0.rnHj7OTG1QQOS2qXUAhPxrW_N-5Z0MUzzdljhz6WzLU";
	private static final String REFRESH_PURPOSE = "refresh";
	@InjectMocks
	private JwtCacheService jwtCacheService;
	@Mock
	private JwtRedisRepository jwtRedisRepository;

	@Test
	@Order(1)
	@DisplayName("캐시된 JWT 비교 검증 성공")
	void verifyJwtCache() {
		//given
		String uuid = UUID.randomUUID().toString();
		String key = EXAMPLE_EMAIL;
		String purpose = REFRESH_PURPOSE;
		String token = EXAMPLE_TOKEN;
		long expiration = 1000L;
		Jwt jwt = new Jwt(uuid, key, purpose, token, expiration);

		given(jwtRedisRepository.findByKeyAndPurpose(key, purpose)).willReturn(Optional.of(jwt));

		//when
		jwtCacheService.verifyJwt(key, token, purpose);

		//then

		verify(jwtRedisRepository, Mockito.atLeastOnce()).findByKeyAndPurpose(key, purpose);
	}

	@Test
	@Order(2)
	@DisplayName("JWT 캐시 DTO 저장 성공")
	void saveJwtCache() {
		//given
		String uuid = UUID.randomUUID().toString();
		String key = EXAMPLE_EMAIL;
		String purpose = REFRESH_PURPOSE;
		String token = EXAMPLE_TOKEN;
		long expiration = 1000L;
		Jwt jwt = new Jwt(uuid, key, purpose, token, expiration);
		JwtCacheDto jwtCacheDto = new JwtCacheDto(uuid, key, purpose, token, expiration);

		given(jwtRedisRepository.save(any(Jwt.class))).willReturn(jwt);

		//when
		Jwt result = jwtCacheService.saveJwt(jwtCacheDto);

		//then
		assertEquals(jwt, result);
	}

	@Test
	@Order(3)
	@DisplayName("주어진 키가 존재하는 JWT 캐시 제거 성공")
	void deleteJwtCacheIfKeyExist() {
		//given
		String uuid = UUID.randomUUID().toString();
		String key = EXAMPLE_EMAIL;
		String purpose = REFRESH_PURPOSE;
		String token = EXAMPLE_TOKEN;
		long expiration = 1000L;
		Jwt jwt = new Jwt(uuid, key, purpose, token, expiration);

		given(jwtRedisRepository.findByKeyAndPurpose(key, purpose)).willReturn(Optional.of(jwt));

		//when
		jwtCacheService.deleteRefreshJwtIfKeyExist(key, purpose);

		//then
		verify(jwtRedisRepository, Mockito.atLeastOnce()).delete(jwt);
	}
}
