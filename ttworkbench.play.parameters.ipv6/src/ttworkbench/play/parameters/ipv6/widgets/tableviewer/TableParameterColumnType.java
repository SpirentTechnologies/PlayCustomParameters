package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.HashMap;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

/*
 * This class contains constants for the Parameter application
 */
public enum TableParameterColumnType {
	COLUMN_EDITOR_TITLE,
	COLUMN_EDITOR_DESCRIPTION,
	COLUMN_PARAMETER_ID,
	COLUMN_PARAMETER_NAME,
	COLUMN_PARAMETER_VALUE,
	COLUMN_PARAMETER_DEFAULT,
	COLUMN_PARAMETER_TYPE,
	COLUMN_PARAMETER_DESCRIPTION;
	

	private interface ParameterEditorSelect { Object get(IParameterEditor editor); }
	private static HashMap<TableParameterColumnType, ParameterEditorSelect> valueSelectors = new HashMap<TableParameterColumnType, ParameterEditorSelect>();
	static {
		valueSelectors.put( TableParameterColumnType.COLUMN_EDITOR_TITLE, new ParameterEditorSelect() {
			@Override
			public String get(IParameterEditor editor) {
				return editor.getTitle();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_EDITOR_DESCRIPTION, new ParameterEditorSelect() {
			@Override
			public String get(IParameterEditor editor) {
				return editor.getDescription();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_ID, new ParameterEditorSelect() {
			@Override
			public String get(IParameterEditor editor) {
				return editor.getParameter().getId();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_NAME, new ParameterEditorSelect() {
			@Override
			public String get(IParameterEditor editor) {
				return editor.getParameter().getName();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_VALUE, new ParameterEditorSelect() {
			@Override
			public Object get(IParameterEditor editor) {
				return editor.getParameter().getValue();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_DEFAULT, new ParameterEditorSelect() {
			@Override
			public Object get(IParameterEditor editor) {
				return editor.getParameter().getDefaultValue();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_TYPE, new ParameterEditorSelect() {
			@Override
			public Object get(IParameterEditor editor) {
				return editor.getParameter().getType();
			}
		});
		valueSelectors.put( TableParameterColumnType.COLUMN_PARAMETER_DESCRIPTION, new ParameterEditorSelect() {
			@Override
			public Object get(IParameterEditor editor) {
				return editor.getParameter().getDescription();
			}
		});
	}
	
	public static Object valueOf(TableParameterColumnType type, IParameterEditor editor) {
		ParameterEditorSelect selector = valueSelectors.get( type);
		return selector!=null ? selector.get( editor) : null;
	}
}
