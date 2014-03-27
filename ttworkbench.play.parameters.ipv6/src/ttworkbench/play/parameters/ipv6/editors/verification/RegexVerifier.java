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
package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public abstract class RegexVerifier implements IVerifier<String> {

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		List<MessageRecord> messages;
		boolean verified;
		String tag = "validity";

		verified = theInput.matches( regex());
		if (verified) {
			messages = Arrays.asList( new MessageRecord( tag, validMessageText( theInput), ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( tag, notValidMessageText( theInput), ErrorKind.warning));
		}
		return new VerificationResult<String>( this, theInput, verified, messages);
	}

	public String toString() {
		return helpValue();
	}

	protected abstract String regex();

	protected abstract String validMessageText(final String input);

	protected abstract String notValidMessageText(final String input);

	protected abstract String helpValue();

}
