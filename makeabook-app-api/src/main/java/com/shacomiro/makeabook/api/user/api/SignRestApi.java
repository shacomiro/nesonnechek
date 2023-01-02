package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.makeabook.api.user.dto.SignInRequest;
import com.shacomiro.makeabook.api.user.dto.SignUpRequest;
import com.shacomiro.makeabook.api.user.dto.model.UserModel;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.token.policy.AuthenticationScheme;
import com.shacomiro.makeabook.domain.token.service.JwtService;
import com.shacomiro.makeabook.domain.user.dto.SignUpDto;
import com.shacomiro.makeabook.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/sign")
@RequiredArgsConstructor
public class SignRestApi {
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping(path = "signin")
	public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest) {
		String emailValue = signInRequest.getEmail();
		String password = signInRequest.getPassword();

		userService.verifySignedInUser(emailValue, password);

		return new ResponseEntity<>(
				EntityModel.of(
						jwtService.issueJwt(emailValue, AuthenticationScheme.BEARER.getType()),
						Link.of(getCurrentApiServletMapping() + "/api/sign/signout").withRel("signout"),
						Link.of(getCurrentApiServletMapping() + "/api/sign/reissue").withRel("reissue"),
						docsLink()
				),
				HttpStatus.OK
		);
	}

	@PostMapping(path = "signup")
	public ResponseEntity<?> singUp(@RequestBody @Valid SignUpRequest signUpRequest) {
		User signUpUser = userService.signUpUser(
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
}
