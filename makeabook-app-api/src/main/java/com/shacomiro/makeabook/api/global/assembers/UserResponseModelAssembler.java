package com.shacomiro.makeabook.api.global.assembers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.shacomiro.makeabook.api.user.api.UserRestApi;
import com.shacomiro.makeabook.api.user.dto.model.UserModel;
import com.shacomiro.makeabook.domain.rds.user.entity.User;

@Component
public class UserResponseModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

	public UserResponseModelAssembler() {
		super(UserRestApi.class, UserModel.class);
	}

	@Override
	public UserModel toModel(User entity) {
		UserModel userModel = instantiateModel(entity);

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
