package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class StringFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Class<?> targetType) {
		return targetType == String.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {
		JTextField tf = new JTextField(15);
		tf.setName(meta.getField().getName());
		return tf;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {
		return ((JTextField) component).getText();
	}

	@Override
	public void setValue(JComponent component, Object value) {
		((JTextField) component).setText(value != null ? value.toString() : "");
	}

}