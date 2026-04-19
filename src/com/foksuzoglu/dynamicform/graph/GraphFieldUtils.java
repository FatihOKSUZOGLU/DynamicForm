package com.foksuzoglu.dynamicform.graph;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GraphFieldUtils {

	public static List<Field> getGraphFields(Class<?> clazz) {

		List<Field> fields = new ArrayList<>();

		for (Field f : clazz.getDeclaredFields()) {

			if (f.isAnnotationPresent(Graph.class)) {

				f.setAccessible(true);
				fields.add(f);
			}
		}

		return fields;
	}
}