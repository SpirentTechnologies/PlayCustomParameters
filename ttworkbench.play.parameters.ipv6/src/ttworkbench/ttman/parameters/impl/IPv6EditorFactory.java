package ttworkbench.ttman.parameters.impl;

import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditorFactory;

public class IPv6EditorFactory implements IParameterEditorFactory {

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
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String theDescription) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IParameterEditor create() {
		// TODO Auto-generated method stub
		return new IParameterEditor() {
			
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
				// TODO Auto-generated method stub
				return null;
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
		};
	}

}
