package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IntegerEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class DefaultEditor extends AbstractEditor<Object> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";
	
	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}
	

	@Override
	public Composite createControl(Composite theTheParent) {
		IParameter<Object> parameter = getParameter();

		Composite composite = new Composite(theTheParent, SWT.NONE);
		

		composite.setLayout(new GridLayout(2, false));
		Label label = new Label(composite, SWT.NONE);
		label.setText( parameter.getName());
		label.setToolTipText( parameter.getDescription());
		
		Text text = new Text(composite, SWT.READ_ONLY | SWT.BORDER);
		text.setText( ParameterValueUtil.getValue( parameter));
		
		text.setToolTipText( parameter.getType());
		
		return composite;
	}
	
	@Override
	public void setConfiguration(IConfiguration theConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}

	

}
