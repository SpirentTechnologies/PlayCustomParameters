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
package ttworkbench.play.parameters.ipv6.composer;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.enums.EnumEditor;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.widgets.EnumWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class EnumWidgetComposer extends WidgetComposer{
	
	//get relevant parameters
	final IParameter<String> parameter_MAC_FILTER = getParametersMap().getParameterById( "PC_MAC_FILTER");

	public EnumWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// TODO Auto-generated method stub
			// declare a Enum Address Widget
			IWidget enumWidget = new EnumWidget();
			// add the Enum widget to the frame work
			getConfigurator().addWidget( enumWidget);

			ValidatingEditor<?> editor_MAC_FILTER = new EnumEditor();

			// The Enum validator
			IParameterValidator enumValidator = new AbstractValidator( "Enum Validator", ""){
				@Override
				protected List<ValidationResult> validateParameter(IParameter parameter, Object theClient) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				String theValue = ( (StringValue) parameter.getValue()).getTheContent();
				System.out.println( "this is my Enum parameter value:  " + theValue);
				validationResults.add( new ValidationResultMessage( "This entry is valid.", ErrorKind.success, "tag_is_enum"));
				return validationResults;
			}

		};
		
			// assign each parameter to the corresponding editor in this widget
			getConfigurator().assign( editor_MAC_FILTER, enumWidget, parameter_MAC_FILTER);

			// Register the Enum validator to the editor
			enumValidator.registerForMessages( editor_MAC_FILTER);

			// assign the validator to the parameter
			getConfigurator().assign( enumValidator, enumWidget, parameter_MAC_FILTER);
	}

}
