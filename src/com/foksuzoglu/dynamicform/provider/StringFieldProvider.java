package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.api.LanguageProvider;

public class StringFieldProvider extends MainProvider implements FieldComponentProvider {

	public StringFieldProvider(LanguageProvider languageProvider) {
		super(languageProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> targetType) {
		return targetType == String.class;
	}

	@Override
	public JComponent create(Class<?> clazz) {
		JTextField tf = new JTextField(15);
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