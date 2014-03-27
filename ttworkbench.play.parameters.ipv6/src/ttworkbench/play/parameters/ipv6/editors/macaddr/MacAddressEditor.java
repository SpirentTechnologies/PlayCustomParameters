package ttworkbench.play.parameters.ipv6.editors.macaddr;


import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingCombo;
import ttworkbench.play.parameters.ipv6.valueproviders.MacValueProvider;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacAddressEditor extends VerifyingEditor<Combo,StringValue> {

	
	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";
	
	private static final int MAC_LENGTH = 17;
	
	private  MacPatternVerifier macPatternVerifier = new MacPatternVerifier();
	private  MacRangeVerifier macRangeVerifier = new MacRangeVerifier();
	private  MacCharVerifier macCharVerifier = new MacCharVerifier();
	
	private IParameterValueProvider<StringValue> macValueProvider = new MacValueProvider();
	
	
	
	public MacAddressEditor() {
		  super( TITLE, DESCRIPTION);
	}
		

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new MacEditorLookAndBehaviour();
	}



	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO Auto-generated method stub
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		
		CLabel label = new CLabel(theContainer, SWT.LEFT);
		label.setText( this.getParameter().getDescription());
		label.setLayoutData( layoutData[0]);
		
		createComboBox( theContainer, layoutData[0]);
	}
	
	private void createComboBox( Composite theComposite, Object theLayoutData){
		IVerifyingControl<Combo, StringValue> inputControl = new VerifyingCombo<StringValue>( getParameter(), theComposite, SWT.BORDER, macCharVerifier, macRangeVerifier, macPatternVerifier);
		setInputControl( inputControl);
		final Combo macCombo = inputControl.getControl();
		final Rectangle dimensions = new Rectangle(50, 50, 200, 65);
		macCombo.setBounds( dimensions);
		setWidthForText(macCombo, MAC_LENGTH);
		macCombo.setTextLimit( MAC_LENGTH);
		
		Set<StringValue> availableValues = macValueProvider.getAvailableValues( this.getParameter());

		int index = 0;
		for(StringValue value : availableValues){
			macCombo.setForeground(theComposite.getDisplay().getSystemColor(SWT.COLOR_GREEN));
			macCombo.add(value.getTheContent().toString(), index);
			index++;
			System.out.println(value.getTheContent().toString());
		}
		
		macCombo.setForeground(theComposite.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		setVerifyListenerToControl( inputControl);
	}
	
	private void setVerifyListenerToControl( final IVerifyingControl<?,StringValue> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
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
				forceParameterValue( theEvent.inputToVerify);
				System.out.println("my parameter" + getParameter().getValue().getTheContent());
				// and start the validation process
				validateDelayed(theInputControl);
				
				theEvent.doit = true;
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
	
}
