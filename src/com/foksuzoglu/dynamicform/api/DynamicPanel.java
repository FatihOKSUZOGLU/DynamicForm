package com.foksuzoglu.dynamicform.api;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DynamicPanel extends JPanel {
	private JScrollPane scrollPaneTable;
	private JScrollPane scrollPaneDeatil;
	private JTable table;

	public DynamicPanel() {
		initGUI();
	}

	private void initGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.gridx = 0;
		gbc_scrollPaneTable.gridy = 0;
		add(getScrollPaneTable(), gbc_scrollPaneTable);
		GridBagConstraints gbc_scrollPaneDeatil = new GridBagConstraints();
		gbc_scrollPaneDeatil.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDeatil.gridx = 0;
		gbc_scrollPaneDeatil.gridy = 1;
		add(getScrollPaneDeatil(), gbc_scrollPaneDeatil);
	}

	public JScrollPane getScrollPaneTable() {
		if (scrollPaneTable == null) {
			scrollPaneTable = new JScrollPane(getTable());
		}
		return scrollPaneTable;
	}

	public JScrollPane getScrollPaneDeatil() {
		if (scrollPaneDeatil == null) {
			scrollPaneDeatil = new JScrollPane();
		}
		return scrollPaneDeatil;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		return table;
	}
}
