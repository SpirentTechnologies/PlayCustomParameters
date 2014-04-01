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
package ttworkbench.play.parameters.ipv6.editors.octet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class OctetTypeVerifier implements IVerifier<String> {

//	// allow negative values 
//	private static final String REGEX_OCTAL = "^(-|\\+)?0([0-7]+)$";
//	private static final String REGEX_DECIMAL = "^(-|\\+)?([1-9][0-9]*)$";
//	private static final String REGEX_HEXADECIMAL = "^(-|\\+)?([0-9A-Fa-f]+)$";
//	private static final String REGEX_HEX_0x = "(-|\\+)?0[x|X]([0-9A-Fa-f]+)$";
//	private static final String REGEX_HEX_h = "^(-|\\+)?([0-9A-Fa-f]+)h$";
//	private static final String REGEX_HEX_SHARP = "^(-|\\+)?#([0-9A-Fa-f]+)$";
//	private static final String REGEX_HEX_STRING = "^'(-|\\+)?([0-9A-Fa-f]+)'O$";
//
	private static final String REGEX_OCTAL = "^0([0-7]+)$";
	private static final String REGEX_DECIMAL = "^([1-9][0-9]*)$";
	private static final String REGEX_HEXADECIMAL = "^([0-9A-Fa-f]+)$";
	private static final String REGEX_HEX_0x = "0[x|X]([0-9A-Fa-f]+)$";
	private static final String REGEX_HEX_h = "^([0-9A-Fa-f]+)h$";
	private static final String REGEX_HEX_SHARP = "^#([0-9A-Fa-f]+)$";
	private static final String REGEX_HEX_STRING = "^'([0-9A-Fa-f]+)'O$";
	
	private static String extract( final String theValue, final String thePattern) {
		Pattern pattern = Pattern.compile( thePattern);
    Matcher matcher = pattern.matcher( theValue);
    if (matcher.find() &&
      matcher.groupCount() > 0) {
    	return matcher.group( 1);
    }
    return null;
	}
	
	private static BigInteger extractOctalOctet( String theValue) {
		String valueString = extract( theValue, REGEX_OCTAL);
		if ( valueString == null)
			return null;
		return new BigInteger( valueString, 8);
	}

	private static BigInteger extractDecimalOctet( String theValue) {
		String valueString = extract( theValue, REGEX_DECIMAL);
		if ( valueString == null)
			return null;
		return new BigInteger( valueString);
	}
	
	private static BigInteger extractHexOctet( String theValue) {
		String valueString = extract( theValue, REGEX_HEXADECIMAL);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_0x);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_h);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_SHARP);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_STRING);		
		if ( valueString == null)
			return null;
		return new BigInteger( valueString, 16);
	}
	
	private static BigInteger extractOctet( String theValue) {
		BigInteger bigInteger = extractOctalOctet( theValue);
		if ( bigInteger == null)
			bigInteger = extractDecimalOctet( theValue);
		if ( bigInteger == null)
			bigInteger = extractHexOctet( theValue);
		if ( bigInteger == null)
			return null;
		return bigInteger;
	}


	
	
	
	@Override
	public VerificationResult<String> verify( String theInput, Object... theParams) {
		BigInteger integerRepresentation = extractOctet( theInput);
	 
		boolean typeVerified = integerRepresentation != null;
	
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input \"%s\" violates format.", theInput), ErrorKind.warning); 
		MessageRecord acceptedValuesInfo = new MessageRecord( "valid_chars_info", "Valid notations of 8 are e.g. 010, 8, 8h, #8, 0x8, '8'O .", ErrorKind.info); 
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, acceptedValuesInfo); 
		return new VerificationResult<String>( this, theInput, integerRepresentation, typeVerified, messages);
	}
}