package com.shacomiro.nesonnechek.domain.rds.global.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.shacomiro.nesonnechek.domain.rds.global.validation.EnumCollectionValidator;

@Constraint(validatedBy = {EnumCollectionValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnumCollection {
	String message() default "Invalid value. This is not permitted.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> enumClass();
}
