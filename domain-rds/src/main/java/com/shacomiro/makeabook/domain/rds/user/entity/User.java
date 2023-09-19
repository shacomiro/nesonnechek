package com.shacomiro.makeabook.domain.rds.user.entity;

import static java.time.LocalDateTime.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.global.validation.annotation.ValidEnumCollection;
import com.shacomiro.makeabook.domain.rds.user.exception.UserDeleteException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "email", unique = true))
	@Valid
	@NotNull(message = "Email must be provided")
	private Email email;
	@Column(name = "password")
	@NotBlank(message = "Password must be provided")
	private String password;
	@Column(name = "username", unique = true)
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
	@CollectionTable(name = "user_roles_tb", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	@ValidEnumCollection(enumClass = UserRole.class, message = "User role value is invalid")
	private List<UserRole> roles;
	@BatchSize(size = 100)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Ebook> ebooks;

	@Builder(builderClassName = "BySignUpInfos", builderMethodName = "bySignUpInfos")
	public User(Email email, String encryptedPassword, String username, List<UserRole> roles) {
		this(null, email, encryptedPassword, username, 0, null, now(), roles);
	}

	@Builder(builderClassName = "ByUpdateUserInfos", builderMethodName = "byUpdateUserInfos")
	public User(User currentUser, String encryptedPassword, String username) {
		this(currentUser.id, currentUser.getEmail(),
				encryptedPassword == null ? currentUser.getPassword() : encryptedPassword,
				username == null ? currentUser.getUsername() : username,
				currentUser.getLoginCount(),
				currentUser.getLastLoginAt(), currentUser.getCreatedAt(), currentUser.getRoles());
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
		this.ebooks = new ArrayList<>();
	}

	public void afterLoginSuccess() {
		loginCount++;
		lastLoginAt = now();
	}

	public void verifyDelete() {
		if (!ebooks.isEmpty()) {
			throw new UserDeleteException("Current user's ebooks are still existing");
		}
	}
}
