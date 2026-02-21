package com.foksuzoglu.dynamicform.provider;

import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class StringFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Field field) {
		return field.getType() == String.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {
		return new JTextField(15);
	}
}