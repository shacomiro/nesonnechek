package com.shacomiro.nesonnechek.api.user.api;

import static com.shacomiro.nesonnechek.api.global.util.ApiUtils.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.nesonnechek.api.global.assembers.UserResponseModelAssembler;
import com.shacomiro.nesonnechek.api.global.security.dto.JwtDto;
import com.shacomiro.nesonnechek.api.global.security.service.JwtProvisionService;
import com.shacomiro.nesonnechek.api.user.dto.SignInRequest;
import com.shacomiro.nesonnechek.api.user.dto.SignUpRequest;
import com.shacomiro.nesonnechek.domain.rds.user.entity.User;
import com.shacomiro.nesonnechek.domain.user.dto.SignUpDto;
import com.shacomiro.nesonnechek.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthenticationRestApi {
	private final UserService userService;
	private final JwtProvisionService jwtProvisionService;
	private final PasswordEncoder passwordEncoder;
	private final UserResponseModelAssembler userResponseModelAssembler;

	@PostMapping(path = "sign-in")
	public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest signInRequest) {
		String emailValue = signInRequest.getEmail();
		String password = signInRequest.getPassword();

		if (!passwordEncoder.matches(password, userService.getSignInUserEncryptedPassword(emailValue))) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		JwtDto jwtDto = jwtProvisionService.issueJwt(emailValue);
		userService.updateLoginCount(emailValue);

		return new ResponseEntity<>(
				RepresentationModel.of(
						jwtDto,
						List.of(Link.of(getCurrentApiServletMapping() + "/auth/reissue").withRel("reissue"), docsLink())
				),
				HttpStatus.OK
		);
	}

	@PostMapping(path = "sign-up")
	public ResponseEntity<?> singUp(@RequestBody @Valid SignUpRequest signUpRequest) {
		User signUpUser = userService.signUpUser(
				new SignUpDto(
						signUpRequest.getEmail(),
						passwordEncoder.encode(signUpRequest.getPassword()),
						signUpRequest.getUsername()
				)
		);

		return success(signUpUser,
				userResponseModelAssembler,
				HttpStatus.CREATED);
	}
}
