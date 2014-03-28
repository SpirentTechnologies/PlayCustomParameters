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
package ttworkbench.play.parameters.ipv6.editors.ip;

import java.beans.EventHandler;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.RowEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.OrVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.StringValue;

public class IPEditor extends VerifyingEditor<Text, StringValue> {

	private static final String TITLE = "IP-Editor";
	private static final String DESCRIPTION = "";

	final Display display = Display.getCurrent();

	private IVerifier<String> ALLVERIFIERS = new OrVerifier( new IPv4Verifier(), new IPv6Verifier(), new HostnameVerifier());
	private IVerifier<String> verifier;
	
	private CLabel label;
	private Text text;
	private EventHandler handler; // not a generic Handler
	
	private static final int IP_LENGTH = 15;

	public IPEditor() {
		super( TITLE, DESCRIPTION);
	}

	public IPEditor( final IVerifier<String> verifier) {
		this();
		this.verifier = verifier;
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new RowEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {

	// Hartcodierte Verifier für die IP-Adressen aus der XML-Datei übernehmen
			if (this.getAttribute( "verifiers") != null) {
				List<IVerifier<String>> vs = new LinkedList<IVerifier<String>>();
				for (String s : this.getAttribute( "verifiers").split( ",")) {
					if (s.equalsIgnoreCase( "IPv4")) {
						vs.add( new IPv4Verifier());
					} else if (s.equalsIgnoreCase( "IPv6")) {
						vs.add( new IPv6Verifier());
					} else if (s.equalsIgnoreCase( "Hostname")) {
						vs.add( new HostnameVerifier());
					}
				}

				if (!vs.isEmpty()) {
					this.verifier = new OrVerifier( vs.toArray( new IVerifier[vs.size()]));
				} else {
					this.verifier = this.ALLVERIFIERS;
				}
			}

		
		label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName());

		IVerifyingControl<Text,StringValue> inputControl = new VerifyingText<StringValue>( getParameter(), theContainer, SWT.BORDER | SWT.SINGLE);
		inputControl.addVerifierToEvent( verifier, SWT.Verify);
		setInputControl(inputControl);		
		setVerifyerListenerToControl(inputControl);

		// bad solution, but functional
		text = (Text) inputControl.getControl();
		// must be done after Textinitialisation, because of dependences.
		text.setToolTipText( getParameter().getDescription());
		text.setTextLimit( IP_LENGTH);

		// set the Default Parameter Value
		inputControl.forceText( getParameter().getDefaultValue().getTheContent());
		
	}

	private void setVerifyerListenerToControl(final IVerifyingControl<?, StringValue> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {

			@Override
			public void beforeVerification(VerificationEvent<String> theEvent) {}

			@Override
			public void afterVerificationStep(VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size()-1);
				if (!lastResult.verified) {
					theEvent.doit = true;
					theEvent.skipVerification = true;
				}
				getMessageView().flashMessages( lastResult.messages);
				
			}

			@Override
			public void afterVerification(VerificationEvent<String> theEvent) {
				forceParameterValue(theEvent.inputToVerify);
				validateDelayed(theInputControl);
				theEvent.doit = true;				
			}			
		});		
	}
}
