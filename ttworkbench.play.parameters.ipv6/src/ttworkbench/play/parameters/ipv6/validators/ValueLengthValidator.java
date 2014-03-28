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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class ValueLengthValidator extends ContextualValidator {
	public ValueLengthValidator() {
		super("Parameter Value Length Validator", "Checks if the value has the right length");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		BigInteger bigValue = ((IntegerValue) getContext().getParameters()[0].getValue()).getTheNumber(); 
		int value = bigValue.intValue();
		
		String content = ParameterValueUtil.getValue( parameter);
		boolean correctLength = content.length()==value; 
		
    validationResults.add( new ValidationResultMessage(
    		
  		correctLength
  			? String.format( "%s: This entry of %s doesn't have the correct length. \"%s\" doesn't contain %s characters.",
  					this.getTitle(),
  					parameter.getName(),
  					content,
  					value)
  			: String.format( "%s: This entry of %s has the correct length.", this.getTitle(), parameter.getName()),
  			
  			correctLength
  			? ErrorKind.success
  			: ErrorKind.error,
  			
  		theClient,
  		
  		"tag_is_correct_length"));
		
    	
    return validationResults;
	}

}

