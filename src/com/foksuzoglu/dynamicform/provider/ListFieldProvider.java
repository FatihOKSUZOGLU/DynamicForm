package com.foksuzoglu.dynamicform.provider;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.core.detail.FormDataBinder;
import com.foksuzoglu.dynamicform.core.detail.ListPanel;
import com.foksuzoglu.dynamicform.core.detail.ReflectionUtil;
import com.foksuzoglu.dynamicform.core.detail.RowPanel;

public class ListFieldProvider extends MainProvider implements FieldComponentProvider {

	public ListFieldProvider(LanguageProvider languageProvider) {
		super(languageProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public JComponent create(Class<?> clazz) {
		ListPanel listPanel = new ListPanel(clazz);
		listPanel.getBtnNewButton().addActionListener(e -> {
			RowPanel itemPanel = new RowPanel();
			Component comp = ProviderRegistry.getINSTANCE().find(clazz).create(clazz);
			itemPanel.getPanel().add(comp, BorderLayout.CENTER);
			itemPanel.getBtnRemove().addActionListener(event -> {
				listPanel.remove(itemPanel); // 🔥 HEPSİ SİLİNİR

			});
			listPanel.add(itemPanel);

		});
		return listPanel;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {

		if (!(component instanceof ListPanel)) {
			return null;
		}

		ListPanel panel = (ListPanel) component;

		Class<?> genericType = panel.getGenericType(); // 🔥 artık buradan

		return FormDataBinder.extractList(panel, genericType);
	}

	@Override
	public void setValue(JComponent component, Object value) {

		if (!(component instanceof ListPanel) || !(value instanceof List)) {
			return;
		}

		ListPanel panel = (ListPanel) component;
		List<?> list = (List<?>) value;

		panel.clear();

		for (Object item : list) {

			RowPanel row = panel.createRow(); // sende builder varsa onu kullan

			if (ReflectionUtil.isSimpleType(item.getClass())) {

				JTextField tf = FormDataBinder.findTextField(row);
				if (tf != null) {
					tf.setText(item.toString());
				}

			} else {
				FormDataBinder.bindObjectToRow(item, row);
			}

			panel.add(row);
		}

		panel.revalidate();
		panel.repaint();
	}

	@Override
	public boolean supports(Class<?> targetType) {
		return List.class.isAssignableFrom(targetType);
	}

}