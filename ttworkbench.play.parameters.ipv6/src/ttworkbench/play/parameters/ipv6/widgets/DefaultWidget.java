package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.widgets.tableviewer.WidgetTableViewerAdvancedControl;
import ttworkbench.play.parameters.ipv6.widgets.tableviewer.ParameterEditorColumnType;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class DefaultWidget extends AbstractWidget {

	private static final String TITLE = "Default Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;

	public DefaultWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void designControl(Composite theControl) {

		theControl.setLayout( new FillLayout( SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite( theControl, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal( true);
		scrolledComposite.setExpandVertical( true);

		/* deprecated
		Composite composite = new WidgetGrid( scrolledComposite)
			.setEditors( getEditors())
			.getComposite();
		*/
		
		
		Composite composite = new WidgetTableViewerAdvancedControl(scrolledComposite)
		
		
			.addParameterEditorHolders("All parameters", getEditors().toArray( new IParameterEditor<?>[0] ))
			
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_NAME, "Parameter Id")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_VALUE, "Value")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_DEFAULT, "Default")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_TYPE, "Type")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_DESCRIPTION, "Description")
			
			.getComposite();
		

		scrolledComposite.setContent( composite);
	}

	


}
