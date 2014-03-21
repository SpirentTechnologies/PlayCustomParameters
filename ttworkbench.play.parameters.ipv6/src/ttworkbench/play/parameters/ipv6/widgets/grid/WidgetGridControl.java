package ttworkbench.play.parameters.ipv6.widgets.grid;

import java.util.Collection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class WidgetGridControl {

	private Composite parent;
	private Composite composite;
	private Collection<IParameterEditor<?>> editors;


	public WidgetGridControl(Composite parent) {
		this.parent = parent;
	}

	public Composite getComposite() {
		if(composite==null) {
			createComposite();
		}
		return composite;
		
	}


	public WidgetGridControl setEditors(Collection<IParameterEditor<?>> editors) {
		this.editors = editors;
		return this;
	}
	
	private void createComposite() {
		composite = new Composite(parent, SWT.None);
		GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, false);
		composite.setLayout( new GridLayout( 2, false));
	    for ( IParameterEditor<?> editor : editors) {
			
		    CLabel label = new CLabel( composite, SWT.LEFT);
			label.setText( editor.getParameter().getName());
			label.setLayoutData( gridData);
			
			label = new CLabel( composite, SWT.LEFT);
			label.setText( editor.getParameter().getValue().toString());
			label.setLayoutData( gridData);
			
		}
	}
}
