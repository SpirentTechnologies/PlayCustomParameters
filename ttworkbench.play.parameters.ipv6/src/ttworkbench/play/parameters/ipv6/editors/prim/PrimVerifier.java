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

/**
 * This file is part of the prim editor example
 */
package ttworkbench.play.parameters.ipv6.editors.prim;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class PrimVerifier implements IVerifier<String> {

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		boolean verified = isPrim( theInput);

		MessageRecord inputRejectedWarning = new MessageRecord( 
				"invalid_input_warning", 
				String.format( "Input \"%s\" not acceptable.", theInput), 
				ErrorKind.warning); 
		MessageRecord acceptedValuesInfo = new MessageRecord( 
				"valid_chars_info", 
				"Only prim numbers accepted.", 
				ErrorKind.info); 
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, 
				                                          acceptedValuesInfo); 
		return new VerificationResult<String>( this, theInput, verified, messages);
	}

	private boolean isPrim(String theInput) {
		try {
			BigInteger n = new BigInteger( theInput);
			BigInteger int1 = new BigInteger( "1");
			BigInteger int2 = new BigInteger( "2");
			
			if ( n.compareTo( int1) <= 0)
				return false;
			
			if ( n.compareTo( int2) == 0)
				return true;
			
			BigInteger base = int2;
			BigInteger p = n.subtract( int1); 
			BigInteger result = base.modPow( p, n);
			return result.compareTo( int1) == 0;
		} catch (Exception e) {
			return false;
		}
	}

}
