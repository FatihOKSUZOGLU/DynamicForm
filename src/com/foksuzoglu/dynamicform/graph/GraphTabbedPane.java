package com.foksuzoglu.dynamicform.graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class GraphTabbedPane<T> {

	private final JTabbedPane tabbedPane = new JTabbedPane();

	private final List<Field> graphFields;
	private final List<GraphPanel<T>> graphs = new ArrayList<>();

	public GraphTabbedPane(Class<T> clazz) {

		graphFields = GraphFieldUtils.getGraphFields(clazz);

		createConfigTab();
		enableMiddleClickClose();
	}

	public JTabbedPane getComponent() {
		return tabbedPane;
	}

	private void createConfigTab() {

		GraphConfigPanel<T> configPanel = new GraphConfigPanel<>(graphFields, this::createGraph);

		tabbedPane.addTab("Create Graph", configPanel);
	}

	private void createGraph(GraphPanel<T> panel) {

		graphs.add(panel);

		tabbedPane.addTab("Graph " + graphs.size(), panel);

		createConfigTab();
	}

	public void addData(T obj) {

		for (GraphPanel<T> g : graphs) {
			g.addData(obj);
		}
	}

	private void enableMiddleClickClose() {

		tabbedPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isMiddleMouseButton(e)) {

					int index = tabbedPane.indexAtLocation(e.getX(), e.getY());

					if (index > -1) {
						tabbedPane.remove(index);
					}
				}
			}
		});
	}
}