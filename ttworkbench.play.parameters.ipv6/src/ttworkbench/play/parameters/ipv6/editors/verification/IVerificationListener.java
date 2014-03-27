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

public interface IVerificationListener<T> {

	/**
	 * Hook, that will be executed once, before a verification.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 */
	void beforeVerification(VerificationEvent<T> theEvent);

	/**
	 * Hook, that will be executed, after verification of one verifier. If their
	 * are more then one verifier to be processed.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 * 
	 * @see IVerifier
	 */
	void afterVerificationStep(VerificationEvent<T> theEvent);

	/**
	 * Hook, that will be executed once, after all verifications.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 */
	void afterVerification(VerificationEvent<T> theEvent);

}
