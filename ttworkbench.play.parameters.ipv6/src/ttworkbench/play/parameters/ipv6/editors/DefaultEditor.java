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
package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.integer.IntegerEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class DefaultEditor extends AbstractEditor<Object> {

	private static final String TITLE = "Default Editor";
	private static final String DESCRIPTION = "";
	
	private Text text; 
	
	public DefaultEditor() {
		super( TITLE, DESCRIPTION);
	}
	

	@Override
	protected void designControl( Composite theControl) {
		IParameter<Object> parameter = getParameter();
		

		theControl.setLayout(new GridLayout(2, false));
		Label label = new Label(theControl, SWT.NONE);
		label.setText( parameter.getName());
		label.setToolTipText( parameter.getDescription());
		
		text = new Text(theControl, SWT.READ_ONLY | SWT.BORDER);
		text.setText( ParameterValueUtil.getValue( parameter));
		
		text.setToolTipText( parameter.getType());
	}
	

	@Override
	protected IEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}


	@Override
	public void reloadParameter() {
		if ( hasControl())
		  text.setText( ParameterValueUtil.getValue( getParameter()));
	}

	

}
