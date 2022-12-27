package com.shacomiro.makeabook.domain.redis.token.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;

public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {

	public List<JwtToken> findAllByKey(String key);

	public List<JwtToken> findAllByKeyAndType(String key, String type);
}
