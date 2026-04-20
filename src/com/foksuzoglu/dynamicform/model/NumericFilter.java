package com.foksuzoglu.dynamicform.model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericFilter extends DocumentFilter {

	private final Class<?> type;

	public NumericFilter(Class<?> type) {
		this.type = type;
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {

		if (isValid(fb, string, offset)) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		if (isValid(fb, text, offset)) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

	private boolean isValid(FilterBypass fb, String text, int offset) {

		try {
			String current = fb.getDocument().getText(0, fb.getDocument().getLength());
			String next = new StringBuilder(current).insert(offset, text).toString();

			if (next.isEmpty()) {
				return true;
			}

			if (type == int.class || type == Integer.class) {
				Integer.parseInt(next);
			} else {
				Double.parseDouble(next); // float/double vs
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}