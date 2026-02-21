package com.foksuzoglu.dynamicform.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.model.FieldMeta;

public class FormMetadataCache {

	private static final ConcurrentMap<Class<?>, List<FieldMeta>> CACHE = new ConcurrentHashMap<>();

	public static List<FieldMeta> getMetadata(Class<?> clazz) {

		return CACHE.computeIfAbsent(clazz, FormMetadataCache::analyzeClass);
	}

	private static List<FieldMeta> analyzeClass(Class<?> clazz) {
		List<FieldMeta> result = new ArrayList<>();
		analyzeClass(clazz, 0, result);
		return result;
	}

	private static int analyzeClass(Class<?> clazz, int startRow, List<FieldMeta> result) {

		int currentRow = startRow;

		for (Field field : clazz.getDeclaredFields()) {

			if (!field.isAnnotationPresent(Detail.class)) {
				continue;
			}

			Detail row = field.getAnnotation(Detail.class);

			if (isSimpleType(field.getType())) {

				result.add(new FieldMeta(field, row.key(), currentRow, row.col()));

				currentRow++;

			} else {
				// result.add(new FieldMeta(field, row.key(), currentRow, row.col()));
				// INNER BLOCK
				currentRow = currentRow + 1;
				currentRow = analyzeClass(field.getType(), currentRow, result);
			}
		}

		return currentRow;
	}

	private static boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type.isEnum();
	}

}
