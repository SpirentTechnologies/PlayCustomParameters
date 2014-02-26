package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.widgets.tableviewer.WidgetTableViewerControl;
import ttworkbench.play.parameters.ipv6.widgets.tableviewer.TableParameterColumnType;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class DefaultWidget extends AbstractWidget {

	private static final String TITLE = "Default Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;

	public DefaultWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}

	@Override
	public Control createControl(Composite theParent) {

		theParent.setLayout( new FillLayout( SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite( theParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal( true);
		scrolledComposite.setExpandVertical( true);

		/* deprecated
		Composite composite = new WidgetGrid( scrolledComposite)
			.setEditors( getEditors())
			.getComposite();
		*/
		
		Composite composite = new WidgetTableViewerControl(scrolledComposite)
		
			.addParameterEditorHolders("All parameters", getEditors())
			
			.addTableColumn(TableParameterColumnType.COLUMN_PARAMETER_NAME, "Parameter Name")
			.addTableColumn(TableParameterColumnType.COLUMN_PARAMETER_VALUE, "Value")
			.addTableColumn(TableParameterColumnType.COLUMN_PARAMETER_DEFAULT, "Default")
			.addTableColumn(TableParameterColumnType.COLUMN_PARAMETER_TYPE, "Type")
			.addTableColumn(TableParameterColumnType.COLUMN_PARAMETER_DESCRIPTION, "Description")
			
			.getComposite();
		

		scrolledComposite.setContent( composite);
		scrolledComposite.setMinSize( composite.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		return scrolledComposite;
	}

	

	@Deprecated
	@Override
	protected Set<IParameter> filterRelevantParameters(Set<IParameter> theParameters) {
		return theParameters;
	}

	@Override
	public void addEditor(IParameterEditor theTheEditor) {
		// TODO Auto-generated method stub
		
	}

}
