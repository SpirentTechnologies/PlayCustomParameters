package ttworkbench.play.parameters.ipv6.editors.integer;

import java.awt.Toolkit;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IntegerEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificator;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificators;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyResult;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import de.tu_berlin.cs.uebb.muttcn.runtime.PortLocalEntity.ObjectState;

public class IntegerEditor extends ValidatingEditor<IntegerValue> {
	

	private enum CheckResult {
		IS_INTEGER, IS_IN_RANGE, IS_CHECKED
	}
	

	private static final String TITLE = "Integer Editor";
	private static final String DESCRIPTION = "";
	
	private IntegerType integerType = IntegerType.UNSIGNED_INT;
	private boolean useOnlyTextField;
	
	private Text text;
	private Spinner spinner;
	
	private IntegerTypeVerificator integerTypeVerificator = Verificators.getVerificator( IntegerTypeVerificator.class);
	private IntegerRangeVerificator integerRangeVerificator = Verificators.getVerificator( IntegerRangeVerificator.class);
	
	
	
	public IntegerEditor() {
		super( TITLE, DESCRIPTION);
	}
	
	public IntegerEditor( final boolean optionUseOnlyTextField) {
		super( TITLE, DESCRIPTION);
		this.useOnlyTextField = optionUseOnlyTextField;
	}

	
	@Override
	public void setParameter(IParameter<IntegerValue> theParameter) {
		super.setParameter( theParameter);
		determineIntegerType();
	}
	
	public void setValue( final BigInteger theValue) {
		if ( text != null)
			text.setText( theValue.toString());
		else if ( spinner != null)
			spinner.setSelection( theValue.intValue());
	}
	
	private void determineIntegerType() { 
		String parameterType = getParameter().getType();
		integerType = IntegerType.valueOfTypeName( parameterType);
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
	
	private void verifyTextInput( Event theEvent) {
		String currentText;
		if ( theEvent.widget instanceof Text)
			currentText = ((Text) theEvent.widget).getText();
		else if ( theEvent.widget instanceof Spinner)
			currentText = ((Spinner) theEvent.widget).getText();
		else return;

		Character key = theEvent.character;
		String insertion = (key == '\b') ? "" : theEvent.text; 
		int beginIndex = theEvent.start;
		int endIndex = theEvent.end;
		String leftString = currentText.substring( 0, beginIndex);
		String rightString = currentText.substring( endIndex, currentText.length());
		String modifiedText = leftString + insertion + rightString;
	  
		if ( modifiedText.isEmpty())
	  	modifiedText = "0";
		
	/*	String modifiedText = Adapter.getModifiedText( theEvent);
		R1 integerVerificator.verify();
		R2 integerRangeVerificator.verify();
		R1 or R2
		getMessageView().flashMessage( R1.message);
		*/
		
		VerifyResult<String> integerTypeVerifyResult = integerTypeVerificator.verify( modifiedText);
		VerifyResult<String> integerRangeVerifyResult = integerRangeVerificator.verify( modifiedText, integerType);
		
		for (VerifyResult<?> verifyResult : Arrays.asList( integerTypeVerifyResult, integerRangeVerifyResult)) {
			if ( !verifyResult.verified) {
				getMessageView().flashMessages( verifyResult.messages);
				theEvent.doit = false;
				return;
			}
		}
		
		// verification passed, then validate
		getParameter().getValue().setTheNumber( new BigInteger( modifiedText));
		validateDelayed( 2);
		theEvent.doit = true;
		
		//Adapter.setText( );
	
	}
	
	private void createTextInputWidget( Composite theComposite, Object theLayoutData) {
		text = new Text( theComposite, SWT.BORDER | SWT.SINGLE);
		text.setText( getParameter().getValue().getTheNumber().toString());
		text.setLayoutData( theLayoutData);
		int maxNeededChars = integerType.getMaxValue().toString().length();
		if ( integerType.getMaxValue() != null)
		  text.setTextLimit( maxNeededChars);
		setWidthForText( text, maxNeededChars);
		text.addListener( SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent( Event theEvent) {
				verifyTextInput( theEvent);
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

	private void createSpinnerInputWidget( Composite theComposite, Object theLayoutData) {
		spinner = new Spinner ( theComposite, SWT.BORDER);
		spinner.setMinimum( integerType.getMinValue().intValue());
		spinner.setMaximum( integerType.getMaxValue().intValue());
		spinner.setSelection( getParameter().getValue().getTheNumber().intValue());
		spinner.setIncrement( 1);
		spinner.setPageIncrement( 100);
		spinner.setTextLimit( integerType.getMaxValue().toString().length());
		spinner.setLayoutData( theLayoutData);
		
		spinner.addListener( SWT.Verify, new Listener() {

			@Override
			public void handleEvent(Event theEvent) {
				verifyTextInput( theEvent);
			}
		});
	
	}
	
	@Override
	protected void createEditRow(Composite theContainer) {
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getId());
		label.setLayoutData( layoutData[0]);
		
		String toolTipString = this.getParameter().getName() + ":\n" + this.getParameter().getDescription();
		label.setToolTipText( toolTipString);
		
		if ( useOnlyTextField ||
				 integerType.getMinValue() == null ||
				 integerType.getMaxValue() == null ||
				 integerType.getMinValue().compareTo( new BigInteger( String.valueOf( Integer.MIN_VALUE))) < 0 ||
				 integerType.getMaxValue().compareTo( new BigInteger( String.valueOf( Integer.MAX_VALUE))) > 0) {
			createTextInputWidget( theContainer, layoutData[0]);
		} else {
			createSpinnerInputWidget( theContainer, layoutData[0]);
		}
			
		Button reset = new Button (theContainer, SWT.PUSH);
		reset.setText ("Reset");
		reset.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent theE) {
				setValue( getParameter().getDefaultValue().getTheNumber());
				super.widgetSelected( theE);
			}
		});
		//label.setText( this.getParameter().getDescription());
		//label.setLayoutData( layoutData[2]);
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}





}
