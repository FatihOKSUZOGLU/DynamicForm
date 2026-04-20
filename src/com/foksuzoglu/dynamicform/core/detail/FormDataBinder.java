package com.foksuzoglu.dynamicform.core.detail;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.model.FieldMeta;

public class FormDataBinder {

	public static void bind(Object obj, Map<Field, JComponent> fieldMap, PanelBuilderUtil builderUtil) {

		if (obj == null) {
			return;
		}

		for (Field field : obj.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			try {
				Object value = field.get(obj);

				// 🔥 SIMPLE FIELD UI BIND
				JComponent comp = fieldMap.get(field);

				if (comp != null && !(value instanceof List)) {
					setComponentValue(comp, value);
				}

				// =====================================================
				// 🔥 LIST HANDLING
				// =====================================================
				if (value instanceof List) {

					List<?> list = (List<?>) value;

					JComponent listComp = fieldMap.get(field);

					if (listComp instanceof ListPanel) {

						ListPanel listPanel = (ListPanel) listComp;
						listPanel.clear();

						Class<?> genericType = ReflectionUtil.getListGenericType(field);

						for (Object item : list) {

							RowPanel itemPanel = builderUtil.createListItemPanel(metaFromField(field));

							// 🔥 SIMPLE TYPE
							if (ReflectionUtil.isSimpleType(genericType)) {

								JTextField tf = findTextField(itemPanel);

								if (tf != null && item != null) {
									tf.setText(item.toString());
								}

							}
							// 🔥 OBJECT TYPE (ASIL FIX BURASI)
							else if (item != null) {

								bindObjectToPanel(item, itemPanel, fieldMap, builderUtil);
							}

							JPanel wrapper = new JPanel();
							wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

							wrapper.add(itemPanel);
							wrapper.add(Box.createVerticalStrut(5));
							wrapper.add(new JSeparator());

							listPanel.add(wrapper);
						}

						listPanel.revalidate();
						listPanel.repaint();
					}

					continue;
				}

				// 🔥 NESTED OBJECT
				if (value != null && !isSimple(field.getType())) {
					bind(value, fieldMap, builderUtil);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void bindObjectToPanel(Object obj, JComponent panel, Map<Field, JComponent> fieldMap,
			PanelBuilderUtil builderUtil) {

		for (Field f : obj.getClass().getDeclaredFields()) {

			f.setAccessible(true);

			try {
				Object val = f.get(obj);
				JComponent comp = findComponentByFieldName(panel, f.getName());

				if (comp != null) {

					if (ReflectionUtil.isSimpleType(f.getType())) {
						setComponentValue(comp, val);
					} else {
						bindObjectToPanel(val, comp, fieldMap, builderUtil); // recursive
					}
				} else {
					bind(obj, fieldMap, builderUtil);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static JComponent findComponentByFieldName(JComponent parent, String fieldName) {

		for (Component c : parent.getComponents()) {

			if (c instanceof JComponent) {

				JComponent jc = (JComponent) c;

				// 🔥 NAME CHECK (önemli!)
				if (fieldName.equals(jc.getName())) {
					return jc;
				}

				JComponent child = findComponentByFieldName(jc, fieldName);
				if (child != null) {
					return child;
				}
			}
		}

		return null;
	}

	private static void setComponentValue(JComponent comp, Object value) {

		if (comp instanceof JTextField) {
			((JTextField) comp).setText(value != null ? value.toString() : "");
		}

		else if (comp instanceof JCheckBox) {
			((JCheckBox) comp).setSelected(Boolean.TRUE.equals(value));
		}

		else if (comp instanceof JComboBox) {
			((JComboBox<?>) comp).setSelectedItem(value);
		}
	}

	private static JTextField findTextField(JComponent comp) {

		if (comp instanceof JTextField) {
			return (JTextField) comp;
		}

		Component[] children = comp.getComponents();

		for (Component c : children) {
			if (c instanceof JComponent) {
				JTextField tf = findTextField((JComponent) c);
				if (tf != null) {
					return tf;
				}
			}
		}

		return null;
	}

	// =========================
	// ROW OBJECT BIND
	// =========================
	private static void bindObjectToRow(Object obj, JComponent rowPanel) {

		if (obj == null) {
			return;
		}

		Class<?> clazz = obj.getClass();

		for (Field field : clazz.getDeclaredFields()) {

			field.setAccessible(true);

			try {
				Object value = field.get(obj);

				JComponent comp = findByName(rowPanel, field.getName());

				if (comp == null) {
					continue;
				}

				if (isSimple(field.getType())) {
					ComponentValueAccessor.setValue(comp, value, field);
				} else {
					bindObjectToRow(value, comp);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// =========================
	// FIND COMPONENT
	// =========================
	private static JComponent findByName(JComponent parent, String name) {

		for (Component c : parent.getComponents()) {

			if (!(c instanceof JComponent)) {
				continue;
			}

			JComponent jc = (JComponent) c;

			if (name.equals(jc.getName())) {
				return jc;
			}

			JComponent found = findByName(jc, name);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

	// =========================
	// WRAP UI
	// =========================
	private static JPanel wrap(RowPanel row) {

		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

		wrapper.add(row);
		wrapper.add(Box.createVerticalStrut(5));
		wrapper.add(new JSeparator());

		return wrapper;
	}

	private static FieldMeta metaFromField(Field field) {
		Detail d = field.getAnnotation(Detail.class);
		return new FieldMeta(field, d != null ? d.key() : field.getName(), 0, 0);
	}

	// =========================
	// TYPE CHECK
	// =========================
	private static boolean isSimple(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	public static void extract(Object obj, Map<Field, JComponent> fieldMap) {

		Class<?> clazz = obj.getClass();

		for (Field field : clazz.getDeclaredFields()) {

			field.setAccessible(true);

			try {

				JComponent comp = fieldMap.get(field);
				if (comp == null || ReflectionUtil.isListType(field.getType())) {
					continue;
				}

				Object value = ComponentValueAccessor.getValue(comp, field.getType());

				if (value != null) {
					field.set(obj, value);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}