package com.shacomiro.makeabook.api.global.security.principal;

import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.cache.global.config.CacheKey;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {
	private final UserRdsRepository userRdsRepository;

	@Override
	@Cacheable(value = CacheKey.SIGN_IN_USER, key = "#userUniqueKey", unless = "#result == null")
	public UserDetails loadUserByUsername(String userUniqueKey) throws UsernameNotFoundException {
		User loadedUser = userRdsRepository.findByEmail(Email.byValue().value(userUniqueKey).build())
				.orElseThrow(() -> new UsernameNotFoundException("User '" + userUniqueKey + "' not found"));

		return UserPrincipal.byUserInfos()
				.email(loadedUser.getEmail().getValue())
				.password(loadedUser.getPassword())
				.username(loadedUser.getUsername())
				.authorities(
						loadedUser.getRoles()
								.stream()
								.map(role -> new SimpleGrantedAuthority(role.name()))
								.collect(Collectors.toList())
				)
				.build();
	}
}
//TODO (API <-> 도메인) 간 통신을 통해 User 정보를 DTO로 받는다고 가정한다.
// 따라서 UserDetails, UserDetailsService는 API에 포함되어 (Filter <-> authenticationManager <-> AuthenticationProvider) 순서의 인증을 거치도록 리팩토링 해야한다.
// https://junhyunny.github.io/spring-boot/spring-security/make-authentication-filter/
// https://junhyunny.github.io/spring-boot/spring-security/make-authentication-provider/
// 위 2개 링크 참조.
