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

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.aws.s3.exception.AwsS3ObjectHandleException;
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
import com.shacomiro.makeabook.domain.global.exception.AwsS3ClientException;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.repository.EbookRdsRepository;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EbookService {
	private final EbookRdsRepository ebookRdsRepository;
	private final UserRdsRepository userRdsRepository;
	private final EpubManager epubManager;
	private final AwsS3ClientManager awsS3ClientManager;
	private final AwsS3Configuration awsS3Configuration;

	public Optional<Ebook> createEbook(EbookRequestDto ebookRequestDto) {
		Optional<Ebook> ebook = Optional.empty();
		EpubFileInfo epubFileInfo = null;
		List<ContentTempFileInfo> contentTempFileInfos = saveUploadToTempFile(ebookRequestDto.getFiles());

		if (ebookRequestDto.getEbookType().equals(EbookType.EPUB2)) {
			ContentTempFileInfo txtTempFileInfo = contentTempFileInfos.get(0);
			epubFileInfo = epubManager.translateTxtToEpub2(ebookRequestDto.getUuid(), txtTempFileInfo);

			try {
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(ebookRequestDto.getEbookType().getContentType());
				objectMetadata.setContentLength(Files.size(epubFileInfo.getFilePath()));

				awsS3ClientManager.getAwsS3ObjectHandler()
						.uploadS3Object(
								awsS3Configuration.getBucketName(),
								epubFileInfo.getUuid(),
								epubFileInfo.getFilePath().toAbsolutePath().normalize().toString(),
								objectMetadata
						);
			} catch (AwsS3ObjectHandleException e) {
				throw new AwsS3ClientException("Error occurred while saving ebook file");
			} catch (IOException e) {
				throw new FileIOException("I/O error occurred while measuring ebook file length");
			}

			ebook = Optional.of(ebookRdsRepository
					.save(Ebook.byEbookCreationResult()
							.uuid(epubFileInfo.getUuid())
							.name(epubFileInfo.getFilename())
							.type(EbookType.EPUB2)
							.user(userRdsRepository.findByEmail(Email.byValue().value(ebookRequestDto.getUploader()).build())
									.orElseThrow(() ->
											new UserNotFoundException(
													"Could not find user '" + ebookRequestDto.getUploader() + "'."))
							)
							.build())
			);
		}

		try {
			if (epubFileInfo != null) {
				Files.deleteIfExists(epubFileInfo.getFilePath());
			}

			for (ContentTempFileInfo tempFile : contentTempFileInfos) {
				Files.deleteIfExists(tempFile.getTempFilePath());
			}
		} catch (IOException e) {
			throw new FileIOException("I/O error occurred while deleting files");
		}

		return ebook;
	}

	public Page<Ebook> findEbooksByUser(Pageable pageable, User user) {
		return ebookRdsRepository.findAllByUser(pageable, user);
	}

	public Ebook findEbookByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));

		return ebookRdsRepository.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));
	}

	public EbookResourceDto getEbookResourceByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));
		Ebook currentEbook = ebookRdsRepository.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));

		currentEbook.verifyExpiration();
		currentEbook.addDownloadCount();
		ebookRdsRepository.save(currentEbook);

		try {
			ByteArrayResource ebookResource = new ByteArrayResource(
					awsS3ClientManager.getAwsS3ObjectHandler().downloadS3ObjectBytes(
							awsS3Configuration.getBucketName(),
							currentEbook.getUuid()
					));

			return new EbookResourceDto(ebookResource, currentEbook.getEbookFilename());
		} catch (AwsS3ObjectHandleException e) {
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
