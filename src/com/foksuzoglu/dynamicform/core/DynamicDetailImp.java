package com.foksuzoglu.dynamicform.core;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.model.FieldMeta;
import com.foksuzoglu.dynamicform.provider.FieldComponentProvider;
import com.foksuzoglu.dynamicform.provider.FieldComponentRegistry;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;
import com.foksuzoglu.dynamicform.validation.ValidationResult;

class DynamicDetailImpl<T> implements IDynamicDetail<T> {
	public enum FormMode {
		EDIT, READ_ONLY
	}

	private T current;

	private final Class<T> clazz;
	private JPanel panel;
	private JScrollPane scrollPane;
	private final Map<Field, JComponent> fieldComponentMap = new LinkedHashMap<>();

	private boolean editable = true;
	private FormMode initialMode;

	private ValidationMessageResolver messageResolver;

	private final LanguageProvider languageProvider;

	public DynamicDetailImpl(Class<T> clazz, FormMode mode, LanguageProvider languageProvider,
			ValidationMessageResolver validationResolver) {

		this.clazz = clazz;
		this.initialMode = mode;
		this.editable = (mode == FormMode.EDIT);
		this.languageProvider = languageProvider;
		this.panel = new JPanel(new GridBagLayout());
		this.messageResolver = validationResolver;

		buildForm();

		this.scrollPane = new JScrollPane(panel);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	// --------------------------------------------------
	// BUILD FORM
	// --------------------------------------------------
	private void buildForm() {

		panel.removeAll(); // rebuild için önemli
		fieldComponentMap.clear();

		List<FieldMeta> metas = AnnotationFieldMetaCache.getMetadata(clazz, Detail.class);

		// Row + Col sıralama (garanti)
		metas.sort(Comparator.comparingInt(FieldMeta::getRow).thenComparingInt(FieldMeta::getCol));

		for (FieldMeta meta : metas) {

			Field field = meta.getField();
			if (!isSimpleType(field.getType())) {
				GridBagConstraints gbcComp = createBaseConstraints();
				gbcComp.gridx = 0;
				gbcComp.gridwidth = 4;
				gbcComp.gridy = meta.getRow();
				gbcComp.fill = GridBagConstraints.HORIZONTAL;
				gbcComp.weightx = 3.0;

				panel.add(getLine(meta), gbcComp);
				continue;
			}

			// ---------------- LABEL ----------------
			JLabel label = new JLabel(resolveLabel(meta.getKey()) + ":");
			label.setHorizontalAlignment(SwingConstants.RIGHT);

			GridBagConstraints gbcLabel = createBaseConstraints();
			gbcLabel.gridx = meta.getCol() * 2;
			gbcLabel.gridy = meta.getRow();
			gbcLabel.anchor = GridBagConstraints.EAST;

			panel.add(label, gbcLabel);

			// ---------------- COMPONENT ----------------
			JComponent component = createComponent(meta);

			GridBagConstraints gbcComp = createBaseConstraints();
			gbcComp.gridx = meta.getCol() * 2 + 1;
			gbcComp.gridy = meta.getRow();
			gbcComp.fill = GridBagConstraints.HORIZONTAL;
			gbcComp.weightx = 1.0;

			panel.add(component, gbcComp);

			fieldComponentMap.put(field, component);
		}

		// Alt boşluğu doldurmak için dummy weight
		GridBagConstraints gbcSpacer = createBaseConstraints();
		gbcSpacer.gridx = 0;
		gbcSpacer.gridy = metas.size() + 1;
		gbcSpacer.weighty = 1.0;
		gbcSpacer.fill = GridBagConstraints.VERTICAL;
		panel.add(Box.createVerticalGlue(), gbcSpacer);

		applyEditableState();

		panel.revalidate();
		panel.repaint();
	}

	private JComponent getLine(FieldMeta meta) {

		JPanel sectionPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// ---- LEFT LINE ----
		gbc.gridx = 0;
		gbc.weightx = 1.0;
		JSeparator left = new JSeparator();
		sectionPanel.add(left, gbc);

		// ---- TITLE ----
		gbc.gridx = 1;
		gbc.weightx = 0;
		JLabel title = new JLabel(meta.getKey());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		sectionPanel.add(title, gbc);

		// ---- RIGHT LINE ----
		gbc.gridx = 2;
		gbc.weightx = 1.0;
		JSeparator right = new JSeparator();
		sectionPanel.add(right, gbc);

		return sectionPanel;
	}

	private String resolveLabel(String key) {

		if (languageProvider == null) {
			return key; // fallback
		}

		return languageProvider.getText(key);
	}

	private GridBagConstraints createBaseConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		return gbc;
	}

	// --------------------------------------------------
	// COMPONENT FACTORY
	// --------------------------------------------------
	private JComponent createComponent(FieldMeta meta) {

		for (FieldComponentProvider provider : FieldComponentRegistry.getProviders()) {

			if (provider.supports(meta.getField())) {
				return provider.create(meta);
			}
		}

		throw new IllegalArgumentException("No provider found for type: " + meta.getField().getType());
	}

	// --------------------------------------------------
	// VALIDATION (UI bağımsız)
	// --------------------------------------------------
	@Override
	public ValidationResult validate() {

		ValidationResult result = new ValidationResult();

		for (Map.Entry<Field, JComponent> entry : fieldComponentMap.entrySet()) {

			Field field = entry.getKey();
			JComponent comp = entry.getValue();
			Detail meta = field.getAnnotation(Detail.class);

			Object raw = extractValue(comp);

			Class<?> type = field.getType();

			String fieldLabel = resolveLabel(meta.key());

			// ---------------- REQUIRED ----------------
			if (meta.required()) {

				boolean empty = raw == null || (raw instanceof String && ((String) raw).trim().isEmpty());

				if (empty) {

					result.addError(meta.key(), resolveValidation("validation.required", fieldLabel));

					continue; // diğer kontrolleri atla
				}
			}

			// ---------------- NUMERIC VALIDATION ----------------
			if (raw != null && !raw.toString().trim().isEmpty()) {

				if (isNumeric(type)) {

					if (!isValidNumber(type, raw.toString())) {

						result.addError(meta.key(), resolveValidation("validation.number.invalid", fieldLabel));
					}
				}
			}
		}

		return result;
	}

	private String resolveValidation(String key, String fieldLabel) {

		Map<String, Object> params = new HashMap<>();
		params.put("field", fieldLabel);

		return messageResolver.resolve(key, params);
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

	// --------------------------------------------------
	// GET DATA
	// --------------------------------------------------
	@Override
	public T getData() {

		if (!editable && this.current != null) {
			return this.current;
		}

		ValidationResult result = validate();

		if (!result.isValid()) {
			String message = String.join("\n", result.getErrors().values());
			JOptionPane.showMessageDialog(panel, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		try {

			T instance = this.current != null ? this.current : clazz.getDeclaredConstructor().newInstance();

			fillObject(instance, clazz);

			return instance;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void fillObject(Object target, Class<?> type) throws Exception {

		for (Field field : type.getDeclaredFields()) {

			if (!field.isAnnotationPresent(Detail.class)) {
				continue;
			}

			field.setAccessible(true);

			if (isSimpleType(field.getType())) {

				JComponent comp = fieldComponentMap.get(field);

				if (comp != null) {
					Object raw = extractValue(comp);
					Object converted = convert(field, raw);
					field.set(target, converted);
				}

			} else {

				// 🔥 NESTED OBJECT
				Object nested = field.get(target);

				if (nested == null) {
					nested = field.getType().getDeclaredConstructor().newInstance();
					field.set(target, nested);
				}

				// 🔥 Recursive call
				fillObject(nested, field.getType());
			}
		}
	}

	// --------------------------------------------------
	// SET DATA (Bean Binding)
	// --------------------------------------------------

	@Override
	public void setData(T data) {
		if (data == null) {
			return;
		}
		this.current = data;
		setDataRecursive(data);
	}

	private void setDataRecursive(Object obj) {

		for (Field field : obj.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			try {
				Object value = field.get(obj);

				JComponent comp = fieldComponentMap.get(field);

				if (comp != null) {
					setComponentValue(comp, value);
				}

				if (value != null && !isSimpleType(field.getType())) {
					setDataRecursive(value);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	private void setComponentValue(JComponent comp, Object value) {

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

	// --------------------------------------------------
	private Object extractValue(JComponent comp) {

		if (comp instanceof JTextField) {
			return ((JTextField) comp).getText();
		}

		if (comp instanceof JCheckBox) {
			return ((JCheckBox) comp).isSelected();
		}

		if (comp instanceof JComboBox) {
			return ((JComboBox<?>) comp).getSelectedItem();
		}

		return null;
	}

	private Object getDefaultValue(Class<?> type) {

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

	private Object convert(Field field, Object raw) {

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

	@Override
	public void setEditable(boolean editable) {

		// Eğer başlangıç READ_ONLY ise zorla editable yapamayız
		if (initialMode == FormMode.READ_ONLY) {
			this.editable = false;
		} else {
			this.editable = editable;
		}

		applyEditableState();
	}

	private void applyEditableState() {

		for (JComponent comp : fieldComponentMap.values()) {

			if (comp instanceof JTextField) {
				((JTextField) comp).setEditable(editable);
			} else if (comp instanceof JCheckBox) {
				comp.setEnabled(editable);
			} else if (comp instanceof JComboBox) {
				comp.setEnabled(editable);
			} else {
				comp.setEnabled(editable); // fallback
			}
		}

		panel.revalidate();
		panel.repaint();
	}

	@Override
	public void reset() {

		for (Map.Entry<Field, JComponent> entry : fieldComponentMap.entrySet()) {

			Field field = entry.getKey();
			JComponent comp = entry.getValue();

			Class<?> type = field.getType();

			if (comp instanceof JTextField) {

				if (type == String.class) {
					((JTextField) comp).setText("");
				} else {
					((JTextField) comp).setText("0");
				}
			}

			if (comp instanceof JCheckBox) {
				((JCheckBox) comp).setSelected(false);
			}

			if (comp instanceof JComboBox) {

				JComboBox<?> combo = (JComboBox<?>) comp;

				if (combo.getItemCount() > 0) {
					combo.setSelectedIndex(0);
				}
			}
		}

		panel.revalidate();
		panel.repaint();
	}

	// --------------------------------------------------
	@Override
	public JScrollPane getPanel() {
		return scrollPane;
	}
}