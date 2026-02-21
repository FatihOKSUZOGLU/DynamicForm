package com.foksuzoglu.dynamicform.api;

public interface LanguageProvider {

	/**
	 * Returns localized text for given key.
	 *
	 * @param key translation key
	 * @return localized text, or key itself if not found
	 */
	String getText(String key);
}