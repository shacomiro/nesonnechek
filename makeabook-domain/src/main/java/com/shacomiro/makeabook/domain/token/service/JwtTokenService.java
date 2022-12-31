package com.shacomiro.makeabook.domain.token.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtTokenService {
	private final JwtTokenRedisRepository jwtTokenRedisRepository;
}
