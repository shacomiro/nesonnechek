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
import com.shacomiro.makeabook.domain.user.dto.SignInDto;
import com.shacomiro.makeabook.domain.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.entity.UserRole;
import com.shacomiro.makeabook.domain.rds.user.repository.UserRdsRepository;
import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRedisRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityService {
	private final UserRdsRepository userRdsRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenRedisRepository jwtTokenRedisRepository;
	private final JwtProvider jwtProvider;

	public TokenResponse signIn(SignInDto signInDto) {
		String emailValue = signInDto.getEmail();
		User signInUser = userRdsRepository.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new NotFoundException("User not found"));

		if (!passwordEncoder.matches(signInDto.getPassword(), signInUser.getPassword())) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		String accessToken = jwtProvider.createAccessToken(emailValue);
		String refreshToken = jwtProvider.createRefreshToken(emailValue);

		saveJwtToken(emailValue, refreshToken, 1000L * 60 * 60 * 2);

		return new TokenResponse(
				HttpHeaders.AUTHORIZATION,
				AuthenticationScheme.BEARER.getType(),
				accessToken,
				refreshToken
		);
	}

	public User signUp(SignUpDto signUpDto) {
		if (userRdsRepository.findByEmail(Email.byValue().value(signUpDto.getEmail()).build()).isPresent()) {
			throw new ConflictException("Duplicate email");
		} else if (userRdsRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
			throw new ConflictException("Duplicate username");
		}

		return userRdsRepository.save(
				User.bySignUpDto()
						.signUpDto(signUpDto)
						.roles(List.of(UserRole.USER))
						.build()
		);
	}

	private List<JwtToken> saveJwtTokens(String key, String accessToken, String refreshToken) {
		Claims accessClaims = jwtProvider.parseClaims(accessToken);
		Claims refreshClaims = jwtProvider.parseClaims(refreshToken);

		return (List<JwtToken>)jwtTokenRedisRepository.saveAll(
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

		return jwtTokenRedisRepository.save(
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
