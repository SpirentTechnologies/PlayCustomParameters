package ttworkbench.ttman.parameters.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
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
	public void setTitle( String theTitle) {
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
	  return attributes ;
	}

	@Override
	public IParameterEditor create() {
		// TODO Auto-generated method stub
		return new IParameterEditor() {
			
			private static final String TITLE = "IPv6 Parameter Editor";
			
			@Override
			public void setVisible(boolean theVisible) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setEnabled(boolean theEnable) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
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
			public String getTitle() {
				return TITLE;
			}
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setParameter(IParameter theParameter) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public IParameter getParameter() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Composite createControl(Composite theTheParent,
					Object... theParams) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setConfiguration(IConfiguration theTheConfiguration) {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
