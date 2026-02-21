package com.foksuzoglu.dynamicform.provider;

import java.lang.reflect.Field;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class EnumFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Field field) {
		return field.getType().isEnum();
	}

	@Override
	public JComponent create(FieldMeta meta) {
		Object[] constants = meta.getField().getType().getEnumConstants();
		return new JComboBox<>(constants);
	}
}