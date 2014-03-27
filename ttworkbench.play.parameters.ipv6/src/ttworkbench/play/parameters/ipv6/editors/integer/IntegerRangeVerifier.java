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
package ttworkbench.play.parameters.ipv6.editors.integer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class IntegerRangeVerifier implements IVerifier<String> {

	final IntegerType integerType;
	
	public IntegerRangeVerifier( final IntegerType theIntegerType) {
		super();
		this.integerType = theIntegerType;
	}
	
	private boolean isValueInRange( final String theValue, final IntegerType theIntegerType) {
		if ( theValue == null)
			return false;

		BigInteger value; 
		try {
			value = new BigInteger( theValue);
		} catch ( NumberFormatException e) {
			return false;
		}
		
		
		BigInteger minValue = theIntegerType.getMinValue();
		BigInteger maxValue = theIntegerType.getMaxValue();
		
		if ( minValue == null && maxValue == null)
			return true;

		if ( minValue != null && maxValue != null)
			return ( minValue.compareTo( value) <= 0) &&
	    			 ( maxValue.compareTo( value) >= 0);

		if ( minValue == null && maxValue != null)
			return maxValue.compareTo( value) >= 0;

  	if ( minValue != null && maxValue == null)
		  return minValue.compareTo( value) <= 0;

		return false;
	}
	
	
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		boolean verified = isValueInRange( theInput, integerType);
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
		MessageRecord codomainInfo = new MessageRecord( "valid_chars_info", String.format( "Only integers in range [%s,%s] accepted.", integerType.getMinValue(), integerType.getMaxValue()), ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, codomainInfo); 
		return new VerificationResult<String>( this, theInput, verified, messages);
	}

	

}
