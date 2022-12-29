package com.shacomiro.makeabook.api.global.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.shacomiro.makeabook.api.global.error.JwtException;
import com.shacomiro.makeabook.api.global.security.userdetails.CustomUserDetails;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
	private final JwtTokenRepository jwtTokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (authentication != null) {
			String emailValue = ((CustomUserDetails)authentication.getPrincipal()).getEmail();

			if (!jwtTokenRepository.findAllByKey(emailValue).isEmpty()) {
				jwtTokenRepository.deleteAll(jwtTokenRepository.findAllByKey(emailValue));
			} else {
				throw new JwtException("User info not matched.");
			}
		} else {
			throw new JwtException("User Authentication info not found.");
		}
	}
}
