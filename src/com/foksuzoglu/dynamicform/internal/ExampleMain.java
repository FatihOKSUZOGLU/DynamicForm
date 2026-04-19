package com.foksuzoglu.dynamicform.internal;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.foksuzoglu.dynamicform.graph.GraphTabbedPane;

public class ExampleMain {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			JFrame frame = new JFrame("Graph Example");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(900, 600);

			GraphTabbedPane<User> graphPane = new GraphTabbedPane<>(User.class);

			frame.add(graphPane.getComponent());

			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			startDataFeed(graphPane);
		});
	}

	private static void startDataFeed(GraphTabbedPane<User> graphPane) {

		Random r = new Random();

		new Timer(500, e -> {

			User data;
			try {
				data = Generate.generateObject(User.class);
				graphPane.addData(data);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}).start();
	}
}