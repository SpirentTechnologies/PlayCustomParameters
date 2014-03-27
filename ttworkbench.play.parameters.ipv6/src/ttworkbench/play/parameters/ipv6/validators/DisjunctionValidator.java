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
import java.util.List;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class DisjunctionValidator extends ContextualValidator {
	public DisjunctionValidator() {
		super("Disjunction Validator", "Checks if the values are disjunct");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		IParameter<?> parA = null;
		IParameter<?> parB = null;
		boolean disjunctive = true;
		
		IParameter<?>[] paras = getContext().getParameters();
		
		for(int i=0; disjunctive && i<paras.length-1; i++) {
			for(int j=i+1; disjunctive && j<paras.length; j++) {
				disjunctive &= !compareEqual(parA = paras[i], parB = paras[j]);
			}
			disjunctive &= !compareEqual(parA = paras[i], parB = parameter);
		}
		if(disjunctive && paras.length>0) {
			disjunctive &= !compareEqual(parA = paras[paras.length-1], parB = parameter);
		}

		
    validationResults.add( new ValidationResultMessage(
    		
    		disjunctive
    			? String.format( "%s: %s and %s are NOT disjunct.", this.getTitle(), parA.getName(), parB.getName())
    			: String.format( "%s: set of %s parameters is disjunct.", this.getTitle(), paras.length+1),
    			
    		disjunctive
    			? ErrorKind.success
    			: ErrorKind.error,
    			
    		theClient,
    		
    		"tag_is_disjunct"));
    	
    return validationResults;
	}

	private boolean compareEqual(IParameter<?> parA, IParameter<?> parB) {
		return parA!=null && parB!=null && parA.getValue()!=null && parB.getValue()!=null
				&& (parA.getValue().equals( parB.getValue()) || ParameterValueUtil.getValue( parA).equals( ParameterValueUtil.getValue( parB)));
	}

}

