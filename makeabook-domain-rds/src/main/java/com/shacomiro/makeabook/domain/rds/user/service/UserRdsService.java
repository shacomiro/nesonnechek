package com.shacomiro.makeabook.domain.rds.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class UserRdsService {
	private final UserRdsRepository userRdsRepository;

	public Optional<User> findByEmail(Email email) {
		return userRdsRepository.findByEmail(email);
	}

	public Optional<User> findByUsername(String username) {
		return userRdsRepository.findByUsername(username);
	}

	public User save(User user) {
		return userRdsRepository.save(user);
	}

	public void delete(User user) {
		userRdsRepository.delete(user);
	}
}
