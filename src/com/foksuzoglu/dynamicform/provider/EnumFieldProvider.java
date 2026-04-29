package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.api.LanguageProvider;

public class EnumFieldProvider extends MainProvider implements FieldComponentProvider {

	public EnumFieldProvider(LanguageProvider languageProvider) {
		super(languageProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> type) {
		return type.isEnum();
	}

	@Override
	public JComponent create(Class<?> clazz) {
		JComboBox<Object> combo = new JComboBox<>(clazz.getEnumConstants());
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