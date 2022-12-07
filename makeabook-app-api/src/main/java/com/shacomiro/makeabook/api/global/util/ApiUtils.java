package com.shacomiro.makeabook.api.global.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {

	public static <T, D extends RepresentationModel<D>> ResponseEntity<D> success(T response,
			RepresentationModelAssemblerSupport<T, D> representationModelAssembler, HttpStatus status) {
		return new ResponseEntity<>(representationModelAssembler.toModel(response).add(docsLink()), status);
	}

	public static <T, D extends RepresentationModel<D>> ResponseEntity<CollectionModel<D>> success(List<T> response,
			RepresentationModelAssemblerSupport<T, D> representationModelAssembler, HttpStatus status) {
		return new ResponseEntity<>(representationModelAssembler.toCollectionModel(response).add(docsLink()), status);
	}

	public static EntityModel<ApiError> error(Throwable throwable, HttpStatus status) {
		return EntityModel.of(new ApiError(throwable, status), docsLink());
	}

	public static EntityModel<ApiError> error(String message, HttpStatus status) {
		return EntityModel.of(new ApiError(message, status), docsLink());
	}

	public static String getCurrentApiRequest() {
		return ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
	}

	public static String getCurrentApiServletMapping() {
		return ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
	}

	private static List<Link> errorLinks() {
		return Arrays.asList(Link.of(getCurrentApiRequest()).withSelfRel(), docsLink());
	}

	private static Link docsLink() {
		return Link.of(getCurrentApiServletMapping() + "/api/static/docs/index.html").withRel("docs");
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
}
