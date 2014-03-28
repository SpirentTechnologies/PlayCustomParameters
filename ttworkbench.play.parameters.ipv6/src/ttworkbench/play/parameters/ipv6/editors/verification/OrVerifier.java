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
package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class OrVerifier implements IVerifier<String> {

	Set<IVerifier<String>> verifier;

	public OrVerifier( final IVerifier<String>... verifier) {
		this.verifier = new HashSet<IVerifier<String>>( Arrays.asList( verifier));
	}

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		List<MessageRecord> messages = new LinkedList<MessageRecord>();
		boolean verified = false;
		IVerifier<String> validVerifier = null;

		for (IVerifier<String> v : verifier) {
			VerificationResult<String> result = v.verify( theInput, theParams);
			verified |= result.verified;
			if (verified) {
				validVerifier = v;
				break;
			}
		}

		if (verified) {
			messages = Arrays
					.asList( new MessageRecord( "validInput", "valid " + validVerifier.toString(), ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( "validInput", String.format( "\"%s\" is not a valid ", theInput)
					+ this.toString(), ErrorKind.warning));
		}
		return new VerificationResult<String>( this, theInput, verified, messages);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (IVerifier<String> v : verifier) {
			if (!first) {
				buffer.append( " or ");
			}
			buffer.append( v.toString());
			first = false;
		}
		return buffer.toString();

	}
}
