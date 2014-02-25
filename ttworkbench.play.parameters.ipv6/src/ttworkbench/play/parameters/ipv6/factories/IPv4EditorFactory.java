package ttworkbench.play.parameters.ipv6.factories;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IMediator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditorFactory;

public class IPv4EditorFactory implements IParameterEditorFactory {

	public IPv4EditorFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isApplicable(IParameter<? extends Object> parameter) {
		// TODO Auto-generated method stub
		parameter.getType(); //"Module1.Type2"
		parameter.getName(); //"Module1.Parameter2"
		return false;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void setDescription(String description) {
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
		
		return new IParameterEditor() {
			
			private static final String TITLE = "IPv4 Parameter Editor";
			
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

