package com.shacomiro.makeabook.api.global.error;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.util.List;

import org.springframework.hateoas.Link;
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

import com.shacomiro.makeabook.ebook.error.FileIOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

	private ResponseEntity<?> newResponse(Throwable throwable, HttpStatus status, List<Link> links) {
		return newResponse(throwable.getMessage(), status, links);
	}

	private ResponseEntity<?> newResponse(Throwable throwable, HttpStatus status) {
		return newResponse(throwable.getMessage(), status);
	}

	private ResponseEntity<?> newResponse(String message, HttpStatus status, List<Link> links) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/hal+json");
		return new ResponseEntity<>(error(message, status, links), headers, status);
	}

	private ResponseEntity<?> newResponse(String message, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		return new ResponseEntity<>(error(message, status), headers, status);
	}

	@ExceptionHandler({
			NoHandlerFoundException.class,
			NotFoundException.class
	})
	public ResponseEntity<?> handleNotFoundException(Exception e) {
		return newResponse(e, HttpStatus.NOT_FOUND, errorLinks());
	}

	@ExceptionHandler({
			IllegalArgumentException.class,
			IllegalStateException.class,
			MethodArgumentTypeMismatchException.class,
			MultipartException.class,
			HttpRequestMethodNotSupportedException.class,
			ExpiredException.class
	})
	public ResponseEntity<?> handleBadRequestException(Exception e) {
		log.debug("Bad request exception occurred: {}", e.getMessage(), e);
		if (e instanceof MethodArgumentNotValidException) {
			return newResponse(
					((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0).getDefaultMessage(),
					HttpStatus.BAD_REQUEST, errorLinks());
		} else if (e instanceof MethodArgumentTypeMismatchException) {
			return newResponse(
					((MethodArgumentTypeMismatchException)e).getValue() + " is not supported ebook type",
					HttpStatus.BAD_REQUEST, errorLinks()
			);
		}
		return newResponse(e, HttpStatus.BAD_REQUEST, errorLinks());
	}

	@ExceptionHandler({
			Exception.class,
			RuntimeException.class,
			FileIOException.class
	})
	public ResponseEntity<?> handleException(Exception e) {
		log.error("Unexpected exception occurred: {}", e.getMessage(), e);
		return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, errorLinks());
	}
}
