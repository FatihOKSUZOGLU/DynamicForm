package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class EnumFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Class<?> type) {
		return type.isEnum();
	}

	@Override
	public JComponent create(FieldMeta meta) {
		Class<?> enumType = meta.getField().getType();
		JComboBox<Object> combo = new JComboBox<>(enumType.getEnumConstants());
		combo.setName(meta.getField().getName());
		return combo;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {
		return ((JComboBox<?>) component).getSelectedItem();
	}

	@Override
	public void setValue(JComponent component, Object value) {
		((JComboBox<?>) component).setSelectedItem(value);
	}

}