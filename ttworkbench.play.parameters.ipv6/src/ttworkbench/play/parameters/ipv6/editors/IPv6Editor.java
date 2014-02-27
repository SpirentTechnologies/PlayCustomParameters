package ttworkbench.play.parameters.ipv6.editors;

import java.util.Set;

import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;

public class IPv6Editor extends AbstractEditor<String> {

	private static final String TITLE = "IPv6 Editor";
	private static final String DESCRIPTION = "";
	
	public IPv6Editor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public Composite createControl(Composite theParent, Object... theParams) {
		
		// TODO solve problems with GridLayout: Width of each cell in a row has the width of the smallest cell. 
		
		Object layoutData = new GridData( SWT.FILL, SWT.FILL, true, false);
		Layout layout = new GridLayout( 3, false);
		
		for (Object object : theParams) {
		  if ( object instanceof GridData || 
			   object instanceof RowData ||	  
		       object instanceof FormData)
			  layoutData = object;
		  if ( object instanceof Layout)
		    layout = (Layout) object;
		}
		
		
		Composite container = new Composite( theParent, SWT.None);
		container.setLayout( layout);
		Color color = Display.getCurrent().getSystemColor( SWT.COLOR_DARK_MAGENTA);
		container.setBackground( color);
		
        CLabel label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getName());
		label.setLayoutData( layoutData);
		
		label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getValue());
		label.setLayoutData( layoutData);
		
		String valueString = "";
		Set<String> values = getValues();
		for (String value : values) {
		  valueString += value + ", ";	
		}
		
		label = new CLabel( container, SWT.LEFT);
		label.setText( valueString);
		label.setLayoutData( layoutData);
		
		container.setSize( container.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		container.layout();
		
		return container;
	}

}
