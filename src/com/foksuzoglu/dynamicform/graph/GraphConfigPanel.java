package com.foksuzoglu.dynamicform.graph;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphConfigPanel<T> extends JPanel {

	public GraphConfigPanel(List<Field> fields, Consumer<GraphPanel<T>> onCreate) {

		setLayout(new GridLayout(3, 1));

		JComboBox<Field> xCombo = new JComboBox<>(fields.toArray(new Field[0]));
		JTextField xmin = new JTextField();
		JTextField xmax = new JTextField();

		JComboBox<Field> yCombo = new JComboBox<>(fields.toArray(new Field[0]));
		JTextField ymin = new JTextField();
		JTextField ymax = new JTextField();

		JPanel xRow = new JPanel(new GridLayout(1, 3));
		xRow.add(xCombo);
		xRow.add(xmin);
		xRow.add(xmax);

		JPanel yRow = new JPanel(new GridLayout(1, 3));
		yRow.add(yCombo);
		yRow.add(ymin);
		yRow.add(ymax);

		JButton create = new JButton("Create");

		create.addActionListener(e -> {

			Field xf = (Field) xCombo.getSelectedItem();
			Field yf = (Field) yCombo.getSelectedItem();

			double xminVal = Double.parseDouble(xmin.getText());
			double xmaxVal = Double.parseDouble(xmax.getText());

			double yminVal = Double.parseDouble(ymin.getText());
			double ymaxVal = Double.parseDouble(ymax.getText());

			GraphPanel<T> panel = new GraphPanel<>(xf, yf, xminVal, xmaxVal, yminVal, ymaxVal);

			onCreate.accept(panel);
		});

		add(xRow);
		add(yRow);
		add(create);
	}
}