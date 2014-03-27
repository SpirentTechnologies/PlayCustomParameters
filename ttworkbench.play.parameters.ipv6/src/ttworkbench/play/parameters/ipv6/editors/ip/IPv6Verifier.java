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
package ttworkbench.play.parameters.ipv6.editors.ip;

import ttworkbench.play.parameters.ipv6.editors.verification.RegexVerifier;

public class IPv6Verifier extends RegexVerifier {

	/* source: http://stackoverflow.com/questions/53497 */
	final String REGEX = "("
			+
			// 1:2:3:4:5:6:7:8
			"([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|"
			+
			// 1:: 1:2:3:4:5:6:7::
			"([0-9a-fA-F]{1,4}:){1,7}:|"
			+
			// 1::8 1:2:3:4:5:6::8 1:2:3:4:5:6::8
			"([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|"
			+
			// 1::7:8 1:2:3:4:5::7:8 1:2:3:4:5::8
			"([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|"
			+
			// 1::6:7:8 1:2:3:4::6:7:8 1:2:3:4::8
			"([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|"
			+
			// 1::5:6:7:8 1:2:3::5:6:7:8 1:2:3::8
			"([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|"
			+
			// 1::4:5:6:7:8 1:2::4:5:6:7:8 1:2::8
			"([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|"
			+
			// 1::3:4:5:6:7:8 1::3:4:5:6:7:8 1::8
			"[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|"
			+
			// ::2:3:4:5:6:7:8 ::2:3:4:5:6:7:8 ::8 ::
			":((:[0-9a-fA-F]{1,4}){1,7}|:)|"
			+
			// fe80::7:8%eth0 fe80::7:8%1 (link-local IPv6 addresses with zone index)
			"e80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|"
			+ "::(ffff(:0{1,4}){0,1}:){0,1} "
			+ "((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3} "
			+
			// ::255.255.255.255 ::ffff:255.255.255.255 ::ffff:0:255.255.255.255
			// (IPv4-mapped IPv6 addresses and IPv4-translated addresses)
			"(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|" + "([0-9a-fA-F]{1,4}:){1,4}:"
			+ "((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}" +
			// 2001:db8:3:4::192.0.2.33 64:ff9b::192.0.2.33 (IPv4-Embedded IPv6
			// Address)
			"(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])" + ")";

	@Override
	protected String regex() {
		return REGEX;
	}

	@Override
	protected String validMessageText(String theInput) {
		return "valid IPv6-Address.";
	}

	@Override
	protected String notValidMessageText(String theInput) {
		return String.format( "\"%s\" is not a valid IPv6-Address.", theInput);
	}

	@Override
	protected String helpValue() {
		return "IPv6-Address";
	}

}
