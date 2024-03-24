package com.shacomiro.nesonnechek.api.user.dto.model;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "user")
@Relation(collectionRelation = "users", itemRelation = "user")
@JsonInclude(Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {
	private String email;
	private String username;
	private int loginCount;
	private LocalDateTime lastLoginAt;
	private LocalDateTime createdAt;
}
