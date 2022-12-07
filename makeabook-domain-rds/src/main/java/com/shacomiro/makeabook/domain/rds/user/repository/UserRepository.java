package com.shacomiro.makeabook.domain.rds.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shacomiro.makeabook.domain.rds.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
