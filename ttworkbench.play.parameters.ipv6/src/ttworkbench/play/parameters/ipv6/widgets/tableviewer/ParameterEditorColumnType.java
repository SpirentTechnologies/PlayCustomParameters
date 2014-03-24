package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.HashMap;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

/*
 * This class contains constants for the Parameter application
 */
public enum ParameterEditorColumnType {
	COLUMN_EDITOR_TITLE,
	COLUMN_EDITOR_DESCRIPTION,
	COLUMN_PARAMETER_ID,
	COLUMN_PARAMETER_NAME,
	COLUMN_PARAMETER_VALUE, 
	COLUMN_PARAMETER_DEFAULT,
	COLUMN_PARAMETER_TYPE,
	COLUMN_PARAMETER_DESCRIPTION;

	public enum TableColumnEditor {
		TEXTBOX, COMBOBOX, CHECKBOX, NONE
	}

	private interface ParameterEditorSelect {
		Object get(IParameterEditor<?> editor);
	}

	private static HashMap<ParameterEditorColumnType, ParameterEditorSelect> valueSelectors = new HashMap<ParameterEditorColumnType, ParameterEditorSelect>();

	static {
		valueSelectors.put( ParameterEditorColumnType.COLUMN_EDITOR_TITLE,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getTitle();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_EDITOR_DESCRIPTION,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getDescription();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_ID,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getParameter().getId();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_NAME,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getParameter().getName();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_VALUE,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						IParameter<?> parameter = editor.getParameter();
						String value = ParameterValueUtil.getValue( parameter);
						return value;
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_DEFAULT,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						IParameter<?> parameter = editor.getParameter();
						String value = ParameterValueUtil.getDefaultValue( parameter);
						return value;
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_TYPE,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						return editor.getParameter().getType();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_DESCRIPTION,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						return editor.getParameter().getDescription();
					}
				});
	}


	public Object valueOf(IParameterEditor<?> editor) {
		ParameterEditorSelect selector = valueSelectors.get( this);
		return selector != null ? selector.get( editor) : null;
	}
}
