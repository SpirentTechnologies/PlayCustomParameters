package ttworkbench.play.parameters.ipv6.editors;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

public class IPv6Editor extends AbstractEditor<String> {

	private static final String TITLE = "IPv6 Editor";
	private static final String DESCRIPTION = "";
	
	public IPv6Editor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public Composite createControl(Composite theParent) {
		
		// TODO solve problems with GridLayout: Width of each cell in a row has the width of the smallest cell. 
		
		Object layoutData = getLookAndBehaviour().getLayoutDataOfControls()[0]; 
		Layout layout = getLookAndBehaviour().getLayout(); 
		
		
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

	@Override
	protected IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}

}
