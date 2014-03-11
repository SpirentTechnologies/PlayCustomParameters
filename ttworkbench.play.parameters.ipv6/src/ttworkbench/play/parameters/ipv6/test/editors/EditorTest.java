package ttworkbench.play.parameters.ipv6.test.editors;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ttworkbench.play.parameters.ipv6.editors.IPv4Editor;

/*
 * Problems: Only paste of numbers, without dot-separation
 */
public class EditorTest {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell( display);
		// shell.setLayout( new ColumnLayout());
		shell.setText( "Editor Test");

		new IPv4Editor( "Test", "Test").createControl( shell);

		// please assign a parameter first before calling createControl
		// new IPv6Editor().createControl( shell);
		// new MacAddressEditor().createControl( shell);

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
}