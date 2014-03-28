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

import java.util.List;

import org.eclipse.swt.widgets.Control;

import com.testingtech.muttcn.kernel.Expression;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

public interface IVerifyingControl<C extends Control, P extends Expression> extends IParameterControl<C, P> {

	/**
	 * Set the text after a successfully passed verification process under use of
	 * the given verifier.
	 * 
	 * @param theText
	 */
	void setText(String theText);

	/**
	 * Set the text, without a verification.
	 * 
	 * @param theText
	 *          unverified text, to be set.
	 */
	void forceText(String theText);

	/**
	 * Get the actual text of the control.
	 * 
	 * @return the actual text
	 */
	String getText();

	/**
	 * Return the results of a verification for informational purpose.
	 * 
	 * @return Results of the last verification.
	 */
	List<VerificationResult<String>> getVerificationResults();

	/**
	 * Set an ActionListener that will be invoked, if some actions on the Control
	 * happens.
	 * 
	 * @param theListener
	 *          the Listener to be invoked.
	 */
	void addListener(IVerificationListener<String> theListener);

	/**
	 * Add a verifier to an event. If the event is raised, the verifier will be
	 * triggered. For each event type the add order is considered. 
	 * 
	 * @param theVerifier
	 *          verifier to add for verification
	 * @param theEventType
	 *          the event, which triggers the verification
	 * 
	 * @see org.eclipse.swt.SWT
	 * @see org.eclipse.swt.widgets.Control
	 */
	void addVerifierToEvent(IVerifier<String> theVerifier, int theEventType);

}
