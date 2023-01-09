package com.shacomiro.makeabook.domain.redis.token.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtRedisService {
	private final JwtRedisRepository jwtRedisRepository;

	public Jwt save(Jwt jwt) {
		return jwtRedisRepository.save(jwt);
	}

	public Optional<Jwt> findByKeyAndPurpose(String key, String purpose) {
		return jwtRedisRepository.findByKeyAndPurpose(key, purpose);
	}

	public void delete(Jwt jwt) {
		jwtRedisRepository.delete(jwt);
	}
}
