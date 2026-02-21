package com.foksuzoglu.dynamicform.provider;

import java.lang.reflect.Field;

import javax.swing.JComponent;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public interface FieldComponentProvider {

	boolean supports(Field field);

	JComponent create(FieldMeta meta);

}
