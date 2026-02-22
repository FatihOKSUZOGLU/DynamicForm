package com.foksuzoglu.dynamicform.core;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.foksuzoglu.dynamicform.annotation.Row;
import com.foksuzoglu.dynamicform.api.DynamicPanel;
import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.api.IDynamicPanel;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;

class DynamicPanelImp<T> implements IDynamicPanel<T> {

	private final Class<T> clazz;
	private final DynamicPanel dynamicPanel;
	private final LanguageProvider languageProvider;
	private final ValidationMessageResolver messageResolver;
	private final IDynamicDetail<T> dynamicDetail;

	private final List<T> dataList = new ArrayList<>();
	private final List<ColumnMeta<T>> columns;
	private final DynamicTableModel tableModel;

	public DynamicPanelImp(Class<T> clazz, LanguageProvider languageProvider,
			ValidationMessageResolver validationResolver) {

		this.clazz = clazz;
		this.languageProvider = languageProvider;
		this.messageResolver = validationResolver;

		this.dynamicPanel = new DynamicPanel();

		this.dynamicDetail = DetailPanelFactory.builder(clazz).readOnly().withLanguageProvider(languageProvider)
				.withValidationMessageResolver(validationResolver).build();

		dynamicPanel.getScrollPaneDeatil().setViewportView(dynamicDetail.getPanel());

		this.columns = buildColumns(clazz);
		this.tableModel = new DynamicTableModel();

		JTable table = dynamicPanel.getTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(tableModel);

		adjustColumnWidths();
		initMouseListener();
	}

	// --------------------------------------------------
	// COLUMN BUILD (NESTED SUPPORT)
	// --------------------------------------------------
	private List<ColumnMeta<T>> buildColumns(Class<?> root) {
		List<ColumnMeta<T>> result = new ArrayList<>();
		buildRecursive(root, new ArrayList<>(), result);
		return result;
	}

	private void buildRecursive(Class<?> type, List<Field> parentPath, List<ColumnMeta<T>> result) {

		for (Field field : type.getDeclaredFields()) {

			List<Field> newPath = new ArrayList<>(parentPath);
			newPath.add(field);

			if (isSimpleType(field.getType())) {

				if (field.isAnnotationPresent(Row.class)) {
					String header = field.getAnnotation(Row.class).header();
					result.add(new ColumnMeta<>(newPath, header));
				}

			} else {
				buildRecursive(field.getType(), newPath, result);
			}
		}
	}

	private boolean isSimpleType(Class<?> type) {
		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	// --------------------------------------------------
	// TABLE MODEL
	// --------------------------------------------------
	private class DynamicTableModel extends AbstractTableModel {

		@Override
		public int getRowCount() {
			return dataList.size();
		}

		@Override
		public int getColumnCount() {
			return columns.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			T row = dataList.get(rowIndex);
			return columns.get(columnIndex).extract(row);
		}

		@Override
		public String getColumnName(int column) {
			String key = columns.get(column).getHeaderKey();
			return resolveLabel(key);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
	}

	// --------------------------------------------------
	// MOUSE LISTENER
	// --------------------------------------------------
	private void initMouseListener() {
		JTable table = dynamicPanel.getTable();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int viewRow = table.rowAtPoint(e.getPoint());
				if (viewRow == -1) {
					return;
				}

				int modelRow = table.convertRowIndexToModel(viewRow);
				T selected = getDataFrom(modelRow);

				if (selected != null) {
					dynamicDetail.setData(selected);
				}
			}
		});
	}

	// --------------------------------------------------
	// COLUMN WIDTH
	// --------------------------------------------------
	private void adjustColumnWidths() {

		JTable table = dynamicPanel.getTable();

		for (int col = 0; col < table.getColumnCount(); col++) {

			int maxWidth = 100;

			TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();

			Component headerComp = headerRenderer.getTableCellRendererComponent(table,
					table.getColumnModel().getColumn(col).getHeaderValue(), false, false, -1, col);

			maxWidth = headerComp.getPreferredSize().width + 20;

			table.getColumnModel().getColumn(col).setPreferredWidth(maxWidth);
		}
	}

	// --------------------------------------------------
	// DATA MANAGEMENT
	// --------------------------------------------------
	@Override
	public void addData(T data) {
		if (data == null) {
			return;
		}

		int index = dataList.indexOf(data);

		if (index >= 0) {
			dataList.set(index, data);
			tableModel.fireTableRowsUpdated(index, index);
		} else {
			dataList.add(data);
			int last = dataList.size() - 1;
			tableModel.fireTableRowsInserted(last, last);
		}
	}

	@Override
	public void clear() {
		int size = dataList.size();
		dataList.clear();
		if (size > 0) {
			tableModel.fireTableRowsDeleted(0, size - 1);
		}
	}

	private T getDataFrom(int modelRow) {
		if (modelRow >= 0 && modelRow < dataList.size()) {
			return dataList.get(modelRow);
		}
		return null;
	}

	@Override
	public T getSelecedData() {
		return dynamicDetail.getData();
	}

	@Override
	public DynamicPanel getPanel() {
		return dynamicPanel;
	}

	// --------------------------------------------------
	// LABEL RESOLVE
	// --------------------------------------------------
	private String resolveLabel(String key) {
		if (languageProvider == null) {
			return key;
		}
		return languageProvider.getText(key);
	}
}

class ColumnMeta<T> {
	private final List<Field> path;
	private final String headerKey;

	ColumnMeta(List<Field> path, String headerKey) {
		this.path = path;
		this.headerKey = headerKey;
	}

	public String getHeaderKey() {
		return headerKey;
	}

	public Object extract(T root) {
		try {
			Object current = root;
			for (Field f : path) {
				if (current == null) {
					return null;
				}
				f.setAccessible(true);
				current = f.get(current);
			}
			return current;
		} catch (Exception e) {
			return null;
		}
	}
}