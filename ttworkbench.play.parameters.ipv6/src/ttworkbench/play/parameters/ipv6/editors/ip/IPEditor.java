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

import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.RowEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.OrVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.muttcn.values.StringValue;

public class IPEditor extends ValidatingEditor<StringValue> {

	private static final String TITLE = "Host Editor";
	private static final String DESCRIPTION = "";

	final Display display = Display.getCurrent();

	private IVerifier<String> verifier = new OrVerifier( new IPv4Verifier(), new IPv6Verifier(), new HostnameVerifier());

	private CLabel label;
	private Text text;

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
		theContainer.setBackground( display.getSystemColor( SWT.COLOR_GREEN));

		label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName());

		text = new Text( theContainer, SWT.BORDER | SWT.SINGLE);
		EventHandler handler = new EventHandler();
		// text.setSize( 150, SWT.DEFAULT);
		text.setToolTipText( handler.HELPVALUE);

		text.addVerifyListener( handler);
		text.addModifyListener( handler);
		text.addFocusListener( handler);
	}

	private class EventHandler implements VerifyListener, ModifyListener, FocusListener {

		final String HELPVALUE;
		final Color COLOR_HELP = display.getSystemColor( SWT.COLOR_GRAY);
		final Color COLOR_NORMAL = display.getSystemColor( SWT.COLOR_BLACK);

		protected boolean ignore = false;
		/* indicates the State, with no Input */
		protected boolean empty = true;
		private Text input = text;

		public EventHandler() {
			super();
			this.HELPVALUE = verifier.toString();
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
				}
				verifyText( text.getText());
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

			return result.verified;
		}
	}

}
