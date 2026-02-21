package com.foksuzoglu.dynamicform.core;

import com.foksuzoglu.dynamicform.api.DynamicForm;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.api.ValidationMessageResolver;
import com.foksuzoglu.dynamicform.core.DynamicFormImpl.FormMode;
import com.foksuzoglu.dynamicform.validation.DefaultValidationMessageResolver;

public final class FormPanelFactory {

	private FormPanelFactory() {
	}

	public static <T> Builder<T> builder(Class<T> clazz) {
		return new Builder<>(clazz);
	}

	public static class Builder<T> {

		private final Class<T> clazz;
		private FormMode mode = FormMode.EDIT;
		private LanguageProvider languageProvider;
		private ValidationMessageResolver validationResolver;

		private Builder(Class<T> clazz) {
			this.clazz = clazz;
		}

		public Builder<T> mode(FormMode mode) {
			this.mode = mode;
			return this;
		}

		public Builder<T> editable() {
			this.mode = FormMode.EDIT;
			return this;
		}

		public Builder<T> readOnly() {
			this.mode = FormMode.READ_ONLY;
			return this;
		}

		public Builder<T> withLanguageProvider(LanguageProvider provider) {
			this.languageProvider = provider;
			return this;
		}

		public Builder<T> withValidationMessageResolver(ValidationMessageResolver resolver) {
			this.validationResolver = resolver;
			return this;
		}

		public DynamicForm<T> build() {

			if (languageProvider == null) {
				languageProvider = key -> key;
			}

			if (validationResolver == null) {
				validationResolver = new DefaultValidationMessageResolver(languageProvider);
			}

			return new DynamicFormImpl<>(clazz, mode, languageProvider, validationResolver);
		}
	}
}
