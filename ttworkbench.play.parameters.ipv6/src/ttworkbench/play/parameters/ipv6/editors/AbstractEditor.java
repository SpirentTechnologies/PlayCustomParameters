package ttworkbench.play.parameters.ipv6.editors;

import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class AbstractEditor implements IParameterEditor {

	private boolean enabled = true;
	private boolean visible = true;
	
	private String title;
	private String description;
	private IAttribute attribute;
	private IParameter parameter;

	
	public AbstractEditor( final String theTitle, final String theDescription) {
		super();
		this.title = theTitle;
		this.description = theDescription;
	}
	
	@Override
	public void setEnabled( boolean theEnabledState) {
	  	this.enabled = theEnabledState;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setVisible( boolean theVisibleState) {
		this.visible = theVisibleState;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setAttribute(String theName, String theValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parametersChanged(List<IParameter> theParameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameter(IParameter theParameter) {
		this.parameter = theParameter;
	}
	
	@Override
	public IParameter getParameter() {
		return parameter;
	}

}
