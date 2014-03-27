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
package ttworkbench.play.parameters.ipv6.widgets.grid;

import java.util.Collection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class WidgetGridControl {

	private Composite parent;
	private Composite composite;
	private Collection<IParameterEditor<?>> editors;


	public WidgetGridControl(Composite parent) {
		this.parent = parent;
	}

	public Composite getComposite() {
		if(composite==null) {
			createComposite();
		}
		return composite;
		
	}


	public WidgetGridControl setEditors(Collection<IParameterEditor<?>> editors) {
		this.editors = editors;
		return this;
	}
	
	private void createComposite() {
		composite = new Composite(parent, SWT.None);
		GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, false);
		composite.setLayout( new GridLayout( 2, false));
	    for ( IParameterEditor<?> editor : editors) {
			
		    CLabel label = new CLabel( composite, SWT.LEFT);
			label.setText( editor.getParameter().getName());
			label.setLayoutData( gridData);
			
			label = new CLabel( composite, SWT.LEFT);
			label.setText( editor.getParameter().getValue().toString());
			label.setLayoutData( gridData);
			
		}
	}
}
