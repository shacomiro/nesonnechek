package com.shacomiro.nesonnechek.api.global.assembers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.shacomiro.nesonnechek.api.user.api.AuthenticationRestApi;
import com.shacomiro.nesonnechek.api.user.api.UserRestApi;
import com.shacomiro.nesonnechek.api.user.dto.model.UserModel;
import com.shacomiro.nesonnechek.domain.rds.user.entity.User;

@Component
public class UserResponseModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

	public UserResponseModelAssembler() {
		super(UserRestApi.class, UserModel.class);
	}

	@Override
	public UserModel toModel(User entity) {
		UserModel userModel = instantiateModel(entity);

		userModel.add(linkTo(methodOn(UserRestApi.class).getAccount(null)).withSelfRel());
		if (entity.getLoginCount() == 0) {
			userModel.add(linkTo(methodOn(AuthenticationRestApi.class).signIn(null)).withRel("sign-in"));
		}

		userModel.setEmail(entity.getEmail().getValue());
		userModel.setUsername(entity.getUsername());
		userModel.setLoginCount(entity.getLoginCount());
		userModel.setLastLoginAt(entity.getLastLoginAt());
		userModel.setCreatedAt(entity.getCreatedAt());

		return userModel;
	}

	@Override
	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
		CollectionModel<UserModel> userCollectionModel = super.toCollectionModel(entities);

		return userCollectionModel;
	}
}
