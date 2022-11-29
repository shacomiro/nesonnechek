package com.shacomiro.makeabook.domain.rds.user.entity;

import static java.util.regex.Pattern.*;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
	private String value;

	@Builder(builderClassName = "ByAllArguments", builderMethodName = "byAllArguments")
	public Email(String value) {
		this.value = value;
	}

	private static boolean verifyValue(String value) {
		return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", value);
	}
}
