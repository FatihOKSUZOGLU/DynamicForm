package com.foksuzoglu.dynamicform.validation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationResult {

	private final Map<String, String> errors = new LinkedHashMap<>();

	public void addError(String fieldKey, String message) {
		errors.put(fieldKey, message);
	}

	public boolean isValid() {
		return errors.isEmpty();
	}

	public Map<String, String> getErrors() {
		return Collections.unmodifiableMap(errors);
	}
}
