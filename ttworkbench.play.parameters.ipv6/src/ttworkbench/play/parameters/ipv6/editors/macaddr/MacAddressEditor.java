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

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingCombo;
import ttworkbench.play.parameters.ipv6.valueproviders.MacValueProvider;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacAddressEditor extends ValidatingEditor<StringValue> {

	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";

	private static final int MAC_LENGTH = 17;
	private static final String MAC_DEFAULT = "00:00:00:00:00:00";

	private MacPatternVerifier macPatternVerifier = new MacPatternVerifier();
	private MacRangeVerifier macRangeVerifier = new MacRangeVerifier();
	private MacCharVerifier macCharVerifier = new MacCharVerifier();

	private IParameterValueProvider macValueProvider = new MacValueProvider();

	private IVerifyingControl<Combo, StringValue> inputControl;

	public MacAddressEditor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO Auto-generated method stub
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();

		CLabel label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getDescription());
		label.setLayoutData( layoutData[0]);

		createComboBox( theContainer, layoutData[0]);
	}

	private void createComboBox(Composite theComposite, Object theLayoutData) {
		inputControl = new VerifyingCombo<StringValue>( getParameter(), theComposite, SWT.BORDER, macCharVerifier,
				macRangeVerifier, macPatternVerifier);
		final Combo macCombo = inputControl.getControl();
		final Rectangle dimensions = new Rectangle( 50, 50, 200, 65);
		macCombo.setBounds( dimensions);
		setWidthForText( macCombo, MAC_LENGTH);
		macCombo.setTextLimit( MAC_LENGTH);
		String items[] = { "item1", "item2", "item3" };

		Set<StringValue> availableValues = macValueProvider.getAvailableValues( this.getParameter());

		macCombo.setItems( items);

		int index = 0;
		for (StringValue value : availableValues) {
			macCombo.setItem( index, value.getTheContent().toString());
			index++;
			System.out.println( value.getTheContent().toString());
		}

		setVerifyListenerToControl( inputControl);
	}

	private void setVerifyListenerToControl(final IVerifyingControl<?, StringValue> theInputControl) {
		theInputControl.setListener( new IVerificationListener<String>() {

			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {
			}

			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() - 1);
				if (!lastResult.verified) {
					getMessageView().flashMessages( lastResult.messages);
					theEvent.skipVerification = true;
					theEvent.doit = false;
				}
			}

			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {
				// verification passed, then write the value to parameter
				getParameter().getValue().setTheContent( theEvent.inputToVerify);
				System.out.println( "my parameter" + getParameter().getValue().getTheContent());
				// and start the validation process
				validateDelayed( theInputControl);

				theEvent.doit = true;
			}
		});
	}

	private static void setWidthForText(Control theTextControl, int visibleChars) {
		GC gc = new GC( theTextControl);
		int charWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		int minWidth = visibleChars * charWidth;
		Object layout = theTextControl.getLayoutData();
		if (layout instanceof GridData)
			( (GridData) layout).minimumWidth = minWidth + 20;
		if (layout instanceof RowData)
			( (RowData) layout).width = minWidth + 20;
		else
			theTextControl.setSize( theTextControl.computeSize( minWidth + 20, SWT.DEFAULT));
	}

	@Override
	public void setFocus() {
		inputControl.getControl().setFocus();
	}

	@Override
	protected void updateParameterValue() {
		String updatedValue = ParameterValueUtil.getValue( getParameter());
		inputControl.forceText( updatedValue);
	}
}