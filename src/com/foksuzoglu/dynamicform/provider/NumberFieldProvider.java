package com.foksuzoglu.dynamicform.provider;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.foksuzoglu.dynamicform.model.FieldMeta;
import com.foksuzoglu.dynamicform.model.NumericFilter;

public class NumberFieldProvider implements FieldComponentProvider {

	@Override
	public boolean supports(Class<?> type) {

		return type == int.class || type == Integer.class || type == double.class || type == Double.class
				|| type == long.class || type == Long.class || type == float.class || type == Float.class
				|| type == short.class || type == Short.class || type == byte.class || type == Byte.class;
	}

	@Override
	public JComponent create(FieldMeta meta) {

		JTextField tf = new JTextField(10);
		tf.setName(meta.getField().getName());

		// 🔥 input kontrolü
		((AbstractDocument) tf.getDocument())

				.setDocumentFilter(new NumericFilter(meta.getField().getType()));

		return tf;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {

		String text = ((JTextField) component).getText().trim();

		if (text.isEmpty()) {
			return getDefaultValue(targetType);
		}

		try {

			if (targetType == int.class || targetType == Integer.class) {
				return Integer.parseInt(text);
			}

			if (targetType == double.class || targetType == Double.class) {
				return Double.parseDouble(text);
			}

			if (targetType == long.class || targetType == Long.class) {
				return Long.parseLong(text);
			}

			if (targetType == float.class || targetType == Float.class) {
				return Float.parseFloat(text);
			}

			if (targetType == short.class || targetType == Short.class) {
				return Short.parseShort(text);
			}

			if (targetType == byte.class || targetType == Byte.class) {
				return Byte.parseByte(text);
			}

		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid number: " + text + " for type: " + targetType.getSimpleName());
		}

		throw new RuntimeException("Unsupported number type: " + targetType);
	}

	@Override
	public void setValue(JComponent component, Object value) {
		((JTextField) component).setText(value != null ? String.valueOf(value) : "");
	}

	private Object getDefaultValue(Class<?> type) {

		if (!type.isPrimitive()) {
			return null;
		}

		if (type == int.class) {
			return 0;
		}
		if (type == double.class) {
			return 0d;
		}
		if (type == long.class) {
			return 0L;
		}
		if (type == float.class) {
			return 0f;
		}
		if (type == short.class) {
			return (short) 0;
		}
		if (type == byte.class) {
			return (byte) 0;
		}

		return null;
	}

}