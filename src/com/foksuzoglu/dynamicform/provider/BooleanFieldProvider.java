package com.foksuzoglu.dynamicform.provider;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class BooleanFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Class<?> type) {
		return type == boolean.class || type == Boolean.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {
		JCheckBox cb = new JCheckBox();
		cb.setName(meta.getField().getName());
		return cb;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {
		return ((JCheckBox) component).isSelected();
	}

	@Override
	public void setValue(JComponent component, Object value) {
		((JCheckBox) component).setSelected(Boolean.TRUE.equals(value));
	}

}