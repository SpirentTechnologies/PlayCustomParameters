package ttworkbench.play.parameters.ipv6.editors.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.StringValue;

public class StringEditor extends VerifyingEditor<Text,StringValue> {
	
	private static final String TITLE = "String Editor";
	private static final String DESCRIPTION = "";
	
	private String regex;
	
	private IVerifier<String> regexVerifier;
	
	public StringEditor() {
		this( ".*");
	}
	
	public StringEditor( final String theRegex) {
		super( TITLE, DESCRIPTION);
		this.regex = theRegex;
		regexVerifier = new SimpleRegexVerifier( regex);
	}
	
	
	@Override
	public void setAttribute(String theName, String theValue) {
		if ( theName.equalsIgnoreCase( "regex")) {
			regex = theValue.isEmpty() ? ".*" : theValue;
			regexVerifier = new SimpleRegexVerifier( regex);
		}
		super.setAttribute( theName, theValue);
	}

	
	private void createInputWidget( Composite theComposite, Object theLayoutData) {
		IVerifyingControl<Text, StringValue> inputControl = new VerifyingText<StringValue>( getParameter(), theComposite, SWT.BORDER | SWT.SINGLE, regexVerifier);
		
		// assign input control to editor 
		setInputControl( inputControl);
		
		setVerifyListenerToControl( inputControl);

		// initialize input control
		Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
	}
	

	private void setVerifyListenerToControl( final IVerifyingControl<Text,StringValue> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				if ( !lastResult.verified) {
					getMessageView().flashMessages( lastResult.messages);
				  // ignore after verification 
					theEvent.skipVerification = true;
					theEvent.doit = false;
				}
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {	
				// verification passed, then write the value to parameter
				forceParameterValue( theEvent.inputToVerify);
				// and start the validation process
				validateDelayed( theInputControl);
				theEvent.doit = true;
			}
			
		
		
		});
	}


	@Override
	protected void createEditRow(Composite theContainer) {
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel labelId = new CLabel( theContainer, SWT.LEFT);
		labelId.setText( this.getParameter().getId());
		labelId.setLayoutData( layoutData[0]);
		
		String toolTipString = this.getParameter().getName() + ":\n" + this.getParameter().getDescription();
		labelId.setToolTipText( toolTipString);
		
		createInputWidget( theContainer, layoutData[0]);
		
		Button reset = new Button (theContainer, SWT.PUSH);
		reset.setText ("Reset");
		reset.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent theEvent) {
				String defaultValueString = ParameterValueUtil.getDefaultValue( getParameter());
				setInputValue( defaultValueString);
				super.widgetSelected( theEvent);
			}
		});
		
		theContainer.pack(); 
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new StringEditorLookAndBehaviour();
	}
	



}
