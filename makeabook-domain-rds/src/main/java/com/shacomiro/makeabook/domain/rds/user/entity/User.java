package com.shacomiro.makeabook.domain.rds.user.entity;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Valid
	@NotNull(message = "Email must be provided")
	private Email email;
	@Column(name = "password")
	@NotBlank(message = "Password must be provided")
	private String password;
	@Column(name = "username")
	@NotBlank(message = "Username must be provided")
	@Size(min = 1, max = 20, message = "Username length must be between 1 and 10 characters")
	private String username;
	@Column(name = "login_count")
	private int loginCount;
	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;
	@Column(name = "created_at")
	@NotNull(message = "Created Date must be provided")
	private LocalDateTime createdAt;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private List<UserRole> roles;

	@Builder(builderClassName = "BySignUpDTO", builderMethodName = "bySignUpDTO")
	public User(Email email, String password, String username, List<UserRole> roles) {
		this(null, email, password, username, 0, null, now(), roles);
	}

	public User(Long id, Email email, String password, String username, int loginCount, LocalDateTime lastLoginAt,
			LocalDateTime createdAt, List<UserRole> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.loginCount = loginCount;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
		this.roles = roles;
	}

	public void afterLoginSuccess() {
		loginCount++;
		lastLoginAt = now();
	}
}
