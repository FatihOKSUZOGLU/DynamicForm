package com.foksuzoglu.dynamicform.core.detail;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.api.IDynamicDetail;
import com.foksuzoglu.dynamicform.api.LanguageProvider;
import com.foksuzoglu.dynamicform.core.AnnotationFieldMetaCache;
import com.foksuzoglu.dynamicform.model.FieldMeta;
import com.foksuzoglu.dynamicform.validation.ValidationMessageResolver;
import com.foksuzoglu.dynamicform.validation.ValidationResult;

class DynamicDetailImpl2<T> implements IDynamicDetail<T> {
	public enum FormMode {
		EDIT, READ_ONLY
	}

	private DetailPane detailPane;
	private ValidationMessageResolver messageResolver;
	private final LanguageProvider languageProvider;
	private boolean editable = true;
	private FormMode initialMode;
	private final Map<Field, JComponent> fieldComponentMap = new LinkedHashMap<>();
	private final Class<T> clazz;
	private T current;
	private List<FieldMeta> metas;
	private PanelBuilderUtil builderUtil;

	public DynamicDetailImpl2(Class<T> clazz, FormMode mode, LanguageProvider languageProvider,
			ValidationMessageResolver validationResolver) {

		this.initialMode = mode;
		this.clazz = clazz;
		initListOfFieldMeta();

		this.editable = (mode == FormMode.EDIT);
		this.languageProvider = languageProvider;
		this.builderUtil = new PanelBuilderUtil(languageProvider, fieldComponentMap);
		this.messageResolver = validationResolver;
		buildForm();

	}

	private void initListOfFieldMeta() {
		try {
			this.metas = AnnotationFieldMetaCache.getMetadata(clazz, Detail.class);
			metas.sort(Comparator.comparingInt(FieldMeta::getRow).thenComparingInt(FieldMeta::getCol));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// --------------------------------------------------
	// BUILD FORM
	// --------------------------------------------------
	private void buildForm() {
		// Row + Col sıralama (garanti)

		builderUtil.buildPane(getPanel(), metas);
		builderUtil.applyEditableState(fieldComponentMap, editable);
		getPanel().revalidate();
		getPanel().repaint();
	}

	@Override
	public ValidationResult validate() {
		return new FormValidator(messageResolver, languageProvider).validate(fieldComponentMap);
	}

	@Override
	public T getData() {
		if (!editable && this.current != null) {
			return this.current;
		}
		ValidationResult result = validate();
		if (!result.isValid()) {
			String message = String.join("\n", result.getErrors().values());
			JOptionPane.showMessageDialog(getPanel(), message, "Validation Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		try {
			T instance = this.current != null ? this.current : clazz.getDeclaredConstructor().newInstance();
			FormDataBinder.bind(instance, fieldComponentMap, builderUtil);

			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// --------------------------------------------------
	// SET DATA (Bean Binding)
	// --------------------------------------------------

	@Override
	public void setData(T data) {
		if (data == null) {
			return;
		}
		this.current = data;
		FormDataBinder.bind(data, fieldComponentMap, builderUtil);
	}

	@Override
	public void setEditable(boolean editable) {
		// Eğer başlangıç READ_ONLY ise zorla editable yapamayız
		if (initialMode == FormMode.READ_ONLY) {
			this.editable = false;
		} else {
			this.editable = editable;
		}
		builderUtil.applyEditableState(fieldComponentMap, editable);
		getPanel().revalidate();
		getPanel().repaint();
	}

	@Override
	public void reset() {

		for (Map.Entry<Field, JComponent> entry : fieldComponentMap.entrySet()) {

			Field field = entry.getKey();
			JComponent comp = entry.getValue();

			Class<?> type = field.getType();

			if (comp instanceof JTextField) {

				if (type == String.class) {
					((JTextField) comp).setText("");
				} else {
					((JTextField) comp).setText("0");
				}
			}

			if (comp instanceof JCheckBox) {
				((JCheckBox) comp).setSelected(false);
			}

			if (comp instanceof JComboBox) {

				JComboBox<?> combo = (JComboBox<?>) comp;

				if (combo.getItemCount() > 0) {
					combo.setSelectedIndex(0);
				}
			}
		}

		getPanel().revalidate();
		getPanel().repaint();
	}

	// --------------------------------------------------
	@Override
	public DetailPane getPanel() {
		if (detailPane == null) {
			detailPane = new DetailPane();
		}
		return detailPane;
	}
}