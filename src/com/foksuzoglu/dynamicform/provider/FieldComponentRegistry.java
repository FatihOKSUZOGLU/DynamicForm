package com.foksuzoglu.dynamicform.provider;

import java.util.ArrayList;
import java.util.List;

public class FieldComponentRegistry {

	private static final List<FieldComponentProvider> providers = new ArrayList<>();

	static {
		providers.add(new StringFieldProvider());
		providers.add(new NumberFieldProvider());
		providers.add(new BooleanFieldProvider());
		providers.add(new EnumFieldProvider());
	}

	public static List<FieldComponentProvider> getProviders() {
		return providers;
	}
}
