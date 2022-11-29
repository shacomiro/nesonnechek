package com.shacomiro.makeabook.domain.rds.user.entity;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "email"))
	private Email email;
	@Column(name = "password")
	private String password;
	@Column(name = "username")
	private String username;
	@Column(name = "login_count")
	private int loginCount;
	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder(builderClassName = "ByAllArguments", builderMethodName = "byAllArguments")
	public User(Long id, Email email, String password, String username, int loginCount, LocalDateTime lastLoginAt,
			LocalDateTime createdAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.loginCount = loginCount;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
	}

	public void afterLoginSuccess() {
		loginCount++;
		lastLoginAt = now();
	}
}
