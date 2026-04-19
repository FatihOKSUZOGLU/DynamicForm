package com.foksuzoglu.dynamicform.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.model.FieldMeta;

public final class AnnotationFieldMetaCache {

	private static final ConcurrentMap<String, List<FieldMeta>> CACHE = new ConcurrentHashMap<>();

	private AnnotationFieldMetaCache() {
	}

	public static List<FieldMeta> getMetadata(Class<?> clazz, Class<? extends Annotation> annotationClass) {

		String cacheKey = clazz.getName() + "_" + annotationClass.getName();

		return CACHE.computeIfAbsent(cacheKey, k -> analyze(clazz, annotationClass));
	}

	private static List<FieldMeta> analyze(Class<?> clazz, Class<? extends Annotation> annotationClass) {

		List<FieldMeta> result = new ArrayList<>();
		analyzeRecursive(clazz, 0, result, annotationClass);
		return result;
	}

	private static int analyzeRecursive(Class<?> clazz, int startRow, List<FieldMeta> result,
			Class<? extends Annotation> annotationClass) {

		int currentRow = startRow;

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			field.setAccessible(true);

			if (isListType(field)) {

				if (!field.isAnnotationPresent(annotationClass)) {
					continue;
				}

				Detail detail = field.getAnnotation(Detail.class);

				result.add(new FieldMeta(field, detail.key(), currentRow, detail.col()));

				currentRow++; // list tek satır kaplar (UI expand eder)

				continue;
			}

			if (isSimpleType(field.getType())) {

				if (!field.isAnnotationPresent(annotationClass)) {
					continue;
				}
				Detail row = field.getAnnotation(Detail.class);
				result.add(new FieldMeta(field, row.key(), currentRow, row.col()));

				currentRow++;

			} else {
				currentRow = analyzeRecursive(field.getType(), currentRow, result, annotationClass);
			}
		}

		return currentRow;

	}

	private static String extractStringValue(Annotation annotation, String... possibleMethods) {

		for (String methodName : possibleMethods) {
			try {
				Method method = annotation.annotationType().getMethod(methodName);

				Object value = method.invoke(annotation);

				if (value instanceof String) {
					return (String) value;
				}

			} catch (Exception ignored) {
			}
		}

		return null;
	}

	private static int extractIntValue(Annotation annotation, String methodName) {

		try {
			Method method = annotation.annotationType().getMethod(methodName);

			Object value = method.invoke(annotation);

			if (value instanceof Integer) {
				return (Integer) value;
			}

		} catch (Exception ignored) {
		}

		return 0;
	}

	private static boolean isListType(Field field) {
		return List.class.isAssignableFrom(field.getType());
	}

	private static boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type.isEnum();
	}
}