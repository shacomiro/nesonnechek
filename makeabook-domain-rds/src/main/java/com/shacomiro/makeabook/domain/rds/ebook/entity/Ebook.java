package com.shacomiro.makeabook.domain.rds.ebook.entity;

import static java.time.LocalDateTime.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ebook")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ebook {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "uuid")
	private String uuid;
	@Column(name = "name")
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private EbookType type;
	@Enumerated(EnumType.STRING)
	@Column(name = "extension")
	private EbookExtension extension;
	@Column(name = "download_count")
	private int downloadCount;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;
	@Column(name = "is_exist")
	private boolean isExist;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder(builderClassName = "ByEbookFileInfo", builderMethodName = "byEbookFileInfo")
	public Ebook(String uuid, String name, EbookType type, EbookExtension extension, User user) {
		this(null, uuid, name, type, extension, 0, null, null, true, user);
	}

	@Builder(builderClassName = "ByAllArguments", builderMethodName = "byAllArguments")
	public Ebook(Long id, String uuid, String name, EbookType type, EbookExtension extension,
			int downloadCount, LocalDateTime createdAt, LocalDateTime expiredAt, boolean isExist, User user) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.extension = extension;
		this.downloadCount = downloadCount;
		this.createdAt = defaultIfNull(createdAt, now());
		this.expiredAt = defaultIfNull(expiredAt, now().plusDays(3));
		this.isExist = defaultIfNull(isExist, true);
		this.user = user;
	}

	public String getFileName() {
		return uuid + "." + extension;
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
