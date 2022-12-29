package com.shacomiro.makeabook.api.global.security.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.global.error.ConflictException;
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
import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenRepository jwtTokenRepository;
	private final JwtProvider jwtProvider;

	public TokenResponse signIn(SignInDto signInDto) {
		String emailValue = signInDto.getEmail();
		User signInUser = userRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new NotFoundException("User not found"));

		if (!passwordEncoder.matches(signInDto.getPassword(), signInUser.getPassword())) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		List<JwtToken> storedTokens = jwtTokenRepository.findAllByKey(emailValue);

		if (!storedTokens.isEmpty()) {
			jwtTokenRepository.deleteAll(storedTokens);
		}

		List<JwtToken> jwtTokens = saveJwtTokens(
				emailValue,
				jwtProvider.createAccessToken(emailValue),
				jwtProvider.createRefreshToken(emailValue)
		);

		return new TokenResponse(
				HttpHeaders.AUTHORIZATION,
				AuthenticationScheme.BEARER.getType(),
				jwtTokens.get(0).getToken(),
				jwtTokens.get(1).getToken()
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

	private List<JwtToken> saveJwtTokens(String key, String accessToken, String refreshToken) {
		Claims accessClaims = jwtProvider.parseClaims(accessToken);
		Claims refreshClaims = jwtProvider.parseClaims(refreshToken);

		return (List<JwtToken>)jwtTokenRepository.saveAll(
				List.of(
						JwtToken.byAllParameter()
								.id(accessClaims.getId())
								.key(key)
								.type(accessClaims.get("typ", String.class))
								.token(accessToken)
								.expiration(1000L * 60 * 30)
								.build(),
						JwtToken.byAllParameter()
								.id(refreshClaims.getId())
								.key(key)
								.type(refreshClaims.get("typ", String.class))
								.token(refreshToken)
								.expiration(1000L * 60 * 60 * 2)
								.build()
				)
		);
	}

	private JwtToken saveJwtToken(String key, String token, long expiration) {
		Claims claims = jwtProvider.parseClaims(token);

		return jwtTokenRepository.save(
				JwtToken.byAllParameter()
						.id(claims.getId())
						.key(key)
						.type(claims.get("typ", String.class))
						.token(token)
						.expiration(expiration)
						.build()
		);
	}
}
