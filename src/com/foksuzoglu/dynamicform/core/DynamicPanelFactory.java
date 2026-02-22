package com.foksuzoglu.dynamicform.core;

import com.foksuzoglu.dynamicform.api.IDynamicPanel;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.validation.DefaultValidationMessageResolver;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;

public final class DynamicPanelFactory {

	private DynamicPanelFactory() {
	}

	public static <T> Builder<T> builder(Class<T> clazz) {
		return new Builder<>(clazz);
	}

	public static class Builder<T> {

		private final Class<T> clazz;
		private LanguageProvider languageProvider;
		private ValidationMessageResolver validationResolver;

		private Builder(Class<T> clazz) {
			this.clazz = clazz;
		}

		public Builder<T> withLanguageProvider(LanguageProvider provider) {
			this.languageProvider = provider;
			return this;
		}

		public Builder<T> withValidationMessageResolver(ValidationMessageResolver resolver) {
			this.validationResolver = resolver;
			return this;
		}

		public IDynamicPanel<T> build() {

			if (languageProvider == null) {
				languageProvider = key -> key;
			}

			if (validationResolver == null) {
				validationResolver = new DefaultValidationMessageResolver(languageProvider);
			}

			return new DynamicPanelImp<T>(clazz, languageProvider, validationResolver);
		}
	}
}
