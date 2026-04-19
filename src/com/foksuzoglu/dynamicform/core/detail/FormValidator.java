package com.foksuzoglu.dynamicform.core.detail;

import java.lang.reflect.Field;
import java.util.Map;

import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;
import com.foksuzoglu.dynamicform.validation.ValidationResult;

public class FormValidator {

	private final ValidationMessageResolver messageResolver;
	private final LanguageProvider languageProvider;

	public FormValidator(ValidationMessageResolver messageResolver, LanguageProvider languageProvider) {
		this.messageResolver = messageResolver;
		this.languageProvider = languageProvider;
	}

	public ValidationResult validate(Map<Field, JComponent> fieldMap) {

		ValidationResult result = new ValidationResult();

		for (Map.Entry<Field, JComponent> entry : fieldMap.entrySet()) {

			Field field = entry.getKey();
			JComponent comp = entry.getValue();

			Detail meta = field.getAnnotation(Detail.class);
			if (meta == null) {
				continue;
			}

			Object raw = ComponentValueAccessor.getValue(comp, field.getType());

			String label = resolveLabel(meta.key());

			// =====================================================
			// REQUIRED
			// =====================================================
			if (meta.required()) {

				boolean empty = raw == null || (raw instanceof String && ((String) raw).trim().isEmpty());

				if (empty) {
					result.addError(meta.key(), resolve("validation.required", label));
					continue;
				}
			}

			// =====================================================
			// NUMBER VALIDATION
			// =====================================================
			if (raw != null && !raw.toString().trim().isEmpty()) {

				if (isNumeric(field.getType())) {

					if (!isValidNumber(field.getType(), raw.toString())) {

						result.addError(meta.key(), resolve("validation.number.invalid", label));
					}
				}
			}
		}

		return result;
	}

	private String resolve(String key, String fieldLabel) {

		return messageResolver.resolve(key, Map.of("field", fieldLabel));
	}

	private String resolveLabel(String key) {
		return languageProvider != null ? languageProvider.getText(key) : key;
	}

	private boolean isNumeric(Class<?> type) {

		return type == int.class || type == Integer.class || type == long.class || type == Long.class
				|| type == double.class || type == Double.class || type == float.class || type == Float.class
				|| type == short.class || type == Short.class || type == byte.class || type == Byte.class;
	}

	private boolean isValidNumber(Class<?> type, String value) {

		try {

			if (type == int.class || type == Integer.class) {
				Integer.parseInt(value);
			} else if (type == long.class || type == Long.class) {
				Long.parseLong(value);
			} else if (type == double.class || type == Double.class) {
				Double.parseDouble(value);
			} else if (type == float.class || type == Float.class) {
				Float.parseFloat(value);
			} else if (type == short.class || type == Short.class) {
				Short.parseShort(value);
			} else if (type == byte.class || type == Byte.class) {
				Byte.parseByte(value);
			}

			return true;

		} catch (NumberFormatException e) {
			return false;
		}
	}
}