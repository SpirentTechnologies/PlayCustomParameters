/*******************************************************************************
 * Copyright (c)  .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * You may not use this file except in compliance with the License.
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * This project came to life under the cooperation of the Authors (cited below) and the Testing Technologies GmbH company in the frame of a University Project proposed by the FU-Berlin.
 * 
 * The software is basically a plug-in for the company's eclipse-based framework TTWorkbench. The plug-in offers a new user-friendly view that enables easy configuration of parameters meant to test IPv6 environments.
 * 
 * 
 * Contributors:
 *     
 ******************************************************************************/
package ttworkbench.play.parameters.ipv6.test.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 * Problems: Only paste of numbers, without dot-separation
 */
public class EditorTest {

	final CLabel label;
	final StyledText input;
	final Display display = Display.getCurrent();

	public EditorTest( Composite theParent) {

		final Composite container = new Composite( theParent, SWT.None);
		GridLayout containerLayout = new GridLayout();
		containerLayout.marginHeight = 0;
		containerLayout.marginWidth = 0;
		theParent.setLayout( containerLayout);
		// TODO check layout data. Is compatible? to Flowlayout or Filllayout
		container.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false, 0, 0));
		container.setLayout( new RowLayout());

		label = new CLabel( container, SWT.LEFT);
		label.setText( "IP-Address");

		Text text = new Text( container, SWT.BORDER | SWT.SINGLE);

		input = new StyledText( container, SWT.BORDER | SWT.SINGLE);

		Button reset = new Button( container, SWT.PUSH);
		reset.setText( "Reset");
		reset.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent theE) {
				container.update();
			}
		});

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
}
