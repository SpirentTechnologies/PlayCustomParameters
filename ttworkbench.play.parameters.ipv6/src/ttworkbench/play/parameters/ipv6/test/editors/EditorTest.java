package ttworkbench.play.parameters.ipv6.test.editors;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.editors.ip.IPv4Verificator;
import ttworkbench.play.parameters.ipv6.editors.verification.Verificators;

/*
 * Problems: Only paste of numbers, without dot-separation
 */
public class EditorTest {

	final CLabel label;
	final StyledText input;
	final Display display = Display.getCurrent();

	public EditorTest( Composite theComposite) {
		label = new CLabel( theComposite, SWT.LEFT);
		label.setText( "IP-Address");
		new Text( theComposite, SWT.BORDER | SWT.SINGLE);

		input = new StyledText( theComposite, SWT.BORDER | SWT.SINGLE);

		EventHandler handler = new EventHandler();
		input.addVerifyListener( handler);
		input.addModifyListener( handler);
		input.addFocusListener( handler);

	}

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell( display);
		shell.setLayout( new GridLayout());
		new EditorTest( shell);

		shell.open();

		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch()) {
				// if there are currently no other OS event to process
				// sleep until the next OS event is available
				display.sleep();
			}
		}

		// disposes all associated windows and their components
		display.dispose();
	}

	private class EventHandler implements VerifyListener, ModifyListener, FocusListener {

		final String HELPVALUE = "IP or hostname";
		final Color COLOR_HELP = display.getSystemColor( SWT.COLOR_GRAY);
		final Color COLOR_NORMAL = display.getSystemColor( SWT.COLOR_BLACK);

		private IPv4Verificator verificator = Verificators.getVerificator( IPv4Verificator.class);

		protected boolean ignore = false;
		/* indicates the State, with no Input */
		protected boolean empty = true;

		// private StyledText input = text;

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
					input.setForeground( COLOR_NORMAL);
					empty = false;
				} else {
					if (input.getText() == "")
						empty = true;
				}
				// verifyText( text.getText());
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

		// private boolean verifyText(final String theText) {
		// VerifyResult<String> result = verificator.verify( theText);
		//
		// // evaluate verification result
		// if (!result.verified) {
		// getMessageView().flashMessages( result.messages);
		// return false;
		// }
		// return true;
		// }
	}
}