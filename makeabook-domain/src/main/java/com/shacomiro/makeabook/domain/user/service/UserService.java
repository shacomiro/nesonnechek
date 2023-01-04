package com.shacomiro.makeabook.domain.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;
import com.shacomiro.makeabook.domain.rds.user.service.UserRdsService;
import com.shacomiro.makeabook.domain.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.user.exception.UserConflictException;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class UserService {
	private final UserRdsService userRdsService;

	public Optional<User> findUserByEmail(String emailValue) {
		return userRdsService.findByEmail(Email.byValue().value(emailValue).build());
	}

	public Optional<User> findUserByUsername(String username) {
		return userRdsService.findByUsername(username);
	}

	public String getSignInUserEncryptedPassword(String emailValue) {
		User signedInUser = userRdsService.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return signedInUser.getPassword();
	}

	public User signUpUser(@Valid SignUpDto signUpDto) {
		if (userRdsService.findByEmail(Email.byValue().value(signUpDto.getEmail()).build()).isPresent()) {
			throw new UserConflictException("Duplicate email");
		} else if (userRdsService.findByUsername(signUpDto.getUsername()).isPresent()) {
			throw new UserConflictException("Duplicate username");
		}

		return userRdsService.save(
				User.bySignUpInfos()
						.email(Email.byValue().value(signUpDto.getEmail()).build())
						.encryptedPassword(signUpDto.getEncryptedPassword())
						.username(signUpDto.getUsername())
						.roles(List.of(UserRole.USER))
						.build()
		);
	}

}
