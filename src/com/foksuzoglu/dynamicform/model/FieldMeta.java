package com.foksuzoglu.dynamicform.model;

import java.lang.reflect.Field;

public class FieldMeta {

	private final Field field;
	private final String key;
	private final int row;
	private final int col;

	public FieldMeta(Field field, String label, int row, int col) {
		this.field = field;
		this.key = label;
		this.row = row;
		this.col = col;
		this.field.setAccessible(true);
	}

	public Field getField() {
		return field;
	}

	public String getKey() {
		return key;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
