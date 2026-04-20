package com.foksuzoglu.dynamicform.provider;

import java.util.List;

public class ProviderRegistry {

	public static final List<FieldComponentProvider> providers = List.of(new StringFieldProvider(),
			new BooleanFieldProvider(), new EnumFieldProvider(), new NumberFieldProvider() // 🔥 bunu ekle
	);

	public static FieldComponentProvider find(Class<?> targetType) {
		return providers.stream().filter(p -> p.supports(targetType)).findFirst()
				.orElseThrow(() -> new RuntimeException("No provider for: " + targetType));
	}

}
