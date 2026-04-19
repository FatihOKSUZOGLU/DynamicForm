package com.foksuzoglu.dynamicform.core.detail;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.core.AnnotationFieldMetaCache;
import com.foksuzoglu.dynamicform.model.FieldMeta;
import com.foksuzoglu.dynamicform.provider.FieldComponentProvider;
import com.foksuzoglu.dynamicform.provider.FieldComponentRegistry;

public class PanelBuilderUtil {

	private LanguageProvider languageProvider;

	public PanelBuilderUtil(LanguageProvider languageProvider) {
		this.languageProvider = languageProvider;
	}

	JComponent getLine(FieldMeta meta) {

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

	private JPanel buildNestedPanel(Class<?> clazz) {

		JPanel nested = new JPanel();
		nested.setLayout(new BoxLayout(nested, BoxLayout.Y_AXIS));

		List<FieldMeta> metas = AnnotationFieldMetaCache.getMetadata(clazz, Detail.class);
		metas.sort(Comparator.comparingInt(FieldMeta::getRow).thenComparingInt(FieldMeta::getCol));

		for (FieldMeta meta : metas) {
//			Field field = meta.getField();
//			if (ReflectionUtil.isSimpleType(field.getType())) {
//				nested.add(getPanelNestedRow(meta));
//			} else {
//				// recursive nested object
//				nested.add(buildNestedPanel(field.getType()));
//			}
			JComponent comp = getComp(meta);
			nested.add(comp, meta);
		}

		return nested;
	}

	public ListPanel createListPanel(FieldMeta meta) {

		ListPanel listPanel = new ListPanel(languageProvider.getText(meta.getKey()));
		listPanel.getBtnNewButton().addActionListener(e -> {
			RowPanel itemPanel = createListItemPanel(meta);
			JPanel wrapper = new JPanel();
			wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
			JSeparator separator = new JSeparator();
			wrapper.add(itemPanel);
			wrapper.add(Box.createVerticalStrut(5));
			wrapper.add(separator);
			listPanel.add(wrapper);
			itemPanel.getBtnRemove().addActionListener(event -> {
				listPanel.remove(wrapper); // 🔥 HEPSİ SİLİNİR
				listPanel.revalidate();
				listPanel.repaint();
			});
		});

		return listPanel;
	}

	public void buildPane(DetailPane detailPane, List<FieldMeta> metas, Map<Field, JComponent> fieldComponentMap) {
		for (FieldMeta meta : metas) {
			Field field = meta.getField();
			JComponent comp = getComp(meta);
			detailPane.add(comp, meta);
			fieldComponentMap.put(field, comp);
		}
	}

	public JComponent getComp(FieldMeta meta) {
		Field field = meta.getField();
		if (ReflectionUtil.isListType(field.getType())) {
			JPanel listPanel = createListPanel(meta);
			return listPanel;
		} else if (ReflectionUtil.isSimpleType(field.getType())) {
			JComponent comp = createSimplePanel(meta);
			return comp;
		} else {
			JComponent comp = buildNestedPanel(getListGenericType(field));
			return comp;
		}
	}

	private JComponent createSimplePanel(FieldMeta meta) {
		JPanel simplePanel = new JPanel();
		GridBagLayout gbl_panelMain = new GridBagLayout();
		gbl_panelMain.columnWidths = new int[] { 150, 0 };
		gbl_panelMain.rowHeights = new int[] { 0, 0 };
		gbl_panelMain.columnWeights = new double[] { 0.0, 1.0 };
		gbl_panelMain.rowWeights = new double[] { 0.0 };
		simplePanel.setLayout(gbl_panelMain);
		GridBagConstraints gbc = new GridBagConstraints();
		// ---------------- LABEL ----------------
		JLabel label = new JLabel(resolveLabel(meta.getKey()));
		label.setHorizontalAlignment(SwingConstants.RIGHT);

		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = meta.getCol();
		gbc.gridy = meta.getRow();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;

		simplePanel.add(label, gbc);

		// ---------------- COMPONENT ----------------
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = meta.getCol() + 1;
		gbc.gridy = meta.getRow();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		simplePanel.add(createComponentForType(meta), gbc);
		return simplePanel;
	}

	RowPanel createListItemPanel(FieldMeta meta) {

		RowPanel rowPanel = new RowPanel();
		// get type of list
		Class<?> genericType = getListGenericType(meta.getField());
		JComponent comp = createComponentForType(meta);
		if (!ReflectionUtil.isSimpleType(genericType)) {

			comp = buildNestedPanel(genericType);
		}
		rowPanel.getPanel().add(comp, BorderLayout.CENTER);

		return rowPanel;
	}

	private Class<?> getListGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<?>) type.getActualTypeArguments()[0];
	}

	public JComponent createComponentForType(FieldMeta fieldMeta) {
		for (FieldComponentProvider provider : FieldComponentRegistry.getProviders()) {
			if (provider.supports(fieldMeta.getField())) {
				JComponent comp = provider.create(fieldMeta);
				comp.setName(fieldMeta.getField().getName()); // 🔥 ŞART
				return comp;
			}
		}
		return new JTextField();
	}

	private String resolveLabel(String key) {
		if (languageProvider == null) {
			return key; // fallback
		}
		return languageProvider.getText(key);
	}

	void applyEditableState(Map<Field, JComponent> fieldComponentMap, boolean editable) {
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

	}

}
