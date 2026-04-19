package com.foksuzoglu.dynamicform.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.foksuzoglu.dynamicform.annotation.Detail;

public class RandomObjectGenerator {

	private final Random random = new Random();

	private static final int LIST_SIZE = 10;
	private static final int ARRAY_SIZE = 10;

	// ---------------- ENTRY ----------------
	public <T> T generate(Class<T> type) {
		try {
			return (T) generateInternal(type, new HashSet<>());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ---------------- CORE ----------------
	private Object generateInternal(Class<?> type, Set<Class<?>> visited) throws Exception {

		if (isSimple(type)) {
			return generateSimple(type);
		}

		if (type.isArray()) {
			return generateArray(type.getComponentType(), visited);
		}

		if (List.class.isAssignableFrom(type)) {
			return generateList(Object.class, visited); // generic unknown fallback
		}

		if (visited.contains(type)) {
			return null; // recursion guard
		}

		visited.add(type);

		Object instance = type.getDeclaredConstructor().newInstance();

		for (Field field : type.getDeclaredFields()) {

			if (!field.isAnnotationPresent(Detail.class)) {
				continue;
			}

			field.setAccessible(true);

			Class<?> fieldType = field.getType();
			Object value;

			if (List.class.isAssignableFrom(fieldType)) {
				value = generateList(getGenericType(field), visited);
			} else if (fieldType.isArray()) {
				value = generateArray(fieldType.getComponentType(), visited);
			} else if (isSimple(fieldType)) {
				value = generateSimple(fieldType);
			} else {
				value = generateInternal(fieldType, visited);
			}

			field.set(instance, value);
		}

		visited.remove(type);

		return instance;
	}

	// ---------------- LIST ----------------
	private Object generateList(Class<?> genericType, Set<Class<?>> visited) throws Exception {

		List<Object> list = new ArrayList<>();

		for (int i = 0; i < LIST_SIZE; i++) {

			if (isSimple(genericType)) {
				list.add(generateSimple(genericType));
			} else {
				list.add(generateInternal(genericType, visited));
			}
		}

		return list;
	}

	// ---------------- ARRAY ----------------
	private Object generateArray(Class<?> componentType, Set<Class<?>> visited) throws Exception {

		Object array = Array.newInstance(componentType, ARRAY_SIZE);

		for (int i = 0; i < ARRAY_SIZE; i++) {

			Object value;

			if (isSimple(componentType)) {
				value = generateSimple(componentType);
			} else {
				value = generateInternal(componentType, visited);
			}

			Array.set(array, i, value);
		}

		return array;
	}

	// ---------------- SIMPLE TYPES ----------------
	private Object generateSimple(Class<?> type) {

		if (type == String.class) {
			return randomString();
		}

		if (type == int.class || type == Integer.class) {
			return random.nextInt(1000);
		}

		if (type == long.class || type == Long.class) {
			return random.nextInt(1000);
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
			return (short) random.nextInt(1000);
		}

		if (type == byte.class || type == Byte.class) {
			return (byte) random.nextInt(100);
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

	// ---------------- STRING ----------------
	private String randomString() {
		int len = 2 + random.nextInt(9); // 0–10
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}

		return sb.toString();
	}

	// ---------------- TYPE CHECK ----------------
	private boolean isSimple(Class<?> type) {
		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	// ---------------- GENERIC ----------------
	private Class<?> getGenericType(Field field) {

		if (!(field.getGenericType() instanceof ParameterizedType)) {
			return Object.class;
		}

		ParameterizedType pt = (ParameterizedType) field.getGenericType();

		Type t = pt.getActualTypeArguments()[0];

		if (t instanceof Class<?>) {
			return (Class<?>) t;
		}

		return Object.class;
	}
}