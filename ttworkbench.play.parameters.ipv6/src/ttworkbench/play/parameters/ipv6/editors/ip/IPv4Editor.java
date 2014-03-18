package ttworkbench.play.parameters.ipv6.editors.ip;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificators;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyResult;

import com.testingtech.muttcn.values.StringValue;

/*IPv4 Format: nnn.nnn.nnn.nnn*/

public class IPv4Editor extends ValidatingEditor<StringValue> {

	private static final String TITLE = "IPv4 Editor";
	private static final String DESCRIPTION = "";

	final Display display = Display.getCurrent();

	private CLabel label;
	private StyledText text;

	public IPv4Editor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {

		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();

		label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName() + "_ADDRESS");
		label.setLayoutData( layoutData[0]);

		text = new StyledText( theContainer, SWT.BORDER | SWT.SINGLE);

		EventHandler handler = new EventHandler();
		text.addVerifyListener( handler);
		text.addModifyListener( handler);
		text.addFocusListener( handler);
		text.setLayoutData( layoutData[0]);

	}

	private class EventHandler implements VerifyListener, ModifyListener, FocusListener {

		final String HELPVALUE = "IP or hostname";
		final Color COLOR_HELP = display.getSystemColor( SWT.COLOR_GRAY);
		final Color COLOR_NORMAL = display.getSystemColor( SWT.COLOR_BLACK);

		private IPv4Verificator verificator = Verificators.getVerificator( IPv4Verificator.class);

		protected boolean ignore = false;
		/* indicates the State, with no Input */
		protected boolean empty = true;
		private StyledText input = text;

		public EventHandler() {
			super();
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
					if (input.getText() == "")
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
			VerifyResult<String> result = verificator.verify( theText);

			// evaluate verification result
			if (!result.verified) {
				getMessageView().flashMessages( result.messages);
				return false;
			}
			return true;
		}
	}
}
