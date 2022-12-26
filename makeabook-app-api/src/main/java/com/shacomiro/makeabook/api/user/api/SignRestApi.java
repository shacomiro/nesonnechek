package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.api.user.dto.SignInRequest;
import com.shacomiro.makeabook.api.user.dto.SignUpRequest;
import com.shacomiro.makeabook.api.user.dto.model.UserModel;
import com.shacomiro.makeabook.domain.rds.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/sign")
@RequiredArgsConstructor
public class SignRestApi {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping(path = "signin")
	public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest) {
		User signInUser = userService.findByEmail(Email.byValue().value(signInRequest.getEmail()).build())
				.orElseThrow(() -> new NotFoundException("User not found"));

		if (!passwordEncoder.matches(signInRequest.getPassword(), signInUser.getPassword())) {
			throw new RuntimeException("Password is incorrect");
		}

		return null;
	}

	@PostMapping(path = "signup")
	public ResponseEntity<?> singUp(@RequestBody @Valid SignUpRequest signUpRequest) {
		if (userService.findByEmail(Email.byValue().value(signUpRequest.getEmail()).build()).isPresent()) {
			throw new RuntimeException("Duplicate email");
		} else if (userService.findByUsername(signUpRequest.getUsername()).isPresent()) {
			throw new RuntimeException("Duplicate username");
		}

		SignUpDto signUpDto = new SignUpDto(
				signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()),
				signUpRequest.getUsername()
		);

		User signUpUser = userService.save(signUpDto);

		UserModel userModel = UserModel.builder()
				.email(signUpUser.getEmail().getValue())
				.createdAt(signUpUser.getCreatedAt())
				.build();
		userModel.add(linkTo(methodOn(SignRestApi.class).signIn(null)).withRel("signin"));
		userModel.add(docsLink());

		return new ResponseEntity<>(userModel, HttpStatus.CREATED);
	}
}
