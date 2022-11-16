package com.shacomiro.makeabook.api.global.util;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {

	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response, null);
	}

	public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(throwable, status));
	}

	public static ApiResult<?> error(String message, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(message, status));
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

		private ApiResult(boolean success, T response, ApiError error) {
			this.success = success;
			this.response = response;
			this.error = error;
		}
	}
}
