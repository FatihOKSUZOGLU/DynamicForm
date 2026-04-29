package com.foksuzoglu.dynamicform.provider;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.api.LanguageProvider;

public class BooleanFieldProvider extends MainProvider implements FieldComponentProvider {

	public BooleanFieldProvider(LanguageProvider languageProvider) {
		super(languageProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> type) {
		return type == boolean.class || type == Boolean.class;
	}

	@Override
	public JComponent create(Class<?> clazz) {
		JCheckBox cb = new JCheckBox();
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