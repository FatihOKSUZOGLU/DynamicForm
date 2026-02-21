package com.foksuzoglu.dynamicform.api;

import java.util.Map;

public interface ValidationMessageResolver {

	String resolve(String key, Map<String, Object> params);
}