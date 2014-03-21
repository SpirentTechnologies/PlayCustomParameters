package ttworkbench.play.parameters.ipv6.editors.ip;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.RowEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificators;

public class VerifyingIPEditor extends ValidatingEditor<String> {

	private static final String TITLE = "Host Editor";
	private static final String DESCRIPTION = "";

	final Display display = Display.getCurrent();

	private List<IVerificator<String>> verificators = Arrays.asList( Verificators.getVerificator( IPv4Verificator.class),
			Verificators.getVerificator( IPv6Verificator.class), Verificators.getVerificator( HostnameVerificator.class));

	private CLabel label;
	private Text text;

	public VerifyingIPEditor() {
		super( TITLE, DESCRIPTION);
	}

	public VerifyingIPEditor( final IVerificator<String>... verificators) {
		this();
		this.verificators = Arrays.asList( verificators);

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
		label.setText( this.getParameter().getName() + "_ADDRESS");

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
			StringBuffer buffer = new StringBuffer();
			boolean first = true;
			for (IVerificator<String> v : verificators) {
				if (!first) {
					buffer.append( " or ");
				}
				buffer.append( v.toString());
				first = false;
			}
			this.HELPVALUE = buffer.toString();
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

		private boolean verifyText(final String theText) {
			List<MessageRecord> messages = new LinkedList<MessageRecord>();
			boolean verified = false;

			for (IVerificator<String> v : verificators) {
				VerificationResult<String> result = v.verify( theText);
				verified |= result.verified;
				if (verified) {
					getMessageView().flashMessages( result.messages);
					return verified;
				}
				messages.addAll( result.messages);
			}
			getMessageView().flashMessages( messages);
			return verified;
		}
	}

}
