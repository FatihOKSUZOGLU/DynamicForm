package com.foksuzoglu.dynamicform.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class AnnotationFieldReader {

	private static final ConcurrentMap<String, List<Field>> CACHE = new ConcurrentHashMap<>();

	public static List<Field> getFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {

		String cacheKey = clazz.getName() + "_" + annotationClass.getName();

		return CACHE.computeIfAbsent(cacheKey, k -> analyze(clazz, annotationClass));
	}

	private static List<Field> analyze(Class<?> clazz, Class<? extends Annotation> annotationClass) {

		List<Field> result = new ArrayList<>();
		analyzeRecursive(clazz, 0, result, annotationClass);
		return result;
	}

	private static int analyzeRecursive(Class<?> clazz, int startRow, List<Field> result,
			Class<? extends Annotation> annotationClass) {

		int currentRow = startRow;

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			field.setAccessible(true);

			if (isSimpleType(field.getType())) {

				if (!field.isAnnotationPresent(annotationClass)) {
					continue;
				}

				result.add(field);

				currentRow++;

			} else {
				currentRow = analyzeRecursive(field.getType(), currentRow, result, annotationClass);
			}
		}

		return currentRow;
	}

	private static boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type.isEnum();
	}
}