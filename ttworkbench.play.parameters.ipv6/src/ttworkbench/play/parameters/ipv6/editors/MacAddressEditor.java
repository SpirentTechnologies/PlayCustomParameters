package ttworkbench.play.parameters.ipv6.editors;


import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.valueproviders.MacValueProvider;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacAddressEditor extends ValidatingEditor<StringValue> {

	
	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";
	
	private static final int MAC_LENGTH = 17;
	private static final String MAC_DEFAULT = "00:00:00:00:00:00";
	
	private IParameterValueProvider macValueProvider = new MacValueProvider();
	
	
	
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
		
		final boolean useTextField = false; 
		
		CLabel label = new CLabel(theContainer, SWT.LEFT);
		label.setText( this.getParameter().getDescription());
		label.setLayoutData( layoutData[0]);
		
		
		if(useTextField){
			createTextInputWidget( theContainer, layoutData[0]);
		}
		else{
			createComboBox( theContainer, layoutData[0]);
		}
	}
	
	private void createComboBox( Composite theComposite, Object theLayoutData){
		final Combo macCombo = new Combo(theComposite, SWT.DROP_DOWN);
		final Rectangle dimensions = new Rectangle(50, 50, 200, 65);
		macCombo.setBounds( dimensions);
		setWidthForText(macCombo, MAC_LENGTH);
		macCombo.setTextLimit( MAC_LENGTH);
		String items[] = {"item1", "item2", "item3"};
		
		Set<StringValue> availableValues = macValueProvider.getAvailableValues( this.getParameter());
		
		
		int numberAvailableValues = macValueProvider.getAvailableValues( this.getParameter()).size();
		macCombo.setItems( items);
		
		int index = 0;
		for(StringValue value : availableValues){
			macCombo.setItem( index, value.getTheContent().toString());
			index++;
			System.out.println(value.getTheContent().toString());
		}
		
		macCombo.addVerifyListener(new VerifyListener(){

			@Override
			public void verifyText(VerifyEvent theEvent) {
				// TODO Auto-generated method stub
				String currentText = ((Combo)theEvent.widget).getText();
				verifyEnteredText(theEvent, currentText);
			}
			
		});
		
		macCombo.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theArg0) {
				if ( macCombo.getText().isEmpty())
				  macCombo.setText( MAC_DEFAULT);
			}
		});
	}
	
	private void createTextInputWidget( Composite theComposite, Object theLayoutData) {
		final Text text = new Text( theComposite, SWT.BORDER | SWT.SINGLE);
		text.setLayoutData( theLayoutData);
		text.setText(MAC_DEFAULT);
		text.setTextLimit( MAC_LENGTH);
		setWidthForText( text, MAC_LENGTH);
		text.addVerifyListener(new VerifyListener(){

			@Override
			public void verifyText(VerifyEvent theEvent) {
				String currentText = ((Text)theEvent.widget).getText();
				verifyEnteredText(theEvent, currentText);
			}
			
		});
		
		text.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theArg0) {
				if ( text.getText().isEmpty())
				  text.setText( MAC_DEFAULT);
			}
		});
	}
	
	private static void setWidthForText( Control theTextControl, int visibleChars) {
		 GC gc = new GC( theTextControl);
		 int charWidth = gc.getFontMetrics().getAverageCharWidth();
		 gc.dispose();

		 int minWidth = visibleChars * charWidth;
		 Object layout = theTextControl.getLayoutData();
		 if ( layout instanceof GridData)
			 ((GridData) layout).minimumWidth = minWidth + 20;
		 if ( layout instanceof RowData)
			 ((RowData) layout).width = minWidth + 20;		
		 else
			 theTextControl.setSize( theTextControl.computeSize( minWidth + 20, SWT.DEFAULT));
	}
	
	// Method to verify the entered text in both Listeners: Combo or Text
	private void verifyEnteredText(VerifyEvent theEvent, String currentText){
	// TODO Auto-generated method stub
		String insertionPattern = "[a-fA-F0-9:-]*";
		Character key = theEvent.character;
		String insertion = (key == '\b') ? "" : theEvent.text; 
		int beginIndex = theEvent.start;
		int endIndex = theEvent.end;
		String leftString = currentText.substring( 0, beginIndex);
		String rightString = currentText.substring( endIndex, currentText.length());
		String modifiedText = leftString + insertion + rightString;
		
		if ( modifiedText.isEmpty())
			modifiedText = "00:00:00:00:00:00";
		
		if(modifiedText.matches( insertionPattern) && modifiedText.length() <= MAC_LENGTH){
		getParameter().getValue().setTheContent( modifiedText);
			validateDelayed( theEvent.widget);
			theEvent.doit = true;
		} else if(modifiedText.length() > MAC_LENGTH){
			theEvent.doit = false;
			getMessageView().flashMessage( new MessageRecord( "MAX Length reached", "MAC Address max length is reached", ErrorKind.warning));
		}
		else{
			theEvent.doit = false;
			getMessageView().flashMessage( new MessageRecord( "Invalid Entry", "This is not a valid character", ErrorKind.warning));
		}
	}

}
