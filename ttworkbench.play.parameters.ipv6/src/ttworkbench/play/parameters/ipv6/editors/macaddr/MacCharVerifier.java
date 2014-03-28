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
package ttworkbench.play.parameters.ipv6.editors.macaddr;

import java.util.Arrays;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

public class MacCharVerifier implements IVerifier<String> {

	private static final String VALID_CHAR_PATTERN = "^([0-9a-fA-F:-])*$";
	
	private boolean isValidChar(String theInput){
		if(theInput.matches( VALID_CHAR_PATTERN)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		// TODO Auto-generated method stub
		boolean isValidChar = isValidChar(theInput);
		
		MessageRecord invalidCharWarning = new MessageRecord("invalid_char_warning", "The character is invalid", ErrorKind.warning);
		MessageRecord validCharInfo = new MessageRecord("valid_char_info", "the characters allowed are Alphanumerical (0-9, a-f), \":\" and \"-\"", ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( invalidCharWarning, validCharInfo);
		return new VerificationResult<String>( this, theInput, isValidChar, messages);
	}

}
