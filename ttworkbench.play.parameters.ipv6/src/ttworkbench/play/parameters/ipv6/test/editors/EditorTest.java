package ttworkbench.play.parameters.ipv6.test.editors;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/*
 * Problems: Only paste of numbers, without dot-separation
 */
public class EditorTest implements VerifyListener, ModifyListener {

	StyledText text;

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell( display);
		shell.setLayout( new GridLayout());
		shell.setText( "Editor Test");

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

	@Override
	public void modifyText(ModifyEvent theArg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verifyText(VerifyEvent theArg0) {
		// TODO Auto-generated method stub

	}
}