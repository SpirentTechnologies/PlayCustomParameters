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

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Verifiers {
	
	private static final Map<Class<?>, IVerifier<?>> verifiers = new HashMap<Class<?>, IVerifier<?>>();
	
	private static < T extends IVerifier<?>> T createVerifier( Class<T> theClass) {
  	try {
  		return theClass.newInstance();
  	} catch ( Exception e) {
  		return null;
  	}
	}
	
	
	@SuppressWarnings("unchecked")
	public static < T extends IVerifier<?>> T getVerifier( Class<T> theClass) {
  	if ( verifiers.containsKey( theClass))
  		return (T) verifiers.get( theClass);
  	
  	return newVerifier( theClass);
  }

  @SuppressWarnings("unchecked")
	public static < T extends IVerifier<?>> T newVerifier( Class<T> theClass) {
  	IVerifier<?> verifier = createVerifier( theClass);
  	if ( !verifiers.containsKey( theClass))
  		verifiers.put( theClass, verifier);
  	return (T) verifier;
  }

}
