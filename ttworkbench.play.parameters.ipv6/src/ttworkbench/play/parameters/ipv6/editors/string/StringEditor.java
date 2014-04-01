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
package ttworkbench.play.parameters.ipv6.editors.string;

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
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.CharStringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class StringEditor extends VerifyingEditor<Text,CharStringValue> {
	
	private static final String TITLE = "String Editor";
	private static final String DESCRIPTION = "";
	
	private StringType stringType = StringType.STRING;
	
	private String regex;
	
	private SimpleRegexVerifier regexVerifier;
	private StringRangeVerifier stringRangeVerifier;
	
	public StringEditor() {
		this( ".*");
	}
	
	public StringEditor( final String theRegex) {
		super( TITLE, DESCRIPTION);
		this.regex = theRegex;
		regexVerifier = new SimpleRegexVerifier( regex);
	}
	
	
	@Override
	public void setParameter(IParameter<CharStringValue> theParameter) {
		super.setParameter( theParameter);
		determineOctetType();
	}
	
	private void determineOctetType() { 
		String parameterType = getParameter().getType();
		stringType = StringType.valueOfTypeName( parameterType);
		stringRangeVerifier = new StringRangeVerifier( stringType);
	}
	
	
	@Override
	public void setAttribute(String theName, String theValue) {
		if ( theName.equalsIgnoreCase( "regex")) {
			regex = theValue.isEmpty() ? ".*" : theValue;
			regexVerifier = new SimpleRegexVerifier( regex);
		}
		super.setAttribute( theName, theValue);
	}

	
	private void createInputWidget( Composite theComposite, Object theLayoutData) {
		IVerifyingControl<Text, CharStringValue, String> inputControl = new VerifyingText<CharStringValue>( getParameter(), theComposite, SWT.BORDER | SWT.SINGLE, regexVerifier, stringRangeVerifier);
		
		// assign input control to editor 
		setInputControl( inputControl);
		
		setVerifyListenerToControl( inputControl);

		// initialize input control
		Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
	}
	

	private void setVerifyListenerToControl( final IVerifyingControl<Text,CharStringValue,String> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				
				if ( lastResult.verifier instanceof SimpleRegexVerifier) {
					if ( !lastResult.verified) {
						getMessageView().flashMessages( lastResult.messages);
						// ignore after verification 
						theEvent.skipVerification = true;
						theEvent.doit = false;
					} else {
						theEvent.doit = true;
					}
				}
				
				if ( lastResult.verifier instanceof StringRangeVerifier) {
					if ( !lastResult.verified) {
						getMessageView().showMessage( lastResult.messages.get( 0));
						getMessageView().flashMessage( lastResult.messages.get( 1));
						// ignore after verification 
						theEvent.skipVerification = true;
						theEvent.doit = true;
					} else {
						getMessageView().clearMessagesByTag( lastResult.messages.get( 0).tag);
						theEvent.doit = true;
					}
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


	@Override
	protected void createEditRow(Composite theContainer) {
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel labelId = new CLabel( theContainer, SWT.LEFT);
		labelId.setText( this.getParameter().getId());
		labelId.setLayoutData( layoutData[0]);
		
		String toolTipString = this.getParameter().getName() + ":\n" + this.getParameter().getDescription();
		labelId.setToolTipText( toolTipString);
		
		createInputWidget( theContainer, layoutData[0]);
		
		Button reset = new Button (theContainer, SWT.PUSH);
		reset.setText ("Reset");
		reset.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent theEvent) {
				String defaultValueString = ParameterValueUtil.getDefaultValue( getParameter());
				setInputValue( defaultValueString);
				super.widgetSelected( theEvent);
			}
		});
		
		theContainer.pack(); 
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new StringEditorLookAndBehaviour();
	}
	



}
