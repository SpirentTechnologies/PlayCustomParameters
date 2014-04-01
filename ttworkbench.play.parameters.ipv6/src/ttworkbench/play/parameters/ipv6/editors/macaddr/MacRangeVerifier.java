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

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacRangeVerifier implements IVerifier<String> {

	private static final int MAC_LENGTH = 17;
	
	private boolean isInRange(String theInput){
		if(theInput.length() <= MAC_LENGTH){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		// TODO Auto-generated method stub
		boolean isInRange = isInRange(theInput);
		
		MessageRecord invalidRangeWarning = new MessageRecord("invalid_range_warning", "the maximum range of the MAC is reached", ErrorKind.warning);
		MessageRecord validRangeInfo = new MessageRecord("valid_range_info", "MAC range is 17 characters at most", ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( invalidRangeWarning, validRangeInfo);
		
		return new VerificationResult<String>( this, theInput, isInRange, messages);
	}

	
}
