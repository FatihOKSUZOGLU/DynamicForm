package com.foksuzoglu.dynamicform.core.detail;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ListPanel extends JPanel {
	private static final int MAX_VISIBLE_HEIGHT = 300;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JPanel panelList;

	private Class<?> genericType;

	public void setGenericType(Class<?> type) {
		this.genericType = type;
	}

	private Map<Object, JComponent> localMap = new HashMap<>();

	public Map<Object, JComponent> getLocalMap() {
		return localMap;
	}

	public Class<?> getGenericType() {
		return genericType;
	}

	public ListPanel(Class<?> genericType) {
		setPreferredSize(new Dimension(400, 50));
		this.genericType = genericType;
		initGUI();
	}

	public ListPanel(String title, Class<?> genericType) {
		setPreferredSize(new Dimension(400, 50));
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.genericType = genericType;
		initGUI();
	}

	private void initGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(getScrollPane(), gbc_scrollPane);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		add(getBtnNewButton(), gbc_btnNewButton);
	}

	public JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Add ...");
		}
		return btnNewButton;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getPanelList());
		}
		return scrollPane;
	}

	public JPanel getPanelList() {
		if (panelList == null) {
			panelList = new JPanel();
			panelList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
		}
		return panelList;
	}

	public void add(JComponent comp) {
		getPanelList().add(comp);
		heightCheck();
	}

	private void heightCheck() {
		int contentHeight = getPanelList().getPreferredSize().height;

		int newHeight = Math.min(contentHeight + 50, MAX_VISIBLE_HEIGHT);

		Dimension size = new Dimension(getWidth(), newHeight);

		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, newHeight));

		revalidate();
		repaint();

	}

	public void remove(JComponent comp) {
		getPanelList().remove(comp);
		heightCheck();
	}

	public void clear() {
		getPanelList().removeAll();
		heightCheck();

	}
}
