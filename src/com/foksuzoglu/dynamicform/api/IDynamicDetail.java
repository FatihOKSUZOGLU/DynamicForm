package com.foksuzoglu.dynamicform.api;

import javax.swing.JPanel;

import com.foksuzoglu.dynamicform.validation.ValidationResult;

/**
 * Generic dynamic form abstraction.
 *
 * <p>
 * {@code DynamicForm} represents a UI form that is generated dynamically from a
 * model class (typically using reflection and annotations).
 * </p>
 *
 * <p>
 * Implementations are responsible for:
 * <ul>
 * <li>Rendering input components for annotated fields</li>
 * <li>Mapping UI values back to a model instance</li>
 * <li>Performing validation</li>
 * <li>Supporting editable and read-only modes</li>
 * </ul>
 * </p>
 *
 * <p>
 * Typical usage:
 * </p>
 *
 * <pre>
 * DynamicForm&lt;User&gt; form = FormPanelFactory.getInstance().createDialog(User.class, FormMode.EDIT);
 *
 * JPanel panel = form.getPanel();
 *
 * User user = form.getData(); // returns null if validation fails
 * </pre>
 *
 * @param <T> the model type represented by this form
 */
public interface IDynamicDetail<T> {

	/**
	 * Returns the Swing JScrollPane containing the generated form UI.
	 *
	 * <p>
	 * The returned JScrollPane can be embedded inside any container such as
	 * {@link javax.swing.JFrame}, {@link javax.swing.JDialog}, or another
	 * {@link javax.swing.JScrollPane}.
	 * </p>
	 *
	 * @return the form JScrollPane (never null)
	 */
	JPanel getPanel();

	/**
	 * Collects the current UI values and converts them into a new instance of the
	 * model type {@code T}.
	 *
	 * <p>
	 * If validation fails, this method returns {@code null}. Call
	 * {@link #validate()} explicitly if you need detailed validation errors.
	 * </p>
	 *
	 * @return populated model instance, or {@code null} if validation fails
	 */
	T getData();

	/**
	 * Performs validation on the current form state.
	 *
	 * <p>
	 * Validation does not modify form values. It only reports whether the current
	 * state is valid.
	 * </p>
	 *
	 * @return a {@link ValidationResult} describing validation outcome
	 */
	ValidationResult validate();

	/**
	 * Populates the form UI using values from the given model instance.
	 *
	 * <p>
	 * This method updates all corresponding UI components without triggering
	 * validation automatically.
	 * </p>
	 *
	 * @param data model instance used to fill the form (must not be null)
	 */
	void setData(T data);

	/**
	 * Enables or disables user input dynamically.
	 *
	 * <p>
	 * When set to {@code false}, all input components become read-only or disabled
	 * depending on their type.
	 * </p>
	 *
	 * @param editable true to allow editing, false to make form read-only
	 */
	void setEditable(boolean editable);

	/**
	 * Resets the form to its initial empty state.
	 *
	 * <p>
	 * All text fields are cleared, checkboxes are unchecked, combo boxes are reset
	 * to their default selection, and validation state is cleared.
	 * </p>
	 */
	void reset();
}
