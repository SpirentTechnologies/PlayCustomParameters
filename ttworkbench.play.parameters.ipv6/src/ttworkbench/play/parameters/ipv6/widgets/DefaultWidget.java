package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class DefaultWidget extends AbstractWidget {

	private static final String TITLE = "Default Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;
	
	public DefaultWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}
	
	@Override
	public Control createControl(Composite theParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Set<IParameter> filterRelevantParameters(
			Set<IParameter> theParameters) {
		return theParameters;
	}

}
