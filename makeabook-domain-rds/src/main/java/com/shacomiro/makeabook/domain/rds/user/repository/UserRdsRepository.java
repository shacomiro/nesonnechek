package com.shacomiro.makeabook.domain.rds.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;

public interface UserRdsRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(Email email);

	public Optional<User> findByUsername(String username);
}
