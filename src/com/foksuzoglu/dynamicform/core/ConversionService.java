package com.foksuzoglu.dynamicform.core;

import java.lang.reflect.Field;

public final class ConversionService {

	private ConversionService() {
	}

	public static Object convert(Field field, Object componentValue) {

		Class<?> type = field.getType();

		if (type == String.class) {
			return componentValue;
		}

		if (type == int.class || type == Integer.class) {
			return Integer.parseInt(componentValue.toString());
		}

		if (type == double.class || type == Double.class) {
			return Double.parseDouble(componentValue.toString());
		}

		if (type == boolean.class || type == Boolean.class) {
			return componentValue;
		}

		if (type.isEnum()) {
			return componentValue;
		}

		throw new IllegalArgumentException("Unsupported type: " + type);
	}
}
