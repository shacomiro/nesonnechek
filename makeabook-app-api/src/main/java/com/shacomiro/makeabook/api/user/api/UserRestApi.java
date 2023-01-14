package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.makeabook.api.global.assembers.UserResponseModelAssembler;
import com.shacomiro.makeabook.api.global.security.principal.UserPrincipal;
import com.shacomiro.makeabook.api.global.security.token.JwtAuthenticationToken;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;
import com.shacomiro.makeabook.domain.user.service.UserService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserRestApi {
	private final UserService userService;
	private final UserResponseModelAssembler userResponseModelAssembler;

	@GetMapping(path = "account")
	public ResponseEntity<?> getAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof JwtAuthenticationToken)) {
			throw new JwtException("User authentication not found.");
		}
		String emailValue = ((UserPrincipal)authentication.getPrincipal()).getEmail();

		return success(
				userService.findUserByEmail(emailValue)
						.orElseThrow(() -> new UserNotFoundException("Could not find current user.")),
				userResponseModelAssembler,
				HttpStatus.OK
		);
	}
}
