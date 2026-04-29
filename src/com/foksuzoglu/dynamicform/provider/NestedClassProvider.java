package com.foksuzoglu.dynamicform.provider;

import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.core.AnnotationFieldMetaCache;
import com.foksuzoglu.dynamicform.core.detail.DetailPane;
import com.foksuzoglu.dynamicform.core.detail.ReflectionUtil;
import com.foksuzoglu.dynamicform.model.FieldMeta;

public class NestedClassProvider extends MainProvider implements FieldComponentProvider {

	public NestedClassProvider(LanguageProvider languageProvider) {
		super(languageProvider);
	}

	@Override
	public boolean supports(Class<?> targetType) {
		return true;
	}

	@Override
	public JComponent create(Class<?> clazz) {
		List<FieldMeta> metas = AnnotationFieldMetaCache.getMetadata(clazz, Detail.class);
		metas.sort(Comparator.comparingInt(FieldMeta::getRow).thenComparingInt(FieldMeta::getCol));
		DetailPane pane = new DetailPane();
		for (FieldMeta meta : metas) {
			if (ReflectionUtil.isListType(meta.getField().getType())) {
				Class<?> clazzList = ReflectionUtil.getListGenericType(meta.getField());
				JComponent comp = ProviderRegistry.getINSTANCE().find(meta.getField().getType()).create(clazzList);
				pane.add(createSimplePanel(meta.getKey(), comp), meta);
			} else {

				JComponent comp = ProviderRegistry.getINSTANCE().find(meta.getField().getType())
						.create(meta.getField().getType());
				pane.add(createSimplePanel(meta.getKey(), comp), meta);
			}

		}
		return pane;
	}

	@Override
	public Object getValue(JComponent component, Class<?> targetType) {
		DetailPane pane = (DetailPane) component;

		return ((JTextField) component).getText();
	}

	@Override
	public void setValue(JComponent component, Object value) {
		((JTextField) component).setText(value != null ? value.toString() : "");
	}

}