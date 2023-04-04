package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shacomiro.makeabook.api.global.assembers.EbookResponseModelAssembler;
import com.shacomiro.makeabook.api.global.assembers.UserResponseModelAssembler;
import com.shacomiro.makeabook.api.global.security.principal.UserPrincipal;
import com.shacomiro.makeabook.api.user.dto.DeleteUserRequest;
import com.shacomiro.makeabook.api.user.dto.UpdateUserRequest;
import com.shacomiro.makeabook.cache.global.config.CacheKey;
import com.shacomiro.makeabook.domain.ebook.service.EbookService;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.user.dto.UpdateUserDto;
import com.shacomiro.makeabook.domain.user.service.UserService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserRestApi {
	private final UserService userService;
	private final EbookService ebookService;
	private final PasswordEncoder passwordEncoder;
	private final UserResponseModelAssembler userResponseModelAssembler;
	private final EbookResponseModelAssembler ebookResponseModelAssembler;

	@GetMapping(path = "account")
	public ResponseEntity<?> getAccount(@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal) {
		return success(
				userService.findUserByEmail(userPrincipal.getEmail()),
				userResponseModelAssembler,
				HttpStatus.OK
		);
	}

	@PutMapping(path = "account")
	@CacheEvict(value = CacheKey.SIGN_IN_USER, key = "#userPrincipal.email")
	public ResponseEntity<?> updateAccount(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal,
			@RequestBody UpdateUserRequest updateUserRequest) {
		return success(
				userService.updateUser(
						new UpdateUserDto(
								userPrincipal.getEmail(),
								updateUserRequest.getPassword() == null ?
										null : passwordEncoder.encode(updateUserRequest.getPassword()),
								updateUserRequest.getUsername()
						)
				),
				userResponseModelAssembler,
				HttpStatus.OK
		);
	}

	@DeleteMapping(path = "account")
	@CacheEvict(value = CacheKey.SIGN_IN_USER, key = "#userPrincipal.email")
	public ResponseEntity<?> deleteAccount(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal,
			@RequestBody @Valid DeleteUserRequest deleteUserRequest) {
		if (!passwordEncoder.matches(deleteUserRequest.getPassword(),
				userService.getSignInUserEncryptedPassword(userPrincipal.getEmail()))) {
			throw new IllegalArgumentException("Password is incorrect");
		}
		userService.deleteUser(userPrincipal.getEmail());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "account/ebooks")
	public ResponseEntity<?> getAccountEbooks(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal,
			Pageable pageable, PagedResourcesAssembler<Ebook> assembler) {

		return success(
				ebookService.findEbooksByUser(pageable, userService.findUserByEmail(userPrincipal.getEmail())),
				ebookResponseModelAssembler,
				assembler,
				HttpStatus.OK
		);
	}

	@DeleteMapping(path = "account/ebooks")
	public ResponseEntity<?> deleteAllAccountEbooks(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal,
			@RequestBody @Valid DeleteUserRequest deleteUserRequest) {
		if (!passwordEncoder.matches(deleteUserRequest.getPassword(),
				userService.getSignInUserEncryptedPassword(userPrincipal.getEmail()))) {
			throw new IllegalArgumentException("Password is incorrect");
		}

		ebookService.deleteAllEbooks(userService.findUserByEmail(userPrincipal.getEmail()));

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
