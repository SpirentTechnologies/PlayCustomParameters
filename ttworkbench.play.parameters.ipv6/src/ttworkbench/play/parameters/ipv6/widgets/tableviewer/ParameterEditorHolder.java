package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class ParameterEditorHolder {

	private List<IParameterEditor> parameterEditors = new LinkedList<>();
	private String label;
	
	
	public ParameterEditorHolder(String label, Collection<IParameterEditor> editors) {
		this.label = label;
		this.parameterEditors.addAll(editors);
	}


	public List<IParameterEditor> getParameterEditors() {
		return parameterEditors;
	}


	public String getLabel() {
		return label;
	}
}
