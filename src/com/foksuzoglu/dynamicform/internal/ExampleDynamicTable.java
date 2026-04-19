package com.foksuzoglu.dynamicform.internal;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.foksuzoglu.dynamicform.api.IDynamicPanel;
import com.foksuzoglu.dynamicform.core.DynamicPanelFactory;

public class ExampleDynamicTable extends JFrame {

	private static final int OBJ_SIZE = 100;
	private IDynamicPanel<User> form; // lazy
	private JButton createButton; // lazy
	private JButton randomButton; // lazy

	private JButton readOnlyButton;

	private JButton resetButton;
	private JPanel mainPanel; // lazy

	public ExampleDynamicTable() {
		super("Dynamic Form Demo");
		initFrame();
		initUI();
	}

	private void initFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setLocationRelativeTo(null);
	}

	private void initUI() {
		setContentPane(getMainPanel());
	}

	// ---------------- LAZY PANEL ----------------

	private JPanel getMainPanel() {
		if (mainPanel == null) {

			mainPanel = new JPanel(new BorderLayout(10, 10));

			mainPanel.add(getForm().getPanel(), BorderLayout.CENTER);

			JPanel bottom = new JPanel(new BorderLayout(10, 10));
			bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			bottom.add(getRandomButton(), BorderLayout.WEST);
			bottom.add(getResetButton(), BorderLayout.NORTH);
			bottom.add(getCreateButton(), BorderLayout.EAST);

			mainPanel.add(bottom, BorderLayout.SOUTH);
		}
		return mainPanel;
	}

	// ---------------- LAZY FORM ----------------

	private JButton getResetButton() {

		if (resetButton == null) {

			resetButton = new JButton("Clear");

			resetButton.addActionListener(e -> {
				getForm().clear();
			});
		}

		return resetButton;
	}

	private IDynamicPanel<User> getForm() {
		if (form == null) {
			form = DynamicPanelFactory.builder(User.class).build();

		}
		return form;
	}

	// ---------------- CREATE BUTTON ----------------

	private JButton getCreateButton() {

		if (createButton == null) {

			createButton = new JButton("SelectedSHow");

			createButton.addActionListener(e -> {

				User user = getForm().getSelecedData();

				if (user == null) {
					return; // validation hatası
				}

				JOptionPane.showMessageDialog(this, user.toString(), "Selected Object",
						JOptionPane.INFORMATION_MESSAGE);
			});
		}

		return createButton;
	}

	// ---------------- RANDOM BUTTON ----------------

	private JButton getRandomButton() {

		if (randomButton == null) {

			randomButton = new JButton("Random SetData");

			randomButton.addActionListener(e -> {

				for (int i = 0; i < OBJ_SIZE; i++) {
					User randomUser = generateRandomUser();

					getForm().addData(randomUser);

				}
			});
		}

		return randomButton;
	}

	// ---------------- RANDOM USER GENERATOR ----------------

	private User generateRandomUser() {

		try {
			return Generate.generateObject(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
