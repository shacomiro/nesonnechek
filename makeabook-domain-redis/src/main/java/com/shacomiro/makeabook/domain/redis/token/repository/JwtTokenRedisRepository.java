package com.shacomiro.makeabook.domain.redis.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;

public interface JwtTokenRedisRepository extends CrudRepository<JwtToken, String> {

	public List<JwtToken> findAllByKey(String key);

	public Optional<JwtToken> findByKeyAndType(String key, String type);

	public List<JwtToken> findAllByKeyAndType(String key, String type);
}
