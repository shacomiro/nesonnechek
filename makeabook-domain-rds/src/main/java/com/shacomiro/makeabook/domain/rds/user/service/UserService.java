package com.shacomiro.makeabook.domain.rds.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public Optional<User> findByEmail(Email email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User save(@Valid SignUpDto signUpDto) {
		return userRepository.save(
				User.bySignUpDto()
						.signUpDto(signUpDto)
						.roles(List.of(UserRole.USER))
						.build()
		);
	}
}
