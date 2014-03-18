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
import org.eclipse.swt.widgets.Widget;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IntegerEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.common.editwidgets.EditableWidgetAdapter;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificators;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingWidget;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingSpinner;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

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
	
	private IntegerTypeVerificator integerTypeVerificator = new IntegerTypeVerificator();
	private IntegerRangeVerificator integerRangeVerificator = null;
	
	private IVerifyingWidget inputWidget;
	
	
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
		inputWidget.setText( theValue.toString());
	}
	
	private void determineIntegerType() { 
		String parameterType = getParameter().getType();
		integerType = IntegerType.valueOfTypeName( parameterType);
		integerRangeVerificator = new IntegerRangeVerificator( integerType);
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
	

	
	private void createTextInputWidget( Composite theComposite, Object theLayoutData) {
		inputWidget = new VerifyingText( theComposite, SWT.BORDER | SWT.SINGLE, integerTypeVerificator, integerRangeVerificator);
		Text text = (Text) inputWidget.getEncapsulatedWidget();
		text.setText( getParameter().getValue().getTheNumber().toString());
		text.setLayoutData( theLayoutData);
		int maxNeededChars = integerType.getMaxValue().toString().length();
		if ( integerType.getMaxValue() != null)
		  text.setTextLimit( maxNeededChars);
		setWidthForText( text, maxNeededChars);
		
		setVerifyListenerToWidget( inputWidget);
		
		text.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theArg0) {
				if ( inputWidget.getText().isEmpty())
					inputWidget.setText( "0");
			}
		});
	}
	
	private void createSpinnerInputWidget( Composite theComposite, Object theLayoutData) {
		inputWidget = new VerifyingSpinner( theComposite, SWT.BORDER);
		Spinner spinner = (Spinner) inputWidget.getEncapsulatedWidget();
		spinner.setMinimum( integerType.getMinValue().intValue());
		spinner.setMaximum( integerType.getMaxValue().intValue());
		spinner.setSelection( getParameter().getValue().getTheNumber().intValue());
		spinner.setIncrement( 1);
		spinner.setPageIncrement( 100);
		spinner.setTextLimit( integerType.getMaxValue().toString().length());
		spinner.setLayoutData( theLayoutData);
		
		setVerifyListenerToWidget( inputWidget);
	
	}

	private void setVerifyListenerToWidget( IVerifyingWidget<?> theInputWidget) {
		theInputWidget.setListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				if ( !lastResult.verified) {
					getMessageView().flashMessages( lastResult.messages);
					theEvent.skipVerification = true;
					theEvent.doit = false;
				}
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {
				// verification passed, then write the value to parameter
				getParameter().getValue().setTheNumber( new BigInteger( theEvent.inputToVerify));
				// and start the validation process
				validateDelayed( 2);
				
				theEvent.doit = true;
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
