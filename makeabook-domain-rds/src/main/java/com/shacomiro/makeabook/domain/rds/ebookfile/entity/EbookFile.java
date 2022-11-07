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

	@Column(name = "file_extention")
	private String fileExtention;

	@Column(name = "download_url")
	private String downloadUrl;

	@Column(name = "download_count")
	private int downloadCount;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	@ManyToOne
	@JoinColumn(name = "user_seq")
	private User user;

	@Builder
	public EbookFile(String uuid, String filename, String fileType, String fileExtention, String downloadUrl,
			User user) {
		this(null, uuid, filename, fileType, fileExtention, downloadUrl, 0, null, null, user);
	}

	@Builder
	public EbookFile(Long seq, String uuid, String filename, String fileType, String fileExtention, String downloadUrl,
			int downloadCount,
			LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
		this.seq = seq;
		this.uuid = uuid;
		this.filename = filename;
		this.fileType = fileType;
		this.fileExtention = fileExtention;
		this.downloadUrl = downloadUrl;
		this.downloadCount = downloadCount;
		this.createdAt = defaultIfNull(createdAt, now());
		this.expiredAt = defaultIfNull(expiredAt, now().plusDays(3));
		this.user = user;
	}

	public boolean isExpired() {
		return expiredAt.compareTo(now()) < 0;
	}
}
