package ttworkbench.play.parameters.ipv6.editors.ip;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.RowEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.OrVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.StringValue;

public class IPEditor extends ValidatingEditor<StringValue> {

	private static final String TITLE = "Host Editor";
	private static final String DESCRIPTION = "";

	final Display display = Display.getCurrent();

	private IVerifier<String> verifier = new OrVerifier( new IPv4Verifier(), new IPv6Verifier(), new HostnameVerifier());

	private CLabel label;
	private Text text;
	private EventHandler handler; // not a generic Handler

	private IVerifyingControl<?, StringValue> inputControl;

	public IPEditor() {
		super( TITLE, DESCRIPTION);
	}

	public IPEditor( final IVerifier<String> verifier) {
		this();
		this.verifier = verifier;
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new RowEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO remove
//		this.getAttribute("verifier") {
//			
//		}
		
		theContainer.setBackground( display.getSystemColor( SWT.COLOR_GREEN));

		label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName());

		inputControl = new VerifyingText<StringValue>( getParameter(), theContainer, SWT.BORDER | SWT.SINGLE, verifier);

		// bad solution, but functional
		text = (Text) inputControl.getControl();
		// must be done after Textinitialisation, because of dependences.
		this.handler = new EventHandler();
		text.setToolTipText( handler.HELPVALUE);
		text.addVerifyListener( handler);
		text.addModifyListener( handler);
		text.addFocusListener( handler);
		// set the Default Parameter Value
		text.setText( getParameter().getValue().getTheContent());
	}

	private class EventHandler implements VerifyListener, ModifyListener, FocusListener {

		final String HELPVALUE;
		final Color COLOR_HELP = display.getSystemColor( SWT.COLOR_GRAY);
		final Color COLOR_NORMAL = display.getSystemColor( SWT.COLOR_BLACK);

		protected boolean ignore = false;
		/* indicates the State, with no Input */
		protected boolean empty;;
		private Text input = text;

		public EventHandler() {
			super();
			this.HELPVALUE = verifier.toString();
			empty = input.getText().isEmpty();
			/* Fill with DefaultHelpText */
			focusLost( null);
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (empty) {
				setText( "");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (empty) {
				setText( HELPVALUE);
				input.setForeground( COLOR_HELP);
			} else {
				input.setForeground( COLOR_NORMAL);
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			if (!ignore) {
				if (empty) {
					text.setForeground( COLOR_NORMAL);
					empty = false;
				} else {
					if (input.getText().isEmpty())
						empty = true;
					else
						verifyText( text.getText());
				}
			}
		}

		@Override
		public void verifyText(VerifyEvent e) {
			if (!ignore) {
				if (empty) {
					return;
				}
			}
		}

		private void setText(String theText) {
			ignore = true;
			input.setText( theText);
			ignore = false;
		}

		protected boolean verifyText(final String theText) {

			VerificationResult<String> result = verifier.verify( theText);
			// successMessages will not be shown, but this is not a Problem
			getMessageView().flashMessages( result.messages);

			if (result.verified) {
				setParameterValue( theText);
				validateDelayed( inputControl);
			}

			return result.verified;
		}
	}

<<<<<<< HEAD
	public void setFocus() {
		text.setFocus();
	}

	protected void updateParameterValue() {
		this.handler.ignore = true;
		// this.getParameter().getValue().getTheContent()
		text.setText( ParameterValueUtil.getValue( this.getParameter()));
		this.handler.ignore = false;
	}
=======
	@Override
	public void reloadParameter() {
  	String updatedValue = ParameterValueUtil.getValue( getParameter());
  	// TODO set updated text without touch verification or validation process
  	// e.g. inputControl.forceText( updatedValue);
	}

>>>>>>> branch 'sprint1' of https://github.com/TestingTechnologies/PlayCustomParameters.git
}
