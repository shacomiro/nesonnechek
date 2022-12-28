package com.shacomiro.makeabook.api.global.security.userdetails;

import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRepository;
import com.shacomiro.makeabook.domain.redis.global.config.cache.CacheKey;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	@Cacheable(value = CacheKey.SIGN_IN_USER, key = "#userUniqueKey", unless = "#result == null")
	public UserDetails loadUserByUsername(String userUniqueKey) throws UsernameNotFoundException {
		return userRepository.findByEmail(Email.byValue().value(userUniqueKey).build())
				.map(user -> CustomUserDetails.byAllParameter()
						.email(user.getEmail().getValue())
						.password(user.getPassword())
						.username(user.getUsername())
						.authorities(
								user.getRoles()
										.stream()
										.map(role -> new SimpleGrantedAuthority(role.name()))
										.collect(Collectors.toList())
						)
						.build()
				)
				.orElseThrow(() -> new UsernameNotFoundException("User '" + userUniqueKey + "' not found"));
	}
}
