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
package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class CellParameterEditorDialog extends Dialog {

	private IParameterEditor<Object> editor;

	public CellParameterEditorDialog( Shell parentShell, IParameterEditor<Object> editor) {
		super( parentShell);
		this.editor = editor;
	}

	@Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText(editor.getTitle());
 }

	@Override
	protected Control createDialogArea(Composite parent) {
		if (parent instanceof Shell) {
			((Shell) parent).setText( editor.getTitle());
		}
		
		Composite container = (Composite) super.createDialogArea( parent);
		editor.createControl( container);
		container.setSize( container.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		container.layout();
		
		return container;
	}
	
	@Override
	protected Button createButton(Composite theParent, int theId, String theLabel, boolean theDefaultButton) {
		if(theId==0)
			return super.createButton( theParent, theId, "Close", theDefaultButton);
		return null;
	}
}
