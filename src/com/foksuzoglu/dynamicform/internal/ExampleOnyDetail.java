package com.foksuzoglu.dynamicform.internal;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.core.DetailPanelFactory;

public class ExampleOnyDetail extends JFrame {

	private IDynamicDetail<User> form; // lazy
	private JButton createButton; // lazy
	private JButton randomButton; // lazy

	private JButton readOnlyButton;

	private JButton resetButton;
	private JPanel mainPanel; // lazy

	private final Random random = new Random();

	public ExampleOnyDetail() {
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
			bottom.add(getToggleButton(), BorderLayout.CENTER);
			bottom.add(getCreateButton(), BorderLayout.EAST);

			mainPanel.add(bottom, BorderLayout.SOUTH);
		}
		return mainPanel;
	}

	// ---------------- LAZY FORM ----------------

	private JButton getToggleButton() {
		if (readOnlyButton == null) {
			readOnlyButton = new JButton("Toggle ReadOnly");
			readOnlyButton.addActionListener(e -> {
				form.setEditable(false);
			});

		}
		return readOnlyButton;
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

	private IDynamicDetail<User> getForm() {
		if (form == null) {
			form = DetailPanelFactory.builder(User.class).editable().build();

		}
		return form;
	}

	// ---------------- CREATE BUTTON ----------------

	private JButton getCreateButton() {

		if (createButton == null) {

			createButton = new JButton("Create");

			createButton.addActionListener(e -> {

				User user = getForm().getData();

				if (user == null) {
					return; // validation hatası
				}

				JOptionPane.showMessageDialog(this, user.toString(), "Created Object", JOptionPane.INFORMATION_MESSAGE);
			});
		}

		return createButton;
	}

	// ---------------- RANDOM BUTTON ----------------

	private JButton getRandomButton() {

		if (randomButton == null) {

			randomButton = new JButton("Random SetData");

			randomButton.addActionListener(e -> {

				User randomUser = generateRandomUser();

				getForm().setData(randomUser);
			});
		}

		return randomButton;
	}

	// ---------------- RANDOM USER GENERATOR ----------------

	private User generateRandomUser() {

		try {
			return generateObject(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private <T> T generateObject(Class<T> type) throws Exception {

		if (isSimpleType(type)) {
			return type.cast(generateSimpleValue(type));
		}

		T instance = type.getDeclaredConstructor().newInstance();

		for (Field field : type.getDeclaredFields()) {

			if (!field.isAnnotationPresent(Detail.class)) {
				continue;
			}

			field.setAccessible(true);

			Class<?> fieldType = field.getType();
			Object value;

			if (isSimpleType(fieldType)) {
				value = generateSimpleValue(fieldType);
			} else {
				value = generateObject(fieldType);
			}

			field.set(instance, value);
		}

		return instance;
	}

	private Object generateRandomValue(Class<?> type) {

		if (isSimpleType(type)) {
			return generateSimpleValue(type);
		}

		try {

			Object instance = type.getDeclaredConstructor().newInstance();

			for (Field field : type.getDeclaredFields()) {

				if (!field.isAnnotationPresent(Detail.class)) {
					continue;
				}

				field.setAccessible(true);

				Object value = generateRandomValue(field.getType());

				field.set(instance, value);
			}

			return instance;

		} catch (Exception e) {
			throw new RuntimeException("Cannot instantiate: " + type.getName(), e);
		}
	}

	private Object generateSimpleValue(Class<?> type) {

		if (type == String.class) {
			return randomString();
		}

		if (type == int.class || type == Integer.class) {
			return random.nextInt(150);
		}

		if (type == long.class || type == Long.class) {
			return Math.abs(random.nextLong()) % 10_000;
		}

		if (type == double.class || type == Double.class) {
			return random.nextDouble() * 1000;
		}

		if (type == float.class || type == Float.class) {
			return random.nextFloat() * 1000;
		}

		if (type == boolean.class || type == Boolean.class) {
			return random.nextBoolean();
		}

		if (type == short.class || type == Short.class) {
			return (short) random.nextInt(Short.MAX_VALUE);
		}

		if (type == byte.class || type == Byte.class) {
			return (byte) random.nextInt(Byte.MAX_VALUE);
		}

		if (type == char.class || type == Character.class) {
			return (char) (random.nextInt(26) + 'A');
		}

		if (type.isEnum()) {
			Object[] constants = type.getEnumConstants();
			return constants[random.nextInt(constants.length)];
		}

		return null;
	}

	private String randomString() {
		int length = 5 + random.nextInt(6); // 5-10 karakter
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}

		return sb.toString();
	}

	private boolean isSimpleType(Class<?> type) {

		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new ExampleOnyDetail().setVisible(true);
		});
	}
}
