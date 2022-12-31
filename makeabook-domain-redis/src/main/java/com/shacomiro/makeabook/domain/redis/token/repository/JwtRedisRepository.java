package com.shacomiro.makeabook.domain.redis.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;

public interface JwtRedisRepository extends CrudRepository<Jwt, String> {

	public List<Jwt> findAllByKey(String key);

	public Optional<Jwt> findByKeyAndType(String key, String type);

	public List<Jwt> findAllByKeyAndType(String key, String type);

	public void deleteByKeyAndType(String key, String type);
}
