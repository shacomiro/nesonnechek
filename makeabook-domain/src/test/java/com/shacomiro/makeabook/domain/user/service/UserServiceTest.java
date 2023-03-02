package com.shacomiro.makeabook.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;
import com.shacomiro.makeabook.domain.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.user.dto.UpdateUserDto;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	private static final String USER_EMAIL = "user@email.com";
	private static final LocalDateTime NOW = LocalDateTime.now();
	@InjectMocks
	private UserService userService;
	@Mock
	private UserRdsRepository userRdsRepository;

	@Test
	@Order(1)
	@DisplayName("특정 이메일을 가진 유저 반환")
	void findUserByEmailValue() {
		//given
		User user = new User(1L, new Email(USER_EMAIL), "password", "user", 0, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));

		//when
		User result = userService.findUserByEmail(USER_EMAIL);

		//then
		assertEquals(user.getEmail(), result.getEmail());
	}

	@Test
	@Order(2)
	@DisplayName("특정 유저네임을 가진 유저 반환")
	void findUserByUsername() {
		//given
		String username = "user";
		User user = new User(1L, new Email(USER_EMAIL), "password", "user", 0, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByUsername(username)).willReturn(Optional.of(user));

		//when
		Optional<User> result = userService.findUserByUsername(username);

		//then
		assertTrue(result.isPresent());
		assertEquals(user.getUsername(), result.get().getUsername());
	}

	@Test
	@Order(3)
	@DisplayName("로그인 시도 유저의 암호화 된 비밀번호 반환")
	void getSignInUserEncryptedPassword() {
		//given
		User user = new User(1L, new Email(USER_EMAIL), "password", "user", 0, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));

		//when
		String result = userService.getSignInUserEncryptedPassword(USER_EMAIL);

		//then
		assertNotNull(result);
	}

	@Test
	@Order(4)
	@DisplayName("유저 가입시 가입된 유저 반환")
	void signUpUser() {
		//given
		String username = "user";
		SignUpDto signUpDto = new SignUpDto(USER_EMAIL, "password", username);
		Email email = new Email(signUpDto.getEmail());
		User user = new User(1L, email, "password", username, 0, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.empty());
		given(userRdsRepository.findByUsername(username)).willReturn(Optional.empty());
		given(userRdsRepository.save(any(User.class))).willReturn(user);

		//when
		User result = userService.signUpUser(signUpDto);

		//then
		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());
		assertEquals(user.getEmail(), result.getEmail());
	}

	@Test
	@Order(5)
	@DisplayName("유저 정보 수정시 수정된 유저 반환")
	void updateUser() {
		//given
		String newUsername = "new-user";
		String newPassword = "new-password";
		UpdateUserDto updateUserDto = new UpdateUserDto(USER_EMAIL, newPassword, newUsername);
		Email email = new Email(updateUserDto.getEmail());
		User user = new User(1L, email, "password", "user", 0, null, NOW, List.of(UserRole.USER));
		User updatedUser = new User(1L, email, updateUserDto.getEncryptedPassword(), updateUserDto.getUsername(), 0, null, NOW,
				List.of(UserRole.USER));

		given(userRdsRepository.findByUsername(newUsername)).willReturn(Optional.empty());
		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));
		given(userRdsRepository.save(any(User.class))).willReturn(updatedUser);

		//when
		User result = userService.updateUser(updateUserDto);

		//then
		assertEquals(updatedUser.getEmail(), result.getEmail());
		assertEquals(updatedUser.getUsername(), result.getUsername());
		assertEquals(updatedUser.getPassword(), result.getPassword());
	}

	@Test
	@Order(6)
	@DisplayName("로그인 카운트 증가")
	void updateLoginCount() {
		//given
		int defaultLoginCount = 0;
		User user = new User(1L, new Email(USER_EMAIL), "password", "user", defaultLoginCount, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));
		given(userRdsRepository.save(user)).willReturn(user);

		//when
		userService.updateLoginCount(USER_EMAIL);

		//then
		assertEquals(defaultLoginCount + 1, user.getLoginCount());
	}

	@Test
	@Order(7)
	@DisplayName("유저 제거")
	void deleteUser() {
		//given
		User user = new User(1L, new Email(USER_EMAIL), "password", "user", 0, null, NOW, List.of(UserRole.USER));

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));

		//when
		userService.deleteUser(USER_EMAIL);

		//then
		verify(userRdsRepository, Mockito.atLeastOnce()).delete(user);
	}
}
