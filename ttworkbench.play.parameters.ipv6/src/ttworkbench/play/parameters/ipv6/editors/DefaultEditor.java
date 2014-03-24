package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IntegerEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class DefaultEditor extends AbstractEditor<Object> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";

	private Label label;
	private Text text;

	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	protected void designControl(Composite theControl) {
		IParameter<Object> parameter = getParameter();

		theControl.setLayout( new GridLayout( 2, false));
		label = new Label( theControl, SWT.NONE);
		label.setText( parameter.getName());
		label.setToolTipText( parameter.getDescription());

		text = new Text( theControl, SWT.READ_ONLY | SWT.BORDER);
		text.setText( ParameterValueUtil.getValue( parameter));

		text.setToolTipText( parameter.getType());
	}

	@Override
	protected IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}

	@Override
	protected void updateParameterValue() {
		text.setText( ParameterValueUtil.getValue( this.getParameter()));
	}

}
