package com.foksuzoglu.dynamicform.validation;

import java.util.Map;

public interface ValidationMessageResolver {

	String resolve(String key, Map<String, Object> params);
}