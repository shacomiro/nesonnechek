package com.shacomiro.makeabook.domain.rds.ebookfile.entity;

import static java.time.LocalDateTime.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shacomiro.makeabook.domain.rds.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ebook_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EbookFile {
	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "filename")
	private String filename;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "file_extension")
	private String fileExtension;

	@Column(name = "download_url")
	private String downloadUrl;

	@Column(name = "download_count")
	private int downloadCount;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	@Column(name = "is_exist")
	private boolean isExist;

	@ManyToOne
	@JoinColumn(name = "user_seq")
	private User user;

	@Builder(builderClassName = "ByEbookFileInfo", builderMethodName = "byEbookFileInfo")
	public EbookFile(String uuid, String filename, String fileType, String fileExtension, String downloadUrl,
			User user) {
		this(null, uuid, filename, fileType, fileExtension, downloadUrl, 0, null, null, true, user);
	}

	@Builder(builderClassName = "ByAllArguments", builderMethodName = "byAllArguments")
	public EbookFile(Long seq, String uuid, String filename, String fileType, String fileExtension, String downloadUrl,
			int downloadCount, LocalDateTime createdAt, LocalDateTime expiredAt, boolean isExist, User user) {
		this.seq = seq;
		this.uuid = uuid;
		this.filename = filename;
		this.fileType = fileType;
		this.fileExtension = fileExtension;
		this.downloadUrl = downloadUrl;
		this.downloadCount = downloadCount;
		this.createdAt = defaultIfNull(createdAt, now());
		this.expiredAt = defaultIfNull(expiredAt, now().plusDays(3));
		this.isExist = defaultIfNull(isExist, true);
		this.user = user;
	}

	public void addDownloadCount() {
		downloadCount++;
	}

	public boolean isExpired() {
		return !isExist || expiredAt.compareTo(now()) < 0;
	}

	public void updateExists() {
		this.isExist = false;
	}
}
