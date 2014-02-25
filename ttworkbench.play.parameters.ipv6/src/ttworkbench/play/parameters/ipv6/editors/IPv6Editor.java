package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;

public class IPv6Editor extends AbstractEditor {

	private static final String TITLE = "IPv6 Editor";
	private static final String DESCRIPTION = "";
	
	public IPv6Editor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public Composite createControl(Composite theParent, Object... theParams) {
		
		GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, false);
		Layout layout = new GridLayout( 3, false);
		for (Object object : theParams) {
		  if ( object instanceof GridData)
			gridData = (GridData) object;
		  if ( object instanceof Layout)
		    layout = (Layout) object;
		}
		
		Composite container = new Composite( theParent, SWT.None);
		container.setLayout( layout);
		
        CLabel label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getName());
		label.setLayoutData( gridData);
		
		label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getValue().toString());
		label.setLayoutData( gridData);
		
		return container;
	}

}
