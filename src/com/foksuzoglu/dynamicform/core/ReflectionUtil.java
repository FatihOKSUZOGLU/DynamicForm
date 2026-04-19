package com.foksuzoglu.dynamicform.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ReflectionUtil {

	public static boolean isSimpleType(Class<?> type) {
		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	public static boolean isListType(Class<?> type) {
		return List.class.isAssignableFrom(type);
	}

	public static Class<?> getListGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<?>) type.getActualTypeArguments()[0];
	}

	public static Object getDefaultValue(Class<?> type) {
		if (!type.isPrimitive()) {
			return null;
		}

		if (type == boolean.class) {
			return false;
		}
		if (type == char.class) {
			return '\0';
		}
		if (type == byte.class) {
			return (byte) 0;
		}
		if (type == short.class) {
			return (short) 0;
		}
		if (type == int.class) {
			return 0;
		}
		if (type == long.class) {
			return 0L;
		}
		if (type == float.class) {
			return 0f;
		}
		if (type == double.class) {
			return 0d;
		}

		return null;
	}
}