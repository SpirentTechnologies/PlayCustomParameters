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


/**
 * This file is part of the prim editor example
 */
package ttworkbench.play.parameters.ipv6.editors.prim;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.IntegerValue;

public class PrimEditor extends VerifyingEditor<Text, IntegerValue>{

	private static final String TITLE = "Primzahl Editor";
	private static final String DESCRIPTION = "Editor zur Eingabe von Primzahlen.";
	
	//private IValidatingEditorLookAndBehaviour lookAndBehaviour = null;
	
	private IVerifier<String> primVerifier = new PrimVerifier();
	
	public PrimEditor() {
		super( TITLE, DESCRIPTION);
	}

	private IValidatingEditorLookAndBehaviour lookAndBehaviour = null;
	
	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		if ( lookAndBehaviour == null)
			lookAndBehaviour = new PrimEditorLookAndBehaviour();
		return lookAndBehaviour;
	}

	@Override
	protected void createEditRow(Composite theContainer) {
		// get layout data for each cell
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		
		createParameternameLabel( theContainer, layoutData[0]);
		
		createInputControl( theContainer, layoutData[1]);
		
		createDefaultButton( theContainer, layoutData[2]);
		
		theContainer.pack();
	}


	private void createParameternameLabel( Composite theComposite, Object theLayoutData) {
		// create label for parameter name 
		CLabel label = new CLabel( theComposite, SWT.LEFT);
		label.setText( this.getParameter().getId());
		label.setLayoutData( theLayoutData);

		// set a tooltip to the label
		String toolTipString = this.getParameter().getName() + ":\n" 
			                 	 + this.getParameter().getDescription();
		label.setToolTipText( toolTipString);
	}
	
	private void createInputControl( Composite theComposite, Object theLayoutData) {
		IVerifyingControl<Text, IntegerValue, String> inputControl = 
				new VerifyingText<IntegerValue>( getParameter(), 
																				 theComposite, 
																				 SWT.BORDER | SWT.SINGLE, 
																				 "2",  // default value if input is empty
																				 primVerifier 
																			 );
		setInputControl( inputControl);
		
		setVerifyListenerToControl( inputControl);
		
		Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
	}

	private void setVerifyListenerToControl( final IVerifyingControl<Text, IntegerValue, String> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {

			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}

			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				// retrieve the last verification result
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
			
				if ( !lastResult.verified) {
					getMessageView().showMessage( lastResult.messages.get( 0));
					getMessageView().flashMessage( lastResult.messages.get( 1));
					theEvent.skipVerification = true;
					theEvent.doit = true;
				} else {
					getMessageView().clearMessagesByTag( lastResult.messages.get( 0).tag);
					theEvent.doit = true;
				}
			}

			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {	
				// verification passed, then write the value to parameter
				forceParameterValue( theEvent.inputToVerify);
				// and start the validation process
				validateDelayed( theInputControl);
				theEvent.doit = true;
			}


		});
	}

	private void createDefaultButton( Composite theComposite, Object theLayoutData) {
    // create button to load default parameter value 
		Button defaultValueButton = new Button (theComposite, SWT.PUSH);
		defaultValueButton.setText ("Default");
		defaultValueButton.setLayoutData( theLayoutData);
		defaultValueButton.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent theEvent) {
				String defaultValueString = ParameterValueUtil.getDefaultValue( getParameter());
				// set the value of the input control,
				// that may start the verification process 
				setInputValue( defaultValueString);
				super.widgetSelected( theEvent);
			}
		});

	}
	
}
