package com.shacomiro.nesonnechek.domain.rds.user.entity;

import static java.util.regex.Pattern.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
	@NotBlank(message = "Email value must be provided")
	@Size(min = 4, max = 50, message = "Email value length must be between 4 and 50 characters")
	@javax.validation.constraints.Email(message = "Email value format must be like [user@example.com]")
	private String value;

	@Builder(builderClassName = "ByValue", builderMethodName = "byValue")
	public Email(String value) {
		this.value = value;
	}

	private static boolean verifyValue(String value) {
		return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", value);
	}
}
