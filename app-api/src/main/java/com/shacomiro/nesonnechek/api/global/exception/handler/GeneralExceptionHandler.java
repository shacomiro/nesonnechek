package com.shacomiro.nesonnechek.api.global.exception.handler;

import static com.shacomiro.nesonnechek.api.global.util.ApiUtils.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.shacomiro.nesonnechek.core.global.exception.FileIOException;
import com.shacomiro.nesonnechek.domain.global.exception.NotFoundException;
import com.shacomiro.nesonnechek.domain.rds.ebook.exception.EbookExpiredException;
import com.shacomiro.nesonnechek.domain.rds.user.exception.UserDeleteException;
import com.shacomiro.nesonnechek.domain.user.exception.UserConflictException;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

	private ResponseEntity<?> newResponse(Throwable throwable, HttpStatus status) {
		return newResponse(throwable.getMessage(), status);
	}

	private ResponseEntity<?> newResponse(String message, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE);
		return new ResponseEntity<>(error(message, status), headers, status);
	}

	@ExceptionHandler({
			NoHandlerFoundException.class,
			NotFoundException.class
	})
	public ResponseEntity<?> handleNotFoundException(Exception e) {
		return newResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({
			JwtException.class
	})
	public ResponseEntity<?> handleUnauthorizedException(Exception e) {
		return newResponse(e, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({
			IllegalArgumentException.class,
			IllegalStateException.class,
			MethodArgumentTypeMismatchException.class,
			MethodArgumentNotValidException.class,
			ConstraintViolationException.class,
			MultipartException.class,
			HttpRequestMethodNotSupportedException.class,
			EbookExpiredException.class,
			UserDeleteException.class
	})
	public ResponseEntity<?> handleBadRequestException(Exception e) {
		log.debug("Bad request exception occurred: {}", e.getMessage(), e);
		if (e instanceof MethodArgumentNotValidException) {
			return newResponse(
					((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
					HttpStatus.BAD_REQUEST
			);
		} else if (e instanceof ConstraintViolationException) {
			StringBuilder message = new StringBuilder();
			for (ConstraintViolation<?> violation : ((ConstraintViolationException)e).getConstraintViolations()) {
				message.append(violation.getMessage().concat(";"));
			}
			return newResponse(message.toString(), HttpStatus.BAD_REQUEST);
		} else if (e instanceof MethodArgumentTypeMismatchException) {
			return newResponse(
					((MethodArgumentTypeMismatchException)e).getValue() + " is not supported argument value",
					HttpStatus.BAD_REQUEST
			);
		}
		return newResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({
			UserConflictException.class
	})
	public ResponseEntity<?> handleConflictException(Exception e) {
		log.debug("Conflict exception occurred: {}", e.getMessage(), e);

		return newResponse(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler({
			Exception.class,
			RuntimeException.class,
			FileIOException.class
	})
	public ResponseEntity<?> handleException(Exception e) {
		log.error("Unexpected exception occurred: {}", e.getMessage(), e);
		return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
