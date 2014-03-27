package ttworkbench.play.parameters.ipv6.editors.octet;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingSpinner;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

import de.tu_berlin.cs.uebb.tools.util.Display;

public class OctetEditor extends VerifyingEditor<Text,OctetStringValue> {
	
	private static final String TITLE = "Octet Editor";
	private static final String DESCRIPTION = "";
	
	private OctetType octetType = OctetType.OCT;
	
	private OctetTypeVerifier octetTypeVerifier = new OctetTypeVerifier();
	private OctetRangeVerifier octetRangeVerifier = null;
	
	public OctetEditor() {
		super( TITLE, DESCRIPTION);
	}

	
	@Override
	public void setParameter(IParameter<OctetStringValue> theParameter) {
		super.setParameter( theParameter);
		determineOctetType();
	}
	
	private void determineOctetType() { 
		String parameterType = getParameter().getType();
		octetType = OctetType.valueOfTypeName( parameterType);
		octetRangeVerifier = new OctetRangeVerifier( octetType);
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
		IVerifyingControl<Text, OctetStringValue> inputControl = new VerifyingText<OctetStringValue>( getParameter(), theComposite, SWT.BORDER | SWT.SINGLE, octetTypeVerifier, octetRangeVerifier);
		setInputControl( inputControl);
		
		setVerifyListenerToControl( inputControl);
		
		Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
		
		text.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theArg0) {
				if ( getInputControl().getText().isEmpty())
					getInputControl().setText( "0");
			}
		});
	}
	
	private void setVerifyListenerToControl( final IVerifyingControl<Text,OctetStringValue> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				
				if ( lastResult.verifier instanceof OctetTypeVerifier) {
					if ( !lastResult.verified) {
						getMessageView().flashMessages( lastResult.messages);
				    theEvent.skipVerification = true;
				    theEvent.doit = false;
					} else {
						theEvent.verifierParams = new Object[]{lastResult.output};
					}
				}
				
				if ( lastResult.verifier instanceof OctetRangeVerifier) {
					if ( !lastResult.verified) {
						getMessageView().showMessages( lastResult.messages);
						theEvent.skipVerification = true;
						theEvent.doit = true;
					} else {
						getMessageView().clearMessagesByTag( lastResult.messages.get( 0).tag);
						theEvent.doit = true;
					}
				}
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {	
				// verification passed, then write the value to parameter
				forceParameterValue( theEvent.inputToVerify);
				// and start the validation process
				validateDelayed( theInputControl);
				theEvent.doit = true;
				//OctetEditor.this.getControl().update();
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

		createTextInputWidget( theContainer, layoutData[0]);
		
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
		return new IntegerEditorLookAndBehaviour();
	}
	



}
