package ttworkbench.play.parameters.ipv6.editors;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IntegerEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacAddressEditor extends ValidatingEditor<StringValue> {

	
	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";
	
	public static final String MAC_PATTERN = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";
	private static final int MAC_LENGTH = 17;
	
	public MacAddressEditor() {
		  super( TITLE, DESCRIPTION);
	}


		
//		CLabel label1 = new CLabel( container, SWT.LEFT);
//		label1.setText( this.getParameter().getName());
//		label1.setLayoutData( gridData);
//
//		Composite macAddrContainer = new Composite( container, SWT.None); 
//		{
//			// determine the width to display 2 characters
//			GC graphicalContext = new GC( new StyledText( macAddrContainer, SWT.NONE));
//			FontMetrics fontMetrics = graphicalContext.getFontMetrics();
//			int macAddrCntainerWidth = 16 * fontMetrics.getAverageCharWidth();
//			int macAddrContainerHeight = fontMetrics.getHeight();
//
//			RowLayout macAddrLayout = new RowLayout();
//			macAddrLayout.wrap = false;
//			macAddrLayout.spacing = 2;
//			macAddrLayout.marginHeight = 0;
//			macAddrLayout.marginWidth = 0;
//			macAddrLayout.marginTop = 0;
//			macAddrLayout.marginBottom = 1;
//			macAddrLayout.marginLeft = 2;
//			macAddrLayout.marginRight = 2;
//			macAddrContainer.setLayout( macAddrLayout);
//			macAddrContainer.setSize( macAddrContainerWidth, macAddrContainerHeight);
//
//			Color colorBlack = Display.getCurrent().getSystemColor( SWT.COLOR_BLACK);
//			macAddrContainer.setBackground( colorBlack);
//		}
//		StyledText[] macChunks = new StyledText[6];
//		for (StyledText text : macChunks) {
//		    text = new StyledText( macAddrContainer, SWT.CENTER);
//			text.setTextLimit( 2);
//		}	
		

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}



	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO Auto-generated method stub
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel label = new CLabel(theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName()+"_ADDRESS");
		label.setLayoutData( layoutData[0]);
		
		createTextInputWidget( theContainer, layoutData[0]);
		
	}
	
	private void createTextInputWidget( Composite theComposite, Object theLayoutData) {
		final Text text = new Text( theComposite, SWT.BORDER | SWT.SINGLE);
		final String macFormat = "00:00:00:00:00:00";
		text.setLayoutData( theLayoutData);
		text.setText(macFormat);
		text.setTextLimit( MAC_LENGTH);
		setWidthForText( text, MAC_LENGTH);
		text.addVerifyListener(new VerifyListener(){

			@Override
			public void verifyText(VerifyEvent theEvent) {
				// TODO Auto-generated method stub
				String insertionPattern = "[a-fA-F0-9:-]*";
				String currentText = ((Text)theEvent.widget).getText();
				Character key = theEvent.character;
				String insertion = (key == '\b') ? "" : theEvent.text; 
				int beginIndex = theEvent.start;
				int endIndex = theEvent.end;
				String leftString = currentText.substring( 0, beginIndex);
				String rightString = currentText.substring( endIndex, currentText.length());
				String modifiedText = leftString + insertion + rightString;
				
				if ( modifiedText.isEmpty())
					modifiedText = "00:00:00:00:00:00";
				
				if(modifiedText.matches( insertionPattern)){
				getParameter().getValue().setTheContent( modifiedText);
					validateDelayed(2);
					theEvent.doit = true;
				}
				else{
					theEvent.doit = false;
					getMessageView().flashMessage( new MessageRecord( "Invalid Entry", "This is not a valid character", ErrorKind.warning));
				}
			}
			
		});
		
		text.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theArg0) {
				if ( text.getText().isEmpty())
				  text.setText( "0");
			}
		});
	}
	
	private static void setWidthForText( Text theTextControl, int visibleChars) {
		 GC gc = new GC( theTextControl);
		 int charWidth = gc.getFontMetrics().getAverageCharWidth();
		 gc.dispose();

		 int minWidth = visibleChars * charWidth;
		 Object layout = theTextControl.getLayoutData();
		 if ( layout instanceof GridData)
			 ((GridData) layout).minimumWidth = minWidth;
		 if ( layout instanceof RowData)
			 ((RowData) layout).width = minWidth;		
		 else
			 theTextControl.setSize( theTextControl.computeSize( minWidth, SWT.DEFAULT));
	}

}
