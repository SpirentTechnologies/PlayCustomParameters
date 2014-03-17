package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.widgets.tableviewer.ParameterEditorColumnType;
import ttworkbench.play.parameters.ipv6.widgets.tableviewer.WidgetTableViewerAdvancedControl;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class MacWidget extends CustomWidget{
	
	private static final String TITLE = "MAC Widget";
	private static final String DESCRIPTION = "MAC addresses editor";
	private static final Image IMAGE = null;

	public MacWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultWidgetLookAndBehaviour();
	}
}
