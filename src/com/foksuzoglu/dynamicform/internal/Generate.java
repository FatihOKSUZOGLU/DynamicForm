package com.foksuzoglu.dynamicform.internal;

import java.lang.reflect.Field;
import java.util.Random;

import com.foksuzoglu.dynamicform.annotation.Detail;

public class Generate {

	private final static Random random = new Random();

	private Object generateRandomValue(Class<?> type) {

		if (isSimpleType(type)) {
			return generateSimpleValue(type);
		}

		try {

			Object instance = type.getDeclaredConstructor().newInstance();

			for (Field field : type.getDeclaredFields()) {

				if (!field.isAnnotationPresent(Detail.class)) {
					continue;
				}

				field.setAccessible(true);

				Object value = generateRandomValue(field.getType());

				field.set(instance, value);
			}

			return instance;

		} catch (Exception e) {
			throw new RuntimeException("Cannot instantiate: " + type.getName(), e);
		}
	}

	public static <T> T generateObject(Class<T> type) throws Exception {

		if (isSimpleType(type)) {
			return type.cast(generateSimpleValue(type));
		}

		T instance = type.getDeclaredConstructor().newInstance();

		for (Field field : type.getDeclaredFields()) {

			field.setAccessible(true);

			Class<?> fieldType = field.getType();
			Object value;

			if (isSimpleType(fieldType)) {
				if (!field.isAnnotationPresent(Detail.class)) {
					continue;
				}

				value = generateSimpleValue(fieldType);
			} else {
				value = generateObject(fieldType);
			}

			field.set(instance, value);
		}

		return instance;
	}

	private static Object generateSimpleValue(Class<?> type) {

		if (type == String.class) {
			return randomString();
		}

		if (type == int.class || type == Integer.class) {
			return random.nextInt(150);
		}

		if (type == long.class || type == Long.class) {
			return Math.abs(random.nextLong()) % 10_000;
		}

		if (type == double.class || type == Double.class) {
			return random.nextDouble() * 1000;
		}

		if (type == float.class || type == Float.class) {
			return random.nextFloat() * 1000;
		}

		if (type == boolean.class || type == Boolean.class) {
			return random.nextBoolean();
		}

		if (type == short.class || type == Short.class) {
			return (short) random.nextInt(Short.MAX_VALUE);
		}

		if (type == byte.class || type == Byte.class) {
			return (byte) random.nextInt(Byte.MAX_VALUE);
		}

		if (type == char.class || type == Character.class) {
			return (char) (random.nextInt(26) + 'A');
		}

		if (type.isEnum()) {
			Object[] constants = type.getEnumConstants();
			return constants[random.nextInt(constants.length)];
		}

		return null;
	}

	private static String randomString() {
		int length = 5 + random.nextInt(6); // 5-10 karakter
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}

		return sb.toString();
	}

	private static boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

}
