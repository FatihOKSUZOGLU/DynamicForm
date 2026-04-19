package com.foksuzoglu.dynamicform.core.detail;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ComponentValueAccessor {

	public static Object getValue(JComponent comp, Class<?> targetType) {
		try {

			// =====================================================
			// 🔥 1. LIST (EN BAŞTA OLMALI)
			// =====================================================
			if (comp instanceof ListPanel) {

				ListPanel listPanel = (ListPanel) comp;

				List<Object> list = new ArrayList<>();

				// ⚠️ generic type'ı dışardan vermen daha doğru olur
				Class<?> genericType = targetType;

				for (Component wrapper : listPanel.getComponents()) {

					if (!(wrapper instanceof JPanel)) {
						continue;
					}

					JPanel panelWrapper = (JPanel) wrapper;

					for (Component inner : panelWrapper.getComponents()) {

						if (inner instanceof JComponent) {

							Object val = getValue((JComponent) inner, genericType);

							if (val != null) {
								list.add(val);
							}
						}
					}
				}

				return list;
			}

			// ---------------- SIMPLE ----------------
			if (comp instanceof JTextField) {
				return ((JTextField) comp).getText();
			}

			if (comp instanceof JCheckBox) {
				return ((JCheckBox) comp).isSelected();
			}

			if (comp instanceof JComboBox) {
				return ((JComboBox<?>) comp).getSelectedItem();
			}

			// ---------------- PANEL → OBJECT ----------------
			if (targetType != null && !ReflectionUtil.isSimpleType(targetType)) {

				Object instance = targetType.getDeclaredConstructor().newInstance();

				Component[] children = comp.getComponents();

				for (Component c : children) {

					if (!(c instanceof JComponent)) {
						continue;
					}

					JComponent jc = (JComponent) c;

					String name = jc.getName();

					if (name == null) {
						// nested panel olabilir → recurse
						extractNested(instance, jc);
						continue;
					}

					try {

						Field field = targetType.getDeclaredField(name);
						field.setAccessible(true);

						Object value;

						if (ReflectionUtil.isSimpleType(field.getType())) {

							value = getValue(jc, field.getType());
							value = convert(field, value);

						} else {

							value = getValue(jc, field.getType());
						}

						field.set(instance, value);

					} catch (NoSuchFieldException ignore) {
						// component name eşleşmezse skip
					}
				}

				return instance;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void extractNested(Object parent, JComponent panel) {

		for (Component c : panel.getComponents()) {

			if (!(c instanceof JComponent)) {
				continue;
			}

			JComponent jc = (JComponent) c;

			String name = jc.getName();

			if (name == null) {
				extractNested(parent, jc);
				continue;
			}

			try {

				Field field = parent.getClass().getDeclaredField(name);
				field.setAccessible(true);

				Object value;

				if (ReflectionUtil.isSimpleType(field.getType())) {

					value = getValue(jc, field.getType());
					value = convert(field, value);

				} else {

					value = getValue(jc, field.getType());
				}

				field.set(parent, value);

			} catch (Exception ignore) {
			}
		}
	}

	private static Object getDefaultValue(Class<?> type) {

		if (!type.isPrimitive()) {
			return null;
		}

		if (type == boolean.class) {
			return false;
		}
		if (type == char.class) {
			return '\u0000';
		}
		if (type == byte.class) {
			return (byte) 0;
		}
		if (type == short.class) {
			return (short) 0;
		}
		if (type == int.class) {
			return 0;
		}
		if (type == long.class) {
			return 0L;
		}
		if (type == float.class) {
			return 0f;
		}
		if (type == double.class) {
			return 0d;
		}

		return null;
	}

	private static Object convert(Field field, Object raw) {

		Class<?> type = field.getType();

		// null kontrolü
		if (raw == null) {
			return getDefaultValue(type);
		}

		String value = raw.toString().trim();

		// boş string kontrolü
		if (value.isEmpty()) {
			return getDefaultValue(type);
		}

		// String
		if (type == String.class) {
			return value;
		}

		// Integer
		if (type == int.class || type == Integer.class) {
			return Integer.parseInt(value);
		}

		// Double
		if (type == double.class || type == Double.class) {
			return Double.parseDouble(value);
		}

		// Long
		if (type == long.class || type == Long.class) {
			return Long.parseLong(value);
		}

		// Float
		if (type == float.class || type == Float.class) {
			return Float.parseFloat(value);
		}

		// Short
		if (type == short.class || type == Short.class) {
			return Short.parseShort(value);
		}

		// Byte
		if (type == byte.class || type == Byte.class) {
			return Byte.parseByte(value);
		}

		// Boolean
		if (type == boolean.class || type == Boolean.class) {
			return Boolean.parseBoolean(value);
		}

		// Char
		if (type == char.class || type == Character.class) {
			return value.charAt(0);
		}

		// Enum
		if (type.isEnum()) {
			return Enum.valueOf((Class<Enum>) type, value);
		}

		throw new IllegalArgumentException("Unsupported type: " + type.getName());
	}

	public static void setValue(JComponent comp, Object value) {

		if (comp instanceof JTextField) {
			((JTextField) comp).setText(value != null ? value.toString() : "");
		} else if (comp instanceof JCheckBox) {
			((JCheckBox) comp).setSelected(Boolean.TRUE.equals(value));
		} else if (comp instanceof JComboBox) {
			((JComboBox<?>) comp).setSelectedItem(value);
		}
	}
}