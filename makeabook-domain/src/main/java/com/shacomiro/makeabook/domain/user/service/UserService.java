package com.shacomiro.makeabook.domain.user.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.user.service.UserRdsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRdsService userRdsService;

}
