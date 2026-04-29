package com.foksuzoglu.dynamicform.core.detail;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.provider.FieldComponentProvider;
import com.foksuzoglu.dynamicform.provider.ProviderRegistry;

public class ComponentValueAccessor {

	public static Object getValue(JComponent comp, Class<?> targetType) {

		try {

			// 🔥 1. COMPONENT bazlı oku (textfield, checkbox vs)
			FieldComponentProvider provider = ProviderRegistry.getINSTANCE().find(targetType);

			if (provider != null && ReflectionUtil.isSimpleType(targetType)) {
				return provider.getValue(comp, targetType);
			}

			// 🔥 2. OBJECT oluştur (complex type)
			if (!ReflectionUtil.isSimpleType(targetType)) {

				Object instance = targetType.getDeclaredConstructor().newInstance();

				fillObject(instance, comp);

				return instance;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 🔥 RECURSIVE FIELD FILL
	private static void fillObject(Object instance, JComponent comp) throws Exception {

		for (Component c : comp.getComponents()) {

			if (!(c instanceof JComponent)) {
				continue;
			}
			JComponent jc = (JComponent) c;
			String name = jc.getName();

			// 🔥 field varsa set et
			if (name != null) {

				Field field;

				try {
					field = instance.getClass().getDeclaredField(name);
				} catch (NoSuchFieldException e) {
					// farklı panel olabilir → altına in
					fillObject(instance, jc);
					continue;
				}

				field.setAccessible(true);

				Class<?> fieldType = field.getType();

				// 🔥 SIMPLE TYPE → provider ile al
				if (ReflectionUtil.isSimpleType(fieldType)) {

					FieldComponentProvider provider = ProviderRegistry.getINSTANCE().find(fieldType);

					if (provider != null) {
						Object value = provider.getValue(jc, fieldType);
						field.set(instance, value);
					}

				} else {
					// 🔥 NESTED OBJECT
					Object nested = getValue(jc, fieldType);
					field.set(instance, nested);
				}
			}

			// 🔥 HER ZAMAN ALTINA İN (çok kritik)
			fillObject(instance, jc);
		}
	}

	public static void setValue(JComponent comp, Object value, Field field) {

		if (comp == null) {
			return;
		}

		try {

			// 🔥 1. SIMPLE COMPONENT → provider ile set
			FieldComponentProvider provider = ProviderRegistry.getINSTANCE().find(field.getType());

			if (provider != null && value != null && ReflectionUtil.isSimpleType(value.getClass())) {

				provider.setValue(comp, value);
				return;
			}

			// 🔥 2. OBJECT → recursive doldur
			if (value != null && !ReflectionUtil.isSimpleType(value.getClass())) {

				fillComponent(comp, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fillComponent(JComponent comp, Object model) throws Exception {

		for (Component c : comp.getComponents()) {

			if (!(c instanceof JComponent)) {
				continue;
			}

			JComponent jc = (JComponent) c;
			String name = jc.getName();

			if (name != null) {

				Field field;

				try {
					field = model.getClass().getDeclaredField(name);
				} catch (NoSuchFieldException e) {
					// farklı panel → altına in
					fillComponent(jc, model);
					continue;
				}

				field.setAccessible(true);

				Object fieldValue = field.get(model);

				if (fieldValue == null) {
					continue;
				}

				Class<?> fieldType = field.getType();

				// 🔥 SIMPLE TYPE → provider ile set
				if (ReflectionUtil.isSimpleType(fieldType)) {

					FieldComponentProvider provider = ProviderRegistry.getINSTANCE().find(fieldType);

					if (provider != null) {
						provider.setValue(jc, fieldValue);
					}

				} else {
					// 🔥 NESTED OBJECT
					setValue(jc, fieldValue, field);
				}
			}

			// 🔥 HER ZAMAN ALTINA İN
			fillComponent(jc, model);
		}
	}
}