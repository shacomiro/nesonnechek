package com.shacomiro.makeabook.domain.rds.global.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shacomiro.makeabook.domain.rds.global.validation.annotation.ValidEnumCollection;

public class EnumCollectionValidator implements ConstraintValidator<ValidEnumCollection, Collection<? extends Enum<?>>> {
	private ValidEnumCollection annotation;

	@Override
	public void initialize(ValidEnumCollection constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Collection<? extends Enum<?>> values, ConstraintValidatorContext context) {
		boolean result = false;
		Object[] enumValues = this.annotation.enumClass().getEnumConstants();

		if (enumValues != null && values != null) {
			for (Object enumValue : enumValues) {
				for (Enum<?> value : values) {
					if (value == enumValue) {
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}
}
