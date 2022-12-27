package com.shacomiro.makeabook.domain.redis.token.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shacomiro.makeabook.domain.redis.token.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	public Optional<RefreshToken> findByToken(String token);
}
