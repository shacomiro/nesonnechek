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
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;
import com.shacomiro.makeabook.domain.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.user.dto.UpdateUserDto;
import com.shacomiro.makeabook.domain.user.exception.UserConflictException;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class UserService {
	private final UserRdsRepository userRdsRepository;

	public User findUserByEmail(String emailValue) {
		return userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));
	}

	public Optional<User> findUserByUsername(String username) {
		return userRdsRepository.findByUsername(username);
	}

	public String getSignInUserEncryptedPassword(String emailValue) {
		User signedInUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return signedInUser.getPassword();
	}

	public User signUpUser(@Valid SignUpDto signUpDto) {
		if (userRdsRepository.findByEmail(Email.byValue().value(signUpDto.getEmail()).build()).isPresent()) {
			throw new UserConflictException("Duplicate email");
		} else if (userRdsRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
			throw new UserConflictException("Duplicate username");
		}

		return userRdsRepository.save(
				User.bySignUpInfos()
						.email(Email.byValue().value(signUpDto.getEmail()).build())
						.encryptedPassword(signUpDto.getEncryptedPassword())
						.username(signUpDto.getUsername())
						.roles(List.of(UserRole.USER))
						.build()
		);
	}

	public User updateUser(@Valid UpdateUserDto updateUserDto) {
		if (userRdsRepository.findByUsername(updateUserDto.getUsername()).isPresent()) {
			throw new UserConflictException("Duplicate username");
		}

		return userRdsRepository.findByEmail(Email.byValue().value(updateUserDto.getEmail()).build())
				.map(currentUser -> userRdsRepository.save(User.byUpdateUserInfos()
						.currentUser(currentUser)
						.encryptedPassword(updateUserDto.getEncryptedPassword())
						.username(updateUserDto.getUsername())
						.build())
				)
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + updateUserDto.getEmail() + "'."));
	}

	public void updateLoginCount(String emailValue) {
		User currentUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));

		currentUser.afterLoginSuccess();
		userRdsRepository.save(currentUser);
	}

	public void deleteUser(String emailValue) {
		User currentUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));

		currentUser.verifyDelete();

		userRdsRepository.delete(currentUser);
	}
}
