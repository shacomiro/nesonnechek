package com.shacomiro.makeabook.api.global.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {
	public static <T> EntityModel<ApiResult<T>> success(T response, List<Link> links) {
		return EntityModel.of(new ApiResult<>(true, response, null), links);
	}

	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response, null);
	}

	public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(throwable, status));
	}

	public static EntityModel<ApiResult<?>> error(String message, HttpStatus status, List<Link> links) {
		return EntityModel.of(new ApiResult<>(false, null, new ApiError(message, status)), links);
	}

	public static ApiResult<?> error(String message, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(message, status));
	}

	public static String getCurrentApiRequest() {
		return ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
	}

	public static String getCurrentApiServletMapping() {
		return ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
	}

	public static List<Link> errorLinks() {
		return Arrays.asList(
				Link.of(getCurrentApiRequest()).withSelfRel(),
				Link.of(getCurrentApiServletMapping() + "/api/static/docs/index.html").withRel("docs"));
	}

	@ToString
	@Getter
	public static class ApiError {
		private final String message;
		private final int status;

		ApiError(Throwable throwable, HttpStatus status) {
			this(throwable.getMessage(), status);
		}

		ApiError(String message, HttpStatus status) {
			this.message = message;
			this.status = status.value();
		}
	}

	@ToString
	@Getter
	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final ApiError error;

		ApiResult(boolean success, T response, ApiError error) {
			this.success = success;
			this.response = response;
			this.error = error;
		}
	}
}
