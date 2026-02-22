package com.foksuzoglu.dynamicform.model;

import java.lang.reflect.Field;
import java.util.List;

public class FieldPath {

	private final List<Field> path;

	public FieldPath(List<Field> path) {
		this.path = List.copyOf(path);
	}

	public List<Field> getPath() {
		return path;
	}

	public Field getLast() {
		return path.get(path.size() - 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FieldPath)) {
			return false;
		}
		FieldPath that = (FieldPath) o;
		return path.equals(that.path);
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}
}