package com.shacomiro.makeabook.domain.rds.user.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-domain-rds-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRdsRepositoryTest {
	private static final String EXIST_USER_EMAIL = "user1@email.com";
	private static final String EXIST_NON_EBOOK_USER_EMAIL = "user5@email.com";
	private static final String NEW_USER_EMAIL = "user6@email.com";
	private final UserRdsRepository userRdsRepository;

	@Autowired
	public UserRdsRepositoryTest(UserRdsRepository userRdsRepository) {
		this.userRdsRepository = userRdsRepository;
	}

	@Test
	@Order(1)
	@DisplayName("유저 저장 후 조회")
	void insertAndFindUser() {
		//given
		User user = User.bySignUpInfos()
				.email(new Email(NEW_USER_EMAIL))
				.encryptedPassword("uesr6_password")
				.username("USER6")
				.roles(List.of(UserRole.USER))
				.build();
		userRdsRepository.save(user);

		//when
		Optional<User> result = userRdsRepository.findByEmail(new Email(NEW_USER_EMAIL));

		//then
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(user, result.get());
	}

	@Test
	@Order(2)
	@DisplayName("유저 목록 조회")
	void findAllUsers() {
		//when
		List<User> users = userRdsRepository.findAll();

		//then
		Assertions.assertFalse(users.isEmpty());
	}

	@Test
	@Order(3)
	@DisplayName("유저 조회 후 수정")
	void updateUser() {
		//given
		User user = userRdsRepository.findByEmail(new Email(EXIST_USER_EMAIL)).orElseThrow();
		int loginCount = user.getLoginCount();

		//when
		user.afterLoginSuccess();
		userRdsRepository.save(user);
		Optional<User> result = userRdsRepository.findByEmail(new Email(EXIST_USER_EMAIL));

		//then
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(loginCount + 1, result.get().getLoginCount());
	}

	@Test
	@Order(4)
	@DisplayName("유저 삭제")
	void deleteUser() {
		//given
		User user = userRdsRepository.findByEmail(new Email(EXIST_NON_EBOOK_USER_EMAIL)).orElseThrow();
		long userCount = userRdsRepository.count();

		//when
		userRdsRepository.delete(user);
		long afterDeleteUserCount = userRdsRepository.count();

		//then
		Assertions.assertEquals(userCount - 1, afterDeleteUserCount);
	}

	@Test
	@Order(5)
	@DisplayName("연결된 전자책 존재로 인한 유저 삭제 실패")
	void failDeleteUserByEbookExistence() {
		//given
		User user = userRdsRepository.findByEmail(new Email(EXIST_USER_EMAIL)).orElseThrow();

		//when
		userRdsRepository.delete(user);
		
		//then
		Assertions.assertThrows(DataIntegrityViolationException.class, userRdsRepository::flush);
	}
}
