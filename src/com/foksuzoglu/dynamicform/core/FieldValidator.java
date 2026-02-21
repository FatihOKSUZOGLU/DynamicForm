package com.foksuzoglu.dynamicform.core;

import java.lang.reflect.Field;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.validation.ValidationException;

public final class FieldValidator {

	private FieldValidator() {
	}

	public static void validate(Field field, Object value, Detail rowMeta) {

		if (rowMeta.required()) {

			if (field.getType() == String.class) {
				if (value == null || value.toString().trim().isEmpty()) {
					throw new ValidationException(rowMeta.key() + " cannot be empty");
				}
			}

			if (field.getType().isPrimitive()) {
				if (value == null || value.toString().trim().isEmpty()) {
					throw new ValidationException(rowMeta.key() + " is required");
				}
			}

			if (field.getType().isEnum()) {
				if (value == null) {
					throw new ValidationException(rowMeta.key() + " must be selected");
				}
			}
		}
	}
}
