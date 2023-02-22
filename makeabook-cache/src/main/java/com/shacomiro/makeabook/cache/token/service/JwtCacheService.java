package com.shacomiro.makeabook.cache.token.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shacomiro.makeabook.cache.token.dto.JwtCacheDto;
import com.shacomiro.makeabook.cache.token.exception.JwtCacheExpiredException;
import com.shacomiro.makeabook.domain.redis.token.entity.Jwt;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtCacheService {
	private final JwtRedisRepository jwtRedisRepository;

	public void verifyJwt(String key, String reqToken, String purpose) {
		jwtRedisRepository.findByKeyAndPurpose(key, purpose)
				.ifPresent(refreshJwt -> {
					if (!refreshJwt.getToken().equals(reqToken)) {
						throw new JwtCacheExpiredException("Expired " + purpose + " token.");
					}
				});
	}

	public Jwt saveJwt(JwtCacheDto jwtCacheDto) {
		return jwtRedisRepository.save(
				Jwt.byAllParameter()
						.id(jwtCacheDto.getId())
						.key(jwtCacheDto.getKey())
						.purpose(jwtCacheDto.getPurpose())
						.token(jwtCacheDto.getToken())
						.expiration(jwtCacheDto.getExpiration())
						.build()
		);
	}

	public void deleteRefreshJwtIfKeyExist(String key, String purpose) {
		jwtRedisRepository.findByKeyAndPurpose(key, purpose).ifPresent(jwtRedisRepository::delete);
	}
}
