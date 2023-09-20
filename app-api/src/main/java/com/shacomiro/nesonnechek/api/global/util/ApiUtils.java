package com.shacomiro.nesonnechek.api.global.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtils {

	public static <T, D extends RepresentationModel<D>> ResponseEntity<D> success(
			T response,
			RepresentationModelAssemblerSupport<T, D> representationModelAssembler,
			HttpStatus status) {
		return new ResponseEntity<>(representationModelAssembler.toModel(response).add(docsLink()), status);
	}

	public static <T, D extends RepresentationModel<D>> ResponseEntity<CollectionModel<D>> success(
			List<T> response,
			RepresentationModelAssemblerSupport<T, D> representationModelAssembler,
			HttpStatus status) {
		return new ResponseEntity<>(representationModelAssembler.toCollectionModel(response).add(docsLink()), status);
	}

	public static <T, D extends RepresentationModel<D>> ResponseEntity<CollectionModel<D>> success(
			Page<T> response,
			RepresentationModelAssemblerSupport<T, D> representationModelAssembler,
			PagedResourcesAssembler<T> pagedResourcesAssembler,
			HttpStatus status) {
		return new ResponseEntity<>(pagedResourcesAssembler.toModel(response, representationModelAssembler).add(docsLink()),
				status);
	}

	public static ApiError error(Throwable throwable, HttpStatus status) {
		return ApiError.byThrowable()
				.throwable(throwable)
				.status(status)
				.build()
				.add(docsLink());
	}

	public static ApiError error(String message, HttpStatus status) {
		return ApiError.byMessage()
				.message(message)
				.status(status)
				.build()
				.add(docsLink());
	}

	public static String getCurrentApiRequest() {
		return ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
	}

	public static String getCurrentApiServletMapping() {
		return ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
	}

	public static List<Link> errorLinks() {
		return Arrays.asList(Link.of(getCurrentApiRequest()).withSelfRel(), docsLink());
	}

	public static Link docsLink() {
		return Link.of(getCurrentApiServletMapping() + "/api/docs/index.html").withRel("docs");
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	@JsonRootName(value = "error")
	@Relation(collectionRelation = "errors", itemRelation = "error")
	public static class ApiError extends RepresentationModel<ApiError> {
		private String message;
		private int status;

		@Builder(builderClassName = "ByThrowable", builderMethodName = "byThrowable")
		ApiError(Throwable throwable, HttpStatus status) {
			this(throwable.getMessage(), status);
		}

		@Builder(builderClassName = "ByMessage", builderMethodName = "byMessage")
		ApiError(String message, HttpStatus status) {
			this(message, status.value());
		}
	}
}
