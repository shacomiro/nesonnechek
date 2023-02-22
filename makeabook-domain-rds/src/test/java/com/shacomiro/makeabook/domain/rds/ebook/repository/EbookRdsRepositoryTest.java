package com.shacomiro.makeabook.domain.rds.ebook.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = {"classpath:application-domain-rds-test.yaml"})
@ActiveProfiles(value = {"domain-rds-test"})
class EbookRdsRepositoryTest {
	private final static String EXIST_UUID = "550e8400-e29b-41d4-a716-446655440001";
	private final static String NEW_UUID = "550e8400-e29b-41d4-a716-446655440008";
	private final EbookRdsRepository ebookRdsRepository;

	@Autowired
	public EbookRdsRepositoryTest(EbookRdsRepository ebookRdsRepository) {
		this.ebookRdsRepository = ebookRdsRepository;
	}

	@Test
	@Order(1)
	@DisplayName("전자책 저장 후 조회")
	void insertAndFindEbook() {
		//given
		Ebook ebook = Ebook.byEbookCreationResult()
				.uuid(NEW_UUID)
				.name("New Ebook")
				.type(EbookType.EPUB2)
				.user(new User(1L, new Email("user1@email.com"), "user1_password", "USER1", 3,
						LocalDateTime.of(2022, 12, 30, 12, 0),
						LocalDateTime.of(2022, 12, 30, 12, 0), List.of(UserRole.USER))
				)
				.build();
		ebookRdsRepository.save(ebook);

		//when
		Optional<Ebook> result = ebookRdsRepository.findByUuid(NEW_UUID);

		//then
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(ebook, result.get());
	}

	@Test
	@Order(2)
	@DisplayName("전자책 목록 조회")
	void findAllEbooks() {
		//when
		List<Ebook> ebooks = ebookRdsRepository.findAll();

		//then
		Assertions.assertFalse(ebooks.isEmpty());
	}

	@Test
	@Order(3)
	@DisplayName("전자책 조회 후 수정")
	void updateEbook() {
		//given
		Ebook ebook = ebookRdsRepository.findByUuid(EXIST_UUID).orElseThrow();
		int downloadCount = ebook.getDownloadCount();

		//when
		ebook.addDownloadCount();
		ebookRdsRepository.save(ebook);
		Optional<Ebook> result = ebookRdsRepository.findByUuid(EXIST_UUID);

		//then
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(downloadCount + 1, result.get().getDownloadCount());
	}

	@Test
	@Order(4)
	@DisplayName("전자책 삭제")
	void deleteEbook() {
		//given
		Ebook ebook = ebookRdsRepository.findByUuid(EXIST_UUID).orElseThrow();
		long ebookCount = ebookRdsRepository.count();

		//when
		ebookRdsRepository.delete(ebook);
		long afterDeleteEbookCount = ebookRdsRepository.count();

		//then
		Assertions.assertEquals(ebookCount - 1, afterDeleteEbookCount);
	}
}

