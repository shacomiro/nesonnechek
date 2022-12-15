package com.shacomiro.makeabook.api.global.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userUniqueKey) throws UsernameNotFoundException {
		return userRepository.findByEmail(Email.byValue().value(userUniqueKey).build())
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new NotFoundException("User '" + userUniqueKey + "' not found"));
	}
}
