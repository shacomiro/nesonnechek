package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.makeabook.api.global.security.service.SecurityService;
import com.shacomiro.makeabook.api.user.dto.SignInRequest;
import com.shacomiro.makeabook.api.user.dto.SignUpRequest;
import com.shacomiro.makeabook.api.user.dto.model.UserModel;
import com.shacomiro.makeabook.domain.rds.user.dto.SignInDto;
import com.shacomiro.makeabook.domain.rds.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.rds.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/sign")
@RequiredArgsConstructor
public class SignRestApi {
	private final SecurityService securityService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping(path = "signin")
	public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest) {
		return new ResponseEntity<>(
				EntityModel.of(
						securityService.signIn(new SignInDto(signInRequest.getEmail(), signInRequest.getPassword())),
						linkTo(methodOn(SignRestApi.class).reissue(null)).withRel("reissue"),
						docsLink()
				),
				HttpStatus.OK
		);
	}

	@PostMapping(path = "signup")
	public ResponseEntity<?> singUp(@RequestBody @Valid SignUpRequest signUpRequest) {
		User signUpUser = securityService.signUp(
				new SignUpDto(
						signUpRequest.getEmail(),
						passwordEncoder.encode(signUpRequest.getPassword()),
						signUpRequest.getUsername()
				)
		);

		UserModel userModel = UserModel.builder()
				.email(signUpUser.getEmail().getValue())
				.createdAt(signUpUser.getCreatedAt())
				.build();
		userModel.add(linkTo(methodOn(SignRestApi.class).signIn(null)).withRel("signin"));
		userModel.add(docsLink());

		return new ResponseEntity<>(userModel, HttpStatus.CREATED);
	}

	@PostMapping(path = "reissue")
	public ResponseEntity<?> reissue(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
		return new ResponseEntity<>(
				EntityModel.of(
						securityService.reissue(refreshToken),
						docsLink()
				),
				HttpStatus.OK
		);
	}
}
