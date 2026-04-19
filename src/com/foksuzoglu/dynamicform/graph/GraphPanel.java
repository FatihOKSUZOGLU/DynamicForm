package com.foksuzoglu.dynamicform.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GraphPanel<T> extends JPanel {

	private final Field xField;
	private final Field yField;

	private final double xmin;
	private final double xmax;
	private final double ymin;
	private final double ymax;

	private final List<T> data = new ArrayList<>();

	public GraphPanel(Field xField, Field yField, double xmin, double xmax, double ymin, double ymax) {

		this.xField = xField;
		this.yField = yField;

		this.xmin = xmin;
		this.xmax = xmax;

		this.ymin = ymin;
		this.ymax = ymax;

		setBackground(Color.white);
	}

	public void addData(T obj) {

		data.add(obj);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();

		drawAxis(g, w, h);

		for (T obj : data) {

			try {

				double x = ((Number) xField.get(obj)).doubleValue();
				double y = ((Number) yField.get(obj)).doubleValue();

				int px = (int) ((x - xmin) / (xmax - xmin) * w);
				int py = h - (int) ((y - ymin) / (ymax - ymin) * h);

				g.fillOval(px - 3, py - 3, 6, 6);

			} catch (Exception ignored) {
			}
		}
	}

	private void drawAxis(Graphics g, int w, int h) {

		g.setColor(Color.gray);

		g.drawLine(0, h - 1, w, h - 1);
		g.drawLine(1, 0, 1, h);
	}
}