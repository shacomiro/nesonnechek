package com.shacomiro.makeabook.domain.rds.ebook.entity;

import static java.time.LocalDateTime.*;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.shacomiro.makeabook.domain.rds.ebook.exception.EbookExpiredException;
import com.shacomiro.makeabook.domain.rds.global.validation.annotation.ValidEnum;
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
	@NotBlank(message = "UUID must be provided")
	@Size(min = 36, max = 36, message = "UUID value length must be 36 characters")
	private String uuid;
	@Column(name = "name")
	@NotBlank(message = "Name must be provided")
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	@ValidEnum(enumClass = EbookType.class, message = "Ebook type value is invalid")
	private EbookType type;
	@Column(name = "download_count")
	@NotNull(message = "Download count must be provided")
	private int downloadCount;
	@Column(name = "created_at")
	@NotNull(message = "Created date must be provided")
	private LocalDateTime createdAt;
	@Column(name = "expired_at")
	@NotNull(message = "Expired date must be provided")
	private LocalDateTime expiredAt;
	@Column(name = "is_exist")
	@NotNull(message = "exists must be provided")
	private boolean isExist;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder(builderClassName = "ByEbookCreationResult", builderMethodName = "byEbookCreationResult")
	public Ebook(String uuid, String name, EbookType type, User user) {
		this(null, uuid, name, type, 0, now(), now().plusDays(3), true, user);
	}

	public Ebook(Long id, String uuid, String name, EbookType type, int downloadCount, LocalDateTime createdAt,
			LocalDateTime expiredAt, boolean isExist, User user) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.downloadCount = downloadCount;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.isExist = isExist;
		this.user = user;
	}

	public String getOriginalFilename() {
		return uuid + type.getExtension();
	}

	public String getEbookFilename() {
		return name + type.getExtension();
	}

	public void addDownloadCount() {
		downloadCount++;
	}

	public boolean isExpired() {
		return !isExist || now().isAfter(expiredAt);
	}

	public void ceaseToExist() {
		this.isExist = false;
	}

	public void verifyExpiration() {
		if (isExpired()) {
			throw new EbookExpiredException("Ebook '" + name + "' is expired");
		}
	}
}
