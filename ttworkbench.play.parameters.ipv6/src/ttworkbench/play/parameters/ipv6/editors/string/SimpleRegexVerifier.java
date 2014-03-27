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
package ttworkbench.play.parameters.ipv6.editors.string;

import java.util.Arrays;
import java.util.Locale;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

public class SimpleRegexVerifier implements IVerifier<String> {
	
	private final String regex;
	
	public SimpleRegexVerifier( final String theRegex) {
		super();
		this.regex = theRegex;
	}

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		boolean verified = theInput.matches( regex);
		
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input %s rejected.", theInput), ErrorKind.warning); 
		MessageRecord inputFormatHint = new MessageRecord( "input_info", String.format( "Only strings that matches regex \"%s\" accepted.", regex), ErrorKind.info); 
		MessageRecord inputAcceptedSuccess = new MessageRecord( "invalid_input_warning", String.format( "Input \"%s\" accepted.", theInput), ErrorKind.success); 	
		
		if ( verified) 
			return new VerificationResult<String>( this, theInput, verified, Arrays.asList( inputAcceptedSuccess));
		else
			return new VerificationResult<String>( this, theInput, verified, Arrays.asList( inputRejectedWarning, inputFormatHint));	
	}

}
