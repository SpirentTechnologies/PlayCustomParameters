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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.widgets.tableviewer.ParameterEditorColumnType;
import ttworkbench.play.parameters.ipv6.widgets.tableviewer.WidgetTableViewerAdvancedControl;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class DefaultWidget extends AbstractWidget {

	private static final String TITLE = "Default Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;

	public DefaultWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void designControl(Composite theControl) {

		theControl.setLayout( new FillLayout( SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite( theControl, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal( true);
		scrolledComposite.setExpandVertical( true);

		/* deprecated
		Composite composite = new WidgetGrid( scrolledComposite)
			.setEditors( getEditors())
			.getComposite();
		*/
		
		
		Composite composite = new WidgetTableViewerAdvancedControl(scrolledComposite)
		
		
			.addParameterEditorHolders("All parameters", getEditors().toArray( new IParameterEditor<?>[0] ))
			
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_NAME, "Parameter Id")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_VALUE, "Value")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_DEFAULT, "Default")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_TYPE, "Type")
			.addTableColumn(ParameterEditorColumnType.COLUMN_PARAMETER_DESCRIPTION, "Description")
			
			.getComposite();
		

		scrolledComposite.setContent( composite);
	}

	


}
