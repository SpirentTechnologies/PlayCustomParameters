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
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/**
 * Verify only small octets.
 * TODO for higher efficiency and precision impl square root algo with Newton's method
 * or Karps trick.  
 * 
 *
 */
public class OctetRangeVerifier implements IVerifier<String> {

	final OctetType octetType;


	public OctetRangeVerifier( final OctetType theOctetType) {
		super();
		this.octetType = theOctetType;
	}


	private static Long valueToOctets( BigInteger theValue) {
		if ( theValue == null || theValue.compareTo( new BigInteger( "0")) <= 0)
			return 0L;

		double valueLn = Math.log( theValue.doubleValue()) / Math.log(2);
		long valueOctets = (long) Math.floor( valueLn / 8.0) +1;
		return valueOctets;
	}

	private boolean isOctetPresentable( final Long theValueOctets, final Long theMaxAcceptedOctets) {
		if ( theMaxAcceptedOctets == null)
			return true;
		return theMaxAcceptedOctets.compareTo( theValueOctets) >= 0;
	}

	private String topOffValueWithZeros( final Long theValueOctets, final BigInteger theIntegerRepresentation, final Long theMinExpectedOctets) {
		return StringUtils.repeat( "0", theMinExpectedOctets.intValue() -theValueOctets.intValue()) + theIntegerRepresentation.toString( 16);
	}





	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		BigInteger integerRepresentation = (BigInteger) theParams[0];

		Long valueOctets = valueToOctets( integerRepresentation);
		Long minOctets = octetType.getMinOctets();
		Long maxOctets = octetType.getMaxOctets();

		boolean rangeVerified = isOctetPresentable( valueOctets, maxOctets);

		String zeroFilledInput = topOffValueWithZeros( valueOctets, integerRepresentation, minOctets);

		String infty = DecimalFormatSymbols.getInstance().getInfinity();
		String minBound = octetType.getMinOctets() == null ? "0" : octetType.getMinOctets().toString();
		String maxBound = octetType.getMaxOctets() == null ? infty : octetType.getMaxOctets().toString();

		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input \"%s\" violates range.", theInput), ErrorKind.warning); 
		MessageRecord codomainInfo = new MessageRecord( "valid_chars_info", String.format( "Only values in range of [%s,%s] octets accepted.", minBound, maxBound), ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, codomainInfo); 
		return new VerificationResult<String>( this, theInput, zeroFilledInput, rangeVerified, messages);
	}
}