package com.foksuzoglu.dynamicform.provider;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.foksuzoglu.dynamicform.api.LanguageProvider;

public class MainProvider {

	private LanguageProvider languageProvider;

	public MainProvider(LanguageProvider languageProvider) {
		this.languageProvider = languageProvider;
	}

	protected String resolveLabel(String key) {
		if (languageProvider == null) {
			return key; // fallback
		}
		return languageProvider.getText(key);
	}

	protected JComponent createSimplePanel(String key, JComponent componentForType) {
		JPanel simplePanel = new JPanel();
		GridBagLayout gbl_panelMain = new GridBagLayout();
		gbl_panelMain.columnWidths = new int[] { 150, 0 };
		gbl_panelMain.rowHeights = new int[] { 0, 0 };
		gbl_panelMain.columnWeights = new double[] { 0.0, 1.0 };
		gbl_panelMain.rowWeights = new double[] { 0.0 };
		simplePanel.setLayout(gbl_panelMain);
		GridBagConstraints gbc = new GridBagConstraints();
		// ---------------- LABEL ----------------
		JLabel label = new JLabel(resolveLabel(key));
		label.setHorizontalAlignment(SwingConstants.RIGHT);

		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;

		simplePanel.add(label, gbc);

		// ---------------- COMPONENT ----------------
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		simplePanel.add(componentForType, gbc);

		return simplePanel;
	}

}
