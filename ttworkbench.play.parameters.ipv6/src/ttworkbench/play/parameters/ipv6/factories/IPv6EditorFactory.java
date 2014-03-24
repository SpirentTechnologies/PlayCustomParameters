package ttworkbench.play.parameters.ipv6.factories;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.editors.ip.IPEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv6Verifier;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditorFactory;

public class IPv6EditorFactory implements IParameterEditorFactory {

	private String title;
	private String description;
	private List<IAttribute> attributes = new ArrayList<IAttribute>();

	public IPv6EditorFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isApplicable(IParameter<? extends Object> theParameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTitle(String theTitle) {
		this.title = theTitle;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setDescription(String theDescription) {
		this.description = theDescription;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<IAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public IParameterEditor create() {
		return new IPEditor( new IPv6Verifier());
	}

}
