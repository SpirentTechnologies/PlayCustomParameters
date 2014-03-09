package ttworkbench.play.parameters.ipv6.editors;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class MacAddressEditor extends AbstractEditor {

	
	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";
	
	public MacAddressEditor() {
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

		Composite macAddrContainer = new Composite( container, SWT.None); 
		{
			// determine the width to display 2 characters
			GC graphicalContext = new GC( new StyledText( macAddrContainer, SWT.NONE));
			FontMetrics fontMetrics = graphicalContext.getFontMetrics();
			int macAddrContainerWidth = 16 * fontMetrics.getAverageCharWidth();
			int macAddrContainerHeight = fontMetrics.getHeight();

			RowLayout macAddrLayout = new RowLayout();
			macAddrLayout.wrap = false;
			macAddrLayout.spacing = 2;
			macAddrLayout.marginHeight = 0;
			macAddrLayout.marginWidth = 0;
			macAddrLayout.marginTop = 0;
			macAddrLayout.marginBottom = 1;
			macAddrLayout.marginLeft = 2;
			macAddrLayout.marginRight = 2;
			macAddrContainer.setLayout( macAddrLayout);
			macAddrContainer.setSize( macAddrContainerWidth, macAddrContainerHeight);

			Color colorBlack = Display.getCurrent().getSystemColor( SWT.COLOR_BLACK);
			macAddrContainer.setBackground( colorBlack);
		}
		StyledText[] macChunks = new StyledText[6];
		for (StyledText text : macChunks) {
		    text = new StyledText( macAddrContainer, SWT.CENTER);
			text.setTextLimit( 2);
		}
		
		label.setText( this.getParameter().getValue().toString());
		label.setLayoutData( gridData);
		
		return container;
	}



	@Override
	public IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}

}
