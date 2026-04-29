package com.foksuzoglu.dynamicform.core.detail;

import java.lang.reflect.Field;
import java.util.Map;

import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;
import com.foksuzoglu.dynamicform.provider.FieldComponentProvider;
import com.foksuzoglu.dynamicform.provider.ProviderRegistry;

public class ComponentResolver {

	private final PanelBuilderUtil builderUtil;
	private final Map<Field, JComponent> fieldMap;

	public ComponentResolver(PanelBuilderUtil builderUtil, Map<Field, JComponent> fieldMap) {
		this.builderUtil = builderUtil;
		this.fieldMap = fieldMap;
	}

	public JComponent resolve(Field field) {

		JComponent existing = fieldMap.get(field);
		if (existing != null) {
			return existing;
		}

		FieldComponentProvider provider = ProviderRegistry.find(field.getType());
		if (provider == null) {
			return null;
		}

		FieldMeta meta = new FieldMeta(field, field.getName(), 0, 0);

		JComponent created = provider.create(meta.getField().getType());

		fieldMap.put(field, created);

		return created;
	}
}