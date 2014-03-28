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

import ttworkbench.play.parameters.ipv6.validators.FibValidator.ParameterKey;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class MacAddressValidator extends ContextualValidator {
	
	private static final String TITLE = "MAC Address Validator";
	private static final String DESCRIPTION = "";
	
	public MacAddressValidator() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		String mac1 = this.<StringValue>getParameter( 0).getValue().getTheContent();
		String mac2 = this.<StringValue>getParameter( 1).getValue().getTheContent();
		
		if(mac1.equalsIgnoreCase( mac2)){
			validationResults.add( new ValidationResultMessage(  "This configuration is not valid.", ErrorKind.error, theClient, "tag_val_mac"));
			validationResults.add( new ValidationResultMessage(  "The MAC-Addresses cannot have the same value", ErrorKind.info, theClient, "tag_val_mac_hint"));
		}else{
			validationResults.add( new ValidationResultMessage(  "This configuration is valid.", ErrorKind.success, theClient, "tag_val_mac"));
		}
		return validationResults;
	}

}
