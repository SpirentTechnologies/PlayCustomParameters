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
package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultAction;

public class VisibilityToggleValidator extends ContextualValidator {

	public VisibilityToggleValidator() {
		super("Visibility Toggle Validator", "");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		
		String pattern = getAttribute( "pattern");
		String value = String.valueOf( parameter.getValue());

		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		
		final boolean visible = value.matches( pattern);
		String tag = "tag_visible_hint_"+visible;
		
		validationResults.add( new ValidationResultAction( String.format( "%s: visibility="+visible, this.getTitle()), ErrorKind.info, theClient, tag) {

			@Override
			public void triggerEditor(IParameterEditor<?> theEditor) {
				theEditor.setVisible( visible);
			}

			@Override
			public void triggerWidget(IWidget theWidget) {
				theWidget.setVisible( visible);
			}
			
		});

		return validationResults;
	}	

}
