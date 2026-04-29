package com.foksuzoglu.dynamicform.core.detail;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.provider.ProviderRegistry;

public class PanelBuilderUtil {

	private LanguageProvider languageProvider;
	private Map<Field, JComponent> fieldComponentMap;

	public PanelBuilderUtil(LanguageProvider languageProvider, Map<Field, JComponent> fieldComponentMap) {
		this.languageProvider = languageProvider;
		this.fieldComponentMap = fieldComponentMap;
		ProviderRegistry.getINSTANCE().setLanguage(languageProvider);
	}

	public void buildPane(DetailPane detailPane, Class<?> clazz) {
		detailPane.getScrollPane().setViewportView(ProviderRegistry.getINSTANCE().find(clazz).create(clazz));

	}

	void applyEditableState(Map<Field, JComponent> fieldComponentMap, boolean editable) {
		for (JComponent comp : fieldComponentMap.values()) {
			setReadonlyRecursive(comp, !editable);
		}

	}

	public static void setReadonlyRecursive(Component comp, boolean readonly) {

		if (comp instanceof JTextField) {
			JTextField tf = (JTextField) comp;
			tf.setEditable(!readonly);
			tf.setEnabled(true);
		}

		else if (comp instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) comp;
			cb.setEnabled(!readonly);
		}

		else if (comp instanceof JComboBox) {
			JComboBox<?> cb = (JComboBox<?>) comp;
			cb.setEnabled(!readonly);
		}

		else {
			comp.setEnabled(!readonly);
		}

		if (comp instanceof java.awt.Container) {

			java.awt.Container container = (java.awt.Container) comp;

			for (Component child : container.getComponents()) {
				setReadonlyRecursive(child, readonly);
			}
		}
	}

}
