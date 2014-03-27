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
package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class FibValidator_ERRORS extends FibValidator {

	public FibValidator_ERRORS() {
		super("Error Counter");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {

		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		
		IParameterEditor<?> editor_PX_N = getEditors( ParameterKey.PX_N)[0];
		IParameterEditor<?> editor_PX_FIB_NUMBER = getEditors( ParameterKey.PX_FIB_NUMBER)[0];
		IParameterEditor<?> editor_PX_FIB_SUCC_NUMBER = getEditors( ParameterKey.PX_FIB_SUCC_NUMBER)[0];
		
		int totalErrors = 0;
		int totalWarnings = 0;
		IMessageView<?> messagePanel_PX_N = ((ValidatingEditor<?>) editor_PX_N).getMessageView();
	  totalErrors += messagePanel_PX_N.getMessages( EnumSet.of( ErrorKind.error)).size();
	  totalWarnings += messagePanel_PX_N.getMessages( EnumSet.of( ErrorKind.warning)).size();
	  IMessageView<?> messagePanel_PX_FIB_NUMBER = ((ValidatingEditor<?>) editor_PX_FIB_NUMBER).getMessageView();
	  totalErrors += messagePanel_PX_FIB_NUMBER.getMessages( EnumSet.of( ErrorKind.error)).size();
	  totalWarnings += messagePanel_PX_FIB_NUMBER.getMessages( EnumSet.of( ErrorKind.warning)).size();
	  IMessageView<?> messagePanel_PX_FIB_SUCC_NUMBER = ((ValidatingEditor<?>) editor_PX_FIB_SUCC_NUMBER).getMessageView();
	  totalErrors += messagePanel_PX_FIB_SUCC_NUMBER.getMessages( EnumSet.of( ErrorKind.error)).size();
	  totalWarnings += messagePanel_PX_FIB_SUCC_NUMBER.getMessages( EnumSet.of( ErrorKind.warning)).size();
	  
		if ( totalErrors > 0) {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: %s errors.", this.getTitle(), totalErrors), ErrorKind.error, theClient, "tag_total_errors"));
		} else {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: No more errors.", this.getTitle()), ErrorKind.success, theClient, "tag_total_errors"));
		}
		
		if ( totalWarnings > 0) {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: %s warnings.", this.getTitle(), totalErrors), ErrorKind.warning, theClient, "tag_total_warnings"));
		} else {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: No more warnings.", this.getTitle()), ErrorKind.success, theClient, "tag_total_warnings"));
		}
		
		return validationResults;
	}
}
