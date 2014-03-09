package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class DefaultEditor extends AbstractEditor<Object> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";
	
	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public Composite createControl(Composite theTheParent, Object... theParams) {
		IParameter<Object> par = getParameter();
		
		Composite composite = new Group(theTheParent, SWT.NONE);
		

		composite.setLayout(new GridLayout(2, false));
		Label label = new Label(composite, SWT.NONE);
		label.setText( par.getName());
		label.setToolTipText( par.getDescription());
		
		Text text = new Text(composite, SWT.READ_ONLY | SWT.BORDER);
		text.setText( String.valueOf( par.getValue()));
		text.setToolTipText( par.getType());
		
		return composite;
	}

	@Override
	public void setConfiguration(IConfiguration theConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}




}
