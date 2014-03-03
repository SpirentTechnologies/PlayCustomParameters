package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;

public class DefaultEditor extends AbstractEditor<Object> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";
	
	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public Composite createControl(Composite theTheParent, Object... theParams) {
		Composite composite = new Group(theTheParent, SWT.BORDER);
		Label label = new Label(composite, SWT.NONE);
		label.setText( getParameter().getName());
		label.setToolTipText( getParameter().getDescription());
		// TODO Auto-generated method stub
		return composite;
	}

	@Override
	public void setConfiguration(IConfiguration theTheConfiguration) {
		// TODO Auto-generated method stub
		
	}




}
