package com.foksuzoglu.dynamicform.provider;

import java.lang.reflect.Field;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class BooleanFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Field field) {
		return field.getType() == boolean.class || field.getType() == Boolean.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {
		return new JCheckBox();
	}
}