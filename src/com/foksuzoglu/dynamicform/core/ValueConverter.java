package com.foksuzoglu.dynamicform.core;

import java.lang.reflect.Field;

public class ValueConverter {

	@SuppressWarnings("unchecked")
	public static Object convert(Field field, Object raw) {

		Class<?> type = field.getType();

		if (raw == null) {
			return ReflectionUtil.getDefaultValue(type);
		}

		String value = raw.toString().trim();

		if (value.isEmpty()) {
			return ReflectionUtil.getDefaultValue(type);
		}

		if (type == String.class) {
			return value;
		}
		if (type == int.class || type == Integer.class) {
			return Integer.parseInt(value);
		}
		if (type == double.class || type == Double.class) {
			return Double.parseDouble(value);
		}
		if (type == long.class || type == Long.class) {
			return Long.parseLong(value);
		}
		if (type == float.class || type == Float.class) {
			return Float.parseFloat(value);
		}
		if (type == short.class || type == Short.class) {
			return Short.parseShort(value);
		}
		if (type == byte.class || type == Byte.class) {
			return Byte.parseByte(value);
		}
		if (type == boolean.class || type == Boolean.class) {
			return Boolean.parseBoolean(value);
		}
		if (type == char.class || type == Character.class) {
			return value.charAt(0);
		}

		if (type.isEnum()) {
			return Enum.valueOf((Class<Enum>) type, value);
		}

		throw new IllegalArgumentException("Unsupported type: " + type.getName());
	}
}