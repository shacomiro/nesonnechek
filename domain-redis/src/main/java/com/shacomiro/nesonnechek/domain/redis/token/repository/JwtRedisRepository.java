package com.shacomiro.nesonnechek.domain.redis.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shacomiro.nesonnechek.domain.redis.token.entity.Jwt;

public interface JwtRedisRepository extends CrudRepository<Jwt, String> {

	public List<Jwt> findAllByKey(String key);

	public Optional<Jwt> findByKeyAndPurpose(String key, String type);

	public List<Jwt> findAllByKeyAndPurpose(String key, String type);
}
