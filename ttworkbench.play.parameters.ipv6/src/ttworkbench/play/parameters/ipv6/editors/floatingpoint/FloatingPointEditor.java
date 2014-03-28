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
package ttworkbench.play.parameters.ipv6.editors.floatingpoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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

import com.testingtech.muttcn.values.FloatValue;

public class FloatingPointEditor extends VerifyingEditor<Text,FloatValue> {
	
	private static final String TITLE = "Integer Editor";
	private static final String DESCRIPTION = "";
	
	private List<Locale> locales;
	
	private CLabel labelInterpretedValue;
		
	public FloatingPointEditor() {
		this( Arrays.asList( Locale.getDefault(), Locale.ENGLISH));
	}
	
	public FloatingPointEditor( final List<Locale> theLocales) {
		super( TITLE, DESCRIPTION);
		this.locales = theLocales;
	}
	
	@Override
	public void setAttribute(String theName, String theValue) {
		if ( theName.equalsIgnoreCase( "locale")) {
			locales = new ArrayList<Locale>();
			String[] localeNames = theValue.split( "\\s*,\\s*");
			for (String localeName : localeNames) {
				locales.add( new Locale( localeName));	
			}
		}
		super.setAttribute( theName, theValue);
	}



	

	
	private void createInputWidget( Composite theComposite, Object theLayoutData) {
		IVerifyingControl<Text, FloatValue, String> inputControl = new VerifyingText<FloatValue>( getParameter(), theComposite, SWT.BORDER | SWT.SINGLE);
		
		// create and assign verifiers
		FloatTypeVerifier verifier;
		for ( Locale locale : locales) {
			verifier = new FloatTypeVerifier( locale);
			inputControl.addVerifierToEvent( verifier, SWT.Verify);
		}
		
		// assign input control to editor 
		setInputControl( inputControl);

		setVerifyListenerToControl( inputControl);
		
		// initialize input control
		Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
	}
	

	private void setVerifyListenerToControl( final IVerifyingControl<Text,FloatValue,String> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				int length = theEvent.verifierParams.length;
				Object[] extendedVerifierParams = Arrays.copyOf( theEvent.verifierParams, length +1);
				extendedVerifierParams[length] = ((FloatTypeVerifier)lastResult.verifier).getLocale();
				theEvent.verifierParams = extendedVerifierParams;
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {	
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				for (VerificationResult<String> verificationResult : results) {
					if ( verificationResult.verified) {
						// clear old error messages by send this success message 
						getMessageView().showMessages( verificationResult.messages);
						// verification passed, then write the value to parameter
						forceParameterValue( verificationResult.input);
						// and start the validation process
						validateDelayed( theInputControl);
						theEvent.doit = true;
						// show float representation of the value
						labelInterpretedValue.setText( new BigDecimal( verificationResult.input).toEngineeringString());
						return;
					}
				}
				
				// if all verification attempts fail
				final VerificationResult<String> lastResult = results.get( results.size() -1);
			  getMessageView().showMessages( lastResult.messages);
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
		
		CLabel labelInterpretation = new CLabel( theContainer, SWT.LEFT);
		labelInterpretation.setText( "Interpreted value:");
		labelInterpretation.setLayoutData( layoutData[0]);
		
		labelInterpretedValue = new CLabel( theContainer, SWT.LEFT);
		labelInterpretation.setLayoutData( layoutData[0]);
		
		createInputWidget( theContainer, layoutData[0]);
		getInputControl().getControl().moveAbove( reset);
		
		theContainer.pack(); 
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new FloatingPointEditorLookAndBehaviour();
	}
	



}
