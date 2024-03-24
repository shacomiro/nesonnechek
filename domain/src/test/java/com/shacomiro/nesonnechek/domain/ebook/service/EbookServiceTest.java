package com.shacomiro.nesonnechek.domain.ebook.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.aws.s3.handler.AwsS3ObjectHandler;
import com.shacomiro.epub.EpubManager;
import com.shacomiro.epub.domain.ContentTempFileInfo;
import com.shacomiro.epub.domain.EpubFileInfo;
import com.shacomiro.nesonnechek.domain.ebook.dto.EbookRequestDto;
import com.shacomiro.nesonnechek.domain.ebook.dto.EbookResourceDto;
import com.shacomiro.nesonnechek.domain.global.config.aws.AwsS3Configuration;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.Ebook;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.EbookType;
import com.shacomiro.nesonnechek.domain.rds.ebook.repository.EbookRdsRepository;
import com.shacomiro.nesonnechek.domain.rds.user.entity.Email;
import com.shacomiro.nesonnechek.domain.rds.user.entity.User;
import com.shacomiro.nesonnechek.domain.rds.user.entity.UserRole;
import com.shacomiro.nesonnechek.domain.rds.user.repository.UserRdsRepository;

@ExtendWith(MockitoExtension.class)
class EbookServiceTest {
	private static final String TEST_RESOURCE_PATH = "./src/test/resources";
	private static final String TEST_TXT_FILENAME = "애국가.txt";
	private static final String EPUB_FILENAME = "test_ebook.epub";
	private static final String TEST_EPUB_FILENAME = "애국가.epub";
	private static final String UPLOADER_EMAIL = "user@email.com";
	private static final LocalDateTime NOW = LocalDateTime.now();
	@InjectMocks
	private EbookService ebookService;
	@Mock
	private EbookRdsRepository ebookRdsRepository;
	@Mock
	private UserRdsRepository userRdsRepository;
	@Mock
	private EpubManager epubManager;
	@Mock
	private AwsS3Configuration awsS3Configuration;
	@Mock
	private AwsS3ClientManager awsS3ClientManager;
	@Mock
	private AwsS3ObjectHandler awsS3ObjectHandler;

	@BeforeAll
	static void beforeAll() throws IOException {
		Files.createDirectories(Paths.get(TEST_RESOURCE_PATH + File.separatorChar + "files" + File.separatorChar + "ebook"));
		Files.createDirectories(Paths.get(TEST_RESOURCE_PATH + File.separatorChar + "files" + File.separatorChar + "content"));
	}

	@BeforeEach
	void setUp() throws IOException {
		Files.copy(
				Path.of(TEST_RESOURCE_PATH, File.separatorChar + EPUB_FILENAME),
				Path.of(TEST_RESOURCE_PATH,
						File.separatorChar + "files" + File.separatorChar + "ebook" + File.separatorChar + TEST_EPUB_FILENAME)
		);
	}

	@AfterEach
	void tearDown() throws IOException {
		Files.deleteIfExists(
				Path.of(TEST_RESOURCE_PATH,
						File.separatorChar + "files" + File.separatorChar + "ebook" + File.separatorChar + TEST_EPUB_FILENAME)
		);
	}

	@Test
	@Order(1)
	@DisplayName("신규 txt 파일 기반 전자책 생성")
	void createNewTxtEbook() throws IOException {
		//given
		User user = new User(1L, new Email(UPLOADER_EMAIL), "password", "user",
				0, null, NOW, List.of(UserRole.USER));
		Path testEpubPath = Path.of(TEST_RESOURCE_PATH,
				File.separatorChar + "files" + File.separatorChar + "ebook" + File.separatorChar + TEST_EPUB_FILENAME);
		EbookRequestDto ebookRequestDto = new EbookRequestDto(
				Files.readAllBytes(testEpubPath), TEST_TXT_FILENAME, UPLOADER_EMAIL, EbookType.EPUB2
		);
		String uuid = UUID.randomUUID().toString();

		given(epubManager.translateTxtToEpub2(eq(ebookRequestDto.getUuid()), any(ContentTempFileInfo.class)))
				.willReturn(new EpubFileInfo(uuid, TEST_EPUB_FILENAME, testEpubPath));
		given(epubManager.getContentDir()).willReturn(TEST_RESOURCE_PATH + "/files/content");
		given(awsS3Configuration.getBucketName()).willReturn("test-bucket");
		given(awsS3ClientManager.getAwsS3ObjectHandler()).willReturn(awsS3ObjectHandler);
		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));
		given(ebookRdsRepository.save(any(Ebook.class)))
				.willReturn(new Ebook(1L, uuid, FilenameUtils.removeExtension(TEST_EPUB_FILENAME), EbookType.EPUB2,
						0, NOW, NOW.plusDays(30), true, user));
		//when
		Optional<Ebook> result = ebookService.createEbook(ebookRequestDto);

		//then
		assertTrue(result.isPresent());
		assertEquals(uuid, result.get().getUuid());
		assertEquals(TEST_EPUB_FILENAME, result.get().getEbookFilename());
		assertEquals(user, result.get().getUser());
		assertEquals(0,
				Files.list(Path.of(TEST_RESOURCE_PATH, File.separatorChar + "files" + File.separatorChar + "content")).count());
		assertFalse(Files.exists(testEpubPath));
	}

	@Test
	@Order(2)
	@DisplayName("특정 유저의 페이징 된 전자책 목록 반환")
	void findEbooksByUserId() {
		//given
		User user = new User(1L, new Email(UPLOADER_EMAIL), "password", "user",
				0, null, NOW, List.of(UserRole.USER));
		int pageNumber = 0;
		int pageSize = 10;
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		List<Ebook> ebooks = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			ebooks.add(new Ebook(1L, UUID.randomUUID().toString(), "ebook" + i, EbookType.EPUB2,
					0, NOW, NOW.plusDays(30), true, user));
		}

		given(ebookRdsRepository.findAllByUser(eq(pageRequest), any(User.class)))
				.willReturn(new PageImpl<>(ebooks, pageRequest, ebooks.size()));

		//when
		Page<Ebook> result = ebookService.findEbooksByUser(pageRequest, user);

		//then
		assertTrue(result.hasContent());
		assertEquals(pageNumber, result.getPageable().getPageNumber());
		assertEquals(pageSize, result.getPageable().getPageSize());
		assertEquals(ebooks.size(), result.getTotalElements());
		assertEquals(Ebook.class, result.getContent().get(0).getClass());
	}

	@Test
	@Order(3)
	@DisplayName("특정 유저의 전자책 정보 반환")
	void findSpecificEbookByUuidAndEmail() {
		//given
		Email email = new Email(UPLOADER_EMAIL);
		User user = new User(1L, email, "password", "user", 0, null, NOW, List.of(UserRole.USER));
		String uuid = UUID.randomUUID().toString();
		Ebook ebook = new Ebook(1L, uuid, "ebook", EbookType.EPUB2, 0, NOW, NOW.plusDays(30), true, user);

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));
		given(ebookRdsRepository.findByUuidAndUser(uuid, user)).willReturn(Optional.of(ebook));

		//when
		Ebook result = ebookService.findEbookByUuidAndEmail(uuid, UPLOADER_EMAIL);

		//then
		assertEquals(uuid, result.getUuid());
		assertEquals(user, result.getUser());
	}

	@Test
	@Order(4)
	@DisplayName("특정 전자책의 리소스 반환")
	void getSpecificEbookResourceByUuidAndEmail() throws IOException {
		//given
		Email email = new Email(UPLOADER_EMAIL);
		User user = new User(1L, email, "password", "user", 0, null, NOW, List.of(UserRole.USER));
		String uuid = UUID.randomUUID().toString();
		Ebook ebook = new Ebook(1L, uuid, "ebook", EbookType.EPUB2, 0, NOW, NOW.plusDays(30), true, user);

		given(userRdsRepository.findByEmail(any(Email.class))).willReturn(Optional.of(user));
		given(ebookRdsRepository.findByUuidAndUser(uuid, user)).willReturn(Optional.of(ebook));
		given(ebookRdsRepository.save(ebook)).willReturn(ebook);
		given(awsS3Configuration.getBucketName()).willReturn("test-bucket");
		given(awsS3ClientManager.getAwsS3ObjectHandler()).willReturn(awsS3ObjectHandler);
		given(awsS3ObjectHandler.downloadS3ObjectBytes(anyString(), eq(ebook.getUuid())))
				.willReturn(Files.readAllBytes(Path.of(TEST_RESOURCE_PATH,
						File.separatorChar + "files" + File.separatorChar + "ebook" + File.separatorChar + TEST_EPUB_FILENAME)));

		//when
		EbookResourceDto result = ebookService.getEbookResourceByUuidAndEmail(uuid, UPLOADER_EMAIL);

		//then
		assertTrue(result.getEbookResource().exists());
		assertEquals(ebook.getEbookFilename(), result.getEbookFilename());
	}
}
