package com.foksuzoglu.dynamicform.validation;

import java.util.Map;

import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.api.ValidationMessageResolver;

public class DefaultValidationMessageResolver implements ValidationMessageResolver {

	private final LanguageProvider languageProvider;

	public DefaultValidationMessageResolver(LanguageProvider provider) {
		this.languageProvider = provider;
	}

	@Override
	public String resolve(String key, Map<String, Object> params) {

		String template = languageProvider.getText(key);

		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				template = template.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
			}
		}

		return template;
	}
}
