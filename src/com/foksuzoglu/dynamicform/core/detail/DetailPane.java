package com.foksuzoglu.dynamicform.core.detail;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.foksuzoglu.dynamicform.model.FieldMeta;

public class DetailPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JPanel panelMain;
	private JLabel lblNewLabel;

	public DetailPane() {
		initGUI();
	}

	private void initGUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(getScrollPane(), gbc_scrollPane);
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getPanelMain());
		}
		return scrollPane;
	}

	public JPanel getPanelMain() {
		if (panelMain == null) {
			panelMain = new JPanel();
			GridBagLayout gbl_panelMain = new GridBagLayout();
			gbl_panelMain.columnWidths = new int[] { 0 };
			gbl_panelMain.rowHeights = new int[] { 0, 0 };
			gbl_panelMain.columnWeights = new double[] { 1.0 };
			gbl_panelMain.rowWeights = new double[] { 0.0 };
			panelMain.setLayout(gbl_panelMain);

		}
		return panelMain;
	}

	public void add(JComponent comp, FieldMeta meta) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 2, 5);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = meta.getCol();
		gbc.gridy = meta.getRow();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addToMain(comp, gbc);

	}

	private void addToMain(JComponent comp, GridBagConstraints gbc) {

		getPanelMain().add(comp, gbc);
		getPanelMain().revalidate();
		getPanelMain().repaint();
	}

	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("New label");
		}
		return lblNewLabel;
	}
}
