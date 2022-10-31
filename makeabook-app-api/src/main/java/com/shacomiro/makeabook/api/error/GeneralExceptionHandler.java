package com.shacomiro.makeabook.api.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.shacomiro.makeabook.ebook.error.FileIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

	private ResponseEntity<?> newResponse(Throwable throwable, HttpStatus status) {
		return newResponse(throwable.getMessage(), status);
	}

	private ResponseEntity<?> newResponse(String message, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		return new ResponseEntity<>(message, headers, status);
	}

	@ExceptionHandler({
			NoHandlerFoundException.class,
			NotFoundException.class
	})
	public ResponseEntity<?> handleNotFoundException(Exception e) {
		return newResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({
			IllegalArgumentException.class,
			IllegalStateException.class,
			MultipartException.class
	})
	public ResponseEntity<?> handleBadRequestException(Exception e) {
		return newResponse(e, HttpStatus.BAD_REQUEST);
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
