package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;


public class IPv6Widget extends AbstractWidget {

	private static final String TITLE = "IPv6 Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;
	
	public IPv6Widget() {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEditor(IParameterEditor theTheEditor) {
		// TODO Auto-generated method stub
		
	}

}
