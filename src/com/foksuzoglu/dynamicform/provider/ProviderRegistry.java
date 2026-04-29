package com.foksuzoglu.dynamicform.provider;

import java.util.List;

import com.foksuzoglu.dynamicform.api.LanguageProvider;

public class ProviderRegistry {

	private LanguageProvider languageProvider;
	private static ProviderRegistry INSTANCE;

	public static ProviderRegistry getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new ProviderRegistry();
		}
		return INSTANCE;
	}

	public final List<FieldComponentProvider> providers = List.of(new StringFieldProvider(languageProvider),
			new BooleanFieldProvider(languageProvider), new EnumFieldProvider(languageProvider),
			new NumberFieldProvider(languageProvider), new ListFieldProvider(languageProvider)// 🔥

	);

	public FieldComponentProvider find(Class<?> targetType) {
		return providers.stream().filter(p -> p.supports(targetType)).findFirst()
				.orElse(new NestedClassProvider(languageProvider));
	}

	public void setLanguage(LanguageProvider languageProvider) {
		this.languageProvider = languageProvider;

	}

}
