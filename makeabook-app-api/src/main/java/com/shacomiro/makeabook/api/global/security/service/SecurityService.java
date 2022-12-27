package com.shacomiro.makeabook.api.global.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.global.error.ConflictException;
import com.shacomiro.makeabook.api.global.error.JwtException;
import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.api.global.security.JwtProvider;
import com.shacomiro.makeabook.api.global.security.dto.TokenResponse;
import com.shacomiro.makeabook.api.global.security.policy.AuthenticationScheme;
import com.shacomiro.makeabook.domain.rds.user.dto.SignInDto;
import com.shacomiro.makeabook.domain.rds.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRepository;
import com.shacomiro.makeabook.domain.redis.token.domain.RefreshToken;
import com.shacomiro.makeabook.domain.redis.token.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtProvider jwtProvider;

	public TokenResponse signIn(SignInDto signInDto) {
		User signInUser = userRepository.findByEmail(Email.byValue().value(signInDto.getEmail()).build())
				.orElseThrow(() -> new NotFoundException("User not found"));

		if (!passwordEncoder.matches(signInDto.getPassword(), signInUser.getPassword())) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		return new TokenResponse(
				HttpHeaders.AUTHORIZATION,
				AuthenticationScheme.BEARER.getType(),
				jwtProvider.createAccessToken(signInUser.getEmail().getValue()),
				saveRefreshToken(signInUser.getEmail().getValue()).getToken()
		);
	}

	public User signUp(SignUpDto signUpDto) {
		if (userRepository.findByEmail(Email.byValue().value(signUpDto.getEmail()).build()).isPresent()) {
			throw new ConflictException("Duplicate email");
		} else if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
			throw new ConflictException("Duplicate username");
		}

		return userRepository.save(
				User.bySignUpDto()
						.signUpDto(signUpDto)
						.roles(List.of(UserRole.USER))
						.build()
		);
	}

	public TokenResponse reissue(String token) {
		String refreshToken = token.replaceFirst(AuthenticationScheme.BEARER.getType(), "").trim();

		String email = jwtProvider.parseClaims(refreshToken).getSubject();

		return refreshTokenRepository.findById(email)
				.map(redisRefreshToken ->
						new TokenResponse(
								HttpHeaders.AUTHORIZATION,
								AuthenticationScheme.BEARER.getType(),
								jwtProvider.createAccessToken(redisRefreshToken.getId()),
								refreshToken
						)
				)
				.orElseThrow(() -> new JwtException("Refresh token not found"));
	}

	private RefreshToken saveRefreshToken(String emailValue) {
		return refreshTokenRepository.save(
				RefreshToken.byAllParameter()
						.id(emailValue)
						.token(jwtProvider.createRefreshToken(emailValue))
						.expiration(1000L * 60 * 60 * 2)
						.build()
		);
	}
}
