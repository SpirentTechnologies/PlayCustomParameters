package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.integer.IntegerEditorLookAndBehaviour;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class DefaultEditor<T extends Value> extends AbstractEditor<T> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";
	
	private Text text; 
	
	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}
	

	@Override
	protected void designControl( Composite theControl) {
		IParameter<T> parameter = getParameter();
		

		theControl.setLayout(new GridLayout(2, false));
		Label label = new Label(theControl, SWT.NONE);
		label.setText( parameter.getName());
		label.setToolTipText( parameter.getDescription());
		
		text = new Text(theControl, SWT.READ_ONLY | SWT.BORDER);
		text.setText( ParameterValueUtil.getValue( parameter));
		
		text.setToolTipText( parameter.getType());
	}
	

	@Override
	protected IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}


	@Override
	public void reloadParameter() {
		if ( hasControl())
		  text.setText( ParameterValueUtil.getValue( getParameter()));
	}

	

}
