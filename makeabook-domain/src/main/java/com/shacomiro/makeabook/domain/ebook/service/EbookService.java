package com.shacomiro.makeabook.domain.ebook.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.aws.s3.exception.AwsS3ObjectOperateException;
import com.shacomiro.epub.EpubManager;
import com.shacomiro.epub.domain.ContentTempFileInfo;
import com.shacomiro.epub.domain.EpubFileInfo;
import com.shacomiro.makeabook.core.global.exception.FileIOException;
import com.shacomiro.makeabook.core.util.IOUtils;
import com.shacomiro.makeabook.domain.ebook.dto.EbookRequestDto;
import com.shacomiro.makeabook.domain.ebook.dto.EbookResourceDto;
import com.shacomiro.makeabook.domain.ebook.dto.FileDto;
import com.shacomiro.makeabook.domain.ebook.exception.EbookNotFoundException;
import com.shacomiro.makeabook.domain.ebook.exception.EbookResourceNotFoundException;
import com.shacomiro.makeabook.domain.global.config.aws.AwsS3Configuration;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.service.EbookRdsService;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.service.UserRdsService;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EbookService {
	private final EbookRdsService ebookRdsService;
	private final UserRdsService userRdsService;
	private final EpubManager epubManager;
	private final AwsS3ClientManager awsS3ClientManager;
	private final AwsS3Configuration awsS3Configuration;

	public Optional<Ebook> createEbook(EbookRequestDto ebookRequestDto) {
		Optional<Ebook> ebook = Optional.empty();
		EpubFileInfo epubFileInfo;
		List<ContentTempFileInfo> contentTempFileInfos = saveUploadToTempFile(ebookRequestDto.getFiles());

		if (ebookRequestDto.getEbookType().equals(EbookType.EPUB2)) {
			ContentTempFileInfo txtTempFileInfo = contentTempFileInfos.get(0);
			epubFileInfo = epubManager.translateTxtToEpub2(ebookRequestDto.getUuid(), txtTempFileInfo);

			awsS3ClientManager.getAwsS3ObjectOperator()
					.uploadS3Object(
							awsS3Configuration.getBucketName(),
							epubFileInfo.getUuid(),
							epubFileInfo.getFilePath().toAbsolutePath().normalize().toString()
					);

			try {
				Files.deleteIfExists(epubFileInfo.getFilePath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			ebook = Optional.of(ebookRdsService
					.save(Ebook.byEbookCreationResult()
							.uuid(epubFileInfo.getUuid())
							.name(epubFileInfo.getFilename())
							.type(EbookType.EPUB2)
							.user(userRdsService.findByEmail(Email.byValue().value(ebookRequestDto.getUploader()).build())
									.orElseThrow(() ->
											new UserNotFoundException(
													"Could not find user '" + ebookRequestDto.getUploader() + "'."))
							)
							.build())
			);
		}

		for (ContentTempFileInfo tempFile : contentTempFileInfos) {
			try {
				Files.deleteIfExists(tempFile.getTempFilePath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return ebook;
	}

	public Page<Ebook> findEbooksByUserId(Pageable pageable, Long userId) {
		return ebookRdsService.findAllByUserId(pageable, userId);
	}

	public Ebook findEbookByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsService.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));

		return ebookRdsService.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));
	}

	public EbookResourceDto getEbookResourceByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsService.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));
		Ebook currentEbook = ebookRdsService.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));

		currentEbook.verifyExpiration();
		currentEbook.addDownloadCount();
		ebookRdsService.save(currentEbook);

		Path path = epubManager.getEpubFilePath(currentEbook.getType().getValue(), currentEbook.getOriginalFilename());
		if (Files.notExists(path)) {
			throw new FileIOException("Ebook resource not found.");
		}

		try {
			ByteArrayResource ebookResource = new ByteArrayResource(
					awsS3ClientManager.getAwsS3ObjectOperator().downloadS3ObjectBytes(
							awsS3Configuration.getBucketName(),
							currentEbook.getUuid()
					));

			return new EbookResourceDto(ebookResource, currentEbook.getEbookFilename());
		} catch (AwsS3ObjectOperateException e) {
			throw new EbookResourceNotFoundException("Ebook resource not found.");
		}
	}

	private List<ContentTempFileInfo> saveUploadToTempFile(List<FileDto> files) {
		Path contentDir = Paths.get(epubManager.getContentDir()).normalize().toAbsolutePath();

		return files.stream()
				.map(
						file -> {
							Path tempUploadFilePath = IOUtils.createTempFile(contentDir, file.getExtension());
							try (OutputStream os = Files.newOutputStream(tempUploadFilePath)) {
								os.write(file.getFileBais().readAllBytes());
							} catch (IOException e) {
								throw new RuntimeException(e);
							}

							return new ContentTempFileInfo(UUID.randomUUID().toString(), tempUploadFilePath,
									file.getOriginalFilename());
						}
				).collect(Collectors.toList());
	}
}
