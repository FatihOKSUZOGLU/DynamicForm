package com.foksuzoglu.dynamicform.provider;

import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class NumberFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Field field) {
		Class<?> type = field.getType();

		return type == int.class || type == Integer.class || type == long.class || type == Long.class
				|| type == double.class || type == Double.class || type == float.class || type == Float.class
				|| type == short.class || type == Short.class || type == byte.class || type == Byte.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {
		return new JTextField(10);
	}
}