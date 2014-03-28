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
package ttworkbench.play.parameters.ipv6.editors.macaddr;


import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingCombo;
import ttworkbench.play.parameters.ipv6.valueproviders.MacValueProvider;

import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacAddressEditor extends VerifyingEditor<Combo,OctetStringValue> {

	
	private static final String TITLE = "MAC Address Editor";
	private static final String DESCRIPTION = "";
	
	private static final int MAC_LENGTH = 17;
	
	
	private IParameterValueProvider<OctetStringValue> macValueProvider = new MacValueProvider();
	private IVerifier<String> verifier;
	
	public MacAddressEditor() {
		  super( TITLE, DESCRIPTION);
		  this.verifier = new MacPatternVerifier();
	}
	
	public MacAddressEditor( final IVerifier<String> verifier) {
		this();
		this.verifier = verifier;
	}
		

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new MacEditorLookAndBehaviour();
	}



	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO Auto-generated method stub
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		
		CLabel label = new CLabel(theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName());
		label.setLayoutData( layoutData[0]);
		
		createComboBox( theContainer, layoutData[0]);
	}
	
	private void createComboBox( Composite theComposite, Object theLayoutData){
		IVerifyingControl<Combo, OctetStringValue, String> inputControl = new VerifyingCombo<OctetStringValue>( getParameter(), theComposite, SWT.BORDER);
		inputControl.addVerifierToEvent( verifier, SWT.Verify);
		setInputControl( inputControl);
		final Combo macCombo = inputControl.getControl();
		final Rectangle dimensions = new Rectangle(50, 50, 200, 65);
		macCombo.setBounds( dimensions);
		setWidthForText(macCombo, MAC_LENGTH);
		macCombo.setTextLimit( MAC_LENGTH);
		
		Set<OctetStringValue> availableValues = macValueProvider.getAvailableValues( this.getParameter());

		int index = 0;
		for(StringValue value : availableValues){
			macCombo.add(value.getTheContent().toString(), index);
			index++;
		}
		
		setVerifyListenerToControl( inputControl);
		
		// set the Default Parameter Value
		inputControl.forceText( getParameter().getDefaultValue().getTheContent());
	}
	
	private void setVerifyListenerToControl( final IVerifyingControl<?,OctetStringValue,String> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				if ( !lastResult.verified) {
					theEvent.doit = true;
					theEvent.skipVerification = true;
				}
				getMessageView().showMessages( lastResult.messages);
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {
				// verification passed, then write the value to parameter
				String stringToOct = theEvent.inputToVerify.replaceAll( "[:-]", "").toUpperCase();
				System.out.println(stringToOct);
				forceParameterValue( theEvent.inputToVerify);
				validateDelayed(theInputControl);
				theEvent.doit = true;
			}
		});
	}

	
	private static void setWidthForText( Control theTextControl, int visibleChars) {
		 GC gc = new GC( theTextControl);
		 int charWidth = gc.getFontMetrics().getAverageCharWidth();
		 gc.dispose();

		 int minWidth = visibleChars * charWidth;
		 Object layout = theTextControl.getLayoutData();
		 if ( layout instanceof GridData)
			 ((GridData) layout).minimumWidth = minWidth + 20;
		 if ( layout instanceof RowData)
			 ((RowData) layout).width = minWidth + 20;		
		 else
			 theTextControl.setSize( theTextControl.computeSize( minWidth + 20, SWT.DEFAULT));
	}
	
}
