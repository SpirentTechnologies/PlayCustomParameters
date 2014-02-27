package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class ParameterEditorHolder {

	private IParameterEditor<?>[] parameterEditors = new IParameterEditor<?>[0];
	private String label;
	
	
	public ParameterEditorHolder(String label, IParameterEditor<?>... editors) {
		this.label = label;
		this.parameterEditors = editors;
	}


	public IParameterEditor<?>[] getParameterEditors() {
		return parameterEditors;
	}


	public String getLabel() {
		return label;
	}
}
