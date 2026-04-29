package com.foksuzoglu.dynamicform.core.detail;

import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.core.detail.DynamicDetailImpl.FormMode;
import com.foksuzoglu.dynamicform.validation.DefaultValidationMessageResolver;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;

public final class DetailPanelFactory {

	private DetailPanelFactory() {
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

		public IDynamicDetail<T> build() {

			if (languageProvider == null) {
				languageProvider = key -> key;
			}

			if (validationResolver == null) {
				validationResolver = new DefaultValidationMessageResolver(languageProvider);
			}

			return new DynamicDetailImpl<>(clazz, mode, languageProvider, validationResolver);
		}
	}
}
