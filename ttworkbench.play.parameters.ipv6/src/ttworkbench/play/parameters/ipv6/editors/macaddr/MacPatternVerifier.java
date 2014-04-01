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

import ttworkbench.play.parameters.ipv6.editors.verification.RegexVerifier;

public class MacPatternVerifier extends RegexVerifier{

	private static final String MAC_PATTERN1 = "^(?:[a-fA-F0-9]{2}([-:]))(?:[a-fA-F0-9]{2}\\1){4}[a-fA-F0-9]{2}$";
	

	@Override
	protected String regex() {
		// TODO Auto-generated method stub
		return MAC_PATTERN1;
	}
	@Override
	protected String validMessageText(String theInput) {
		// TODO Auto-generated method stub
		return "This is a valid MAC-Address";
	}
	@Override
	protected String notValidMessageText(String theInput) {
		// TODO Auto-generated method stub
		return String.format( "\"%s\" is not a valid MAC-Address.", theInput);
	}
	@Override
	protected String helpValue() {
		// TODO Auto-generated method stub
		return "MAC-Address";
	}

}
