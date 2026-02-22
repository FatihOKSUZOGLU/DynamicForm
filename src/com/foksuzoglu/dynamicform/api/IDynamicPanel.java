package com.foksuzoglu.dynamicform.api;

public interface IDynamicPanel<T> {

	DynamicPanel getPanel();

	T getSelecedData();

	void addData(T data);

	void clear();
}
