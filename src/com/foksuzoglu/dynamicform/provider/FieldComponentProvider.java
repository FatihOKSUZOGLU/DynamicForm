package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComponent;

public interface FieldComponentProvider {

	JComponent create(Class<?> clazz);

	Object getValue(JComponent component, Class<?> targetType);

	void setValue(JComponent component, Object value);

	boolean supports(Class<?> targetType);

}
