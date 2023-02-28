package com.shacomiro.makeabook.domain.redis.token.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.shacomiro.makeabook.domain.redis.global.config.EmbeddedRedisConfiguration;
import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;

@DataRedisTest
@Import(value = {EmbeddedRedisConfiguration.class})
@TestPropertySource(properties = {"spring.config.location = classpath:application-domain-redis-test.yaml"})
@ActiveProfiles(value = {"domain-redis-test"})
class JwtRedisRepositoryTest {
	private static final String EXAMPLE_EMAIL = "userB@email.com";
	private static final String EXAMPLE_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzIzZDIwNC1hZDFjLTRjODctYTI5OC1kNzI4NmQ2ZDc3Y2QiLCJpc3MiOiJtYWtlYWJvb2siLCJzdWIiOiJ1c2VyMUBlbWFpbC5jb20iLCJwdXIiOiJyZWZyZXNoIiwiaWF0IjoxNjc2OTA2OTI0LCJleHAiOjE2NzY5MDY5NTQsIm5iZiI6MTY3NjkwNjkzOX0.rnHj7OTG1QQOS2qXUAhPxrW_N-5Z0MUzzdljhz6WzLU";
	private static final String REFRESH_PURPOSE = "refresh";
	private final JwtRedisRepository jwtRedisRepository;

	@Autowired
	public JwtRedisRepositoryTest(JwtRedisRepository jwtRedisRepository) {
		this.jwtRedisRepository = jwtRedisRepository;
	}

	@Test
	@Order(1)
	@DisplayName("JWT 등록 후 조회")
	void insertAndFindJwt() {
		//given
		String tokenUuid = UUID.randomUUID().toString();
		Jwt jwt = Jwt.byAllParameter()
				.id(tokenUuid)
				.key(EXAMPLE_EMAIL)
				.purpose(REFRESH_PURPOSE)
				.token(EXAMPLE_TOKEN)
				.expiration(1000L)
				.build();
		jwtRedisRepository.save(jwt);

		//when
		Optional<Jwt> result = jwtRedisRepository.findById(tokenUuid);

		//then
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(jwt.getId(), result.get().getId());
	}

	@Test
	@Order(2)
	@DisplayName("JWT 등록 및 조회 후 삭제")
	void findAndDeleteJwt() {
		//given
		String tokenUuid = UUID.randomUUID().toString();
		Jwt jwt = Jwt.byAllParameter()
				.id(tokenUuid)
				.key(EXAMPLE_EMAIL)
				.purpose(REFRESH_PURPOSE)
				.token(EXAMPLE_TOKEN)
				.expiration(1000L)
				.build();
		jwtRedisRepository.save(jwt);

		//when
		Jwt savedJwt = jwtRedisRepository.findById(tokenUuid).orElseThrow();
		jwtRedisRepository.delete(savedJwt);
		boolean result = jwtRedisRepository.existsById(tokenUuid);

		//then
		Assertions.assertFalse(result);
	}
}
