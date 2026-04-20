package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public interface FieldComponentProvider {

	JComponent create(FieldMeta meta);

	Object getValue(JComponent component, Class<?> targetType);

	void setValue(JComponent component, Object value);

	boolean supports(Class<?> targetType);

}
