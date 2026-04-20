package com.foksuzoglu.dynamicform.internal;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.core.detail.DetailPanelFactory;

public class ExampleOnyDetail extends JFrame {

	private IDynamicDetail<User> form;

	private JButton createButton;
	private JButton randomButton;
	private JButton toggleButton;
	private JButton resetButton;

	private JPanel mainPanel;

	private final RandomObjectGenerator generator = new RandomObjectGenerator();

	public ExampleOnyDetail() {
		super("Dynamic Form Demo");

		initFrame();
		setContentPane(getMainPanel());
	}

	// ---------------- FRAME ----------------
	private void initFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 700);
		setLocationRelativeTo(null);
	}

	// ---------------- MAIN PANEL ----------------
	private JPanel getMainPanel() {
		if (mainPanel == null) {

			mainPanel = new JPanel(new BorderLayout(10, 10));
			mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			mainPanel.add(getForm().getPanel(), BorderLayout.CENTER);
			mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);
		}
		return mainPanel;
	}

	// ---------------- FORM ----------------
	private IDynamicDetail<User> getForm() {
		if (form == null) {
			form = DetailPanelFactory.builder(User.class).editable().build();
		}
		return form;
	}

	// ---------------- BOTTOM PANEL ----------------
	private JPanel createBottomPanel() {

		JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));

		panel.add(getRandomButton());
		panel.add(getResetButton());
		panel.add(getToggleButton());
		panel.add(getCreateButton());

		return panel;
	}

	// ---------------- BUTTONS ----------------

	private JButton getRandomButton() {
		if (randomButton == null) {
			randomButton = new JButton("Random Data");

			randomButton.addActionListener(e -> {
				User user = generator.generate(User.class);
				getForm().setData(user);
			});
		}
		return randomButton;
	}

	private JButton getResetButton() {
		if (resetButton == null) {
			resetButton = new JButton("Reset");

			resetButton.addActionListener(e -> {
				getForm().reset();
			});
		}
		return resetButton;
	}

	private JButton getToggleButton() {
		if (toggleButton == null) {
			toggleButton = new JButton("Toggle Edit");

			toggleButton.addActionListener(e -> {
				// toggle logic
				form.setEditable(!form.getPanel().isEnabled());
			});
		}
		return toggleButton;
	}

	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton("Create");

			createButton.addActionListener(e -> {

				User user = getForm().getData();

				if (user == null) {
					return;
				}

				JOptionPane.showMessageDialog(this, user.toString(), "Created Object", JOptionPane.INFORMATION_MESSAGE);
			});
		}
		return createButton;
	}

	// ---------------- MAIN ----------------
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new ExampleOnyDetail().setVisible(true);
		});
	}
}