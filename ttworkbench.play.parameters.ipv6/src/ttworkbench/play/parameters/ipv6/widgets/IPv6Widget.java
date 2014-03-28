/*******************************************************************************
 * Copyright (c)  2014 Johannes Dahlke, Thomas Büttner, Alexander Dümont, Fares Mokrani
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
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
 * Contributors: Johannes Dahlke, Thomas Büttner, Alexander Dümont, Fares Mokrani
 ******************************************************************************/
package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;


public class IPv6Widget extends AbstractWidget {

	private static final String TITLE = "IPv6 Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;
	
	private Composite editorsContainer;
	private ScrolledComposite scrolledComposite;
	
	public IPv6Widget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}
	
	@Override
	public void update() {
	    // TODO better add editors incremental
		deleteParameterEditors();
		createParameterEditors();
		
	}
	
	private void deleteParameterEditors() {
		if ( editorsContainer != null) {	
		 Control[] controls = editorsContainer.getChildren();
			for (Control control : controls) {
			  control.dispose();	
			}
		}
	}

	private void createParameterEditors() {
		//GridData gridData = new GridData( SWT.BEGINNING, SWT.FILL, true, true);
		//GridLayout gridLayout = new GridLayout( 1, false);
		//gridLayout.makeColumnsEqualWidth = false;
		if ( editorsContainer != null) {	
			List<IParameterEditor<?>> editors = getEditors();
			for ( IParameterEditor<?> editor : editors) {
				Control editorControl = editor.createControl( editorsContainer/*, gridData, gridLayout*/);
				editorControl.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
				
				// react on dynamically insertion/deletion of controls when messages occur
				if ( editor instanceof AbstractEditor<?>)
					((AbstractEditor<?>) editor).getLookAndBehaviour().addControlChangedListener( new Listener() {
						
						@Override
						public void handleEvent(Event theArg0) {
							scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
							scrolledComposite.layout( true, true);
						}
					});
			}
			editorsContainer.setSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
			editorsContainer.layout();
		}
	}

	
	@Override
	protected void designControl(Composite theControl) {
		
		theControl.setLayout( new FillLayout(SWT.HORIZONTAL));

	  scrolledComposite = new ScrolledComposite( theControl, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout( new FillLayout( SWT.HORIZONTAL));
		scrolledComposite.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		editorsContainer = new Composite( scrolledComposite, SWT.None);
		editorsContainer.setLayout( new GridLayout( 1, true));
		editorsContainer.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
		
		createParameterEditors();
		
		scrolledComposite.setContent( editorsContainer);
		scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
	}


}
