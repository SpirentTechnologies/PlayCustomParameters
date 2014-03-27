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
package ttworkbench.play.parameters.ipv6.editors.octet;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.VerifyingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;

import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class OctetEditor extends VerifyingEditor<Text,OctetStringValue> {
	
	private static final String TITLE = "Octet Editor";
	private static final String DESCRIPTION = "";
	
	private OctetType octetType = OctetType.OCT;
	
	private OctetTypeVerifier octetTypeVerifier = new OctetTypeVerifier();
	private OctetRangeVerifier octetRangeVerifier = null;
	
	public OctetEditor() {
		super( TITLE, DESCRIPTION);
	}

	
	@Override
	public void setParameter(IParameter<OctetStringValue> theParameter) {
		super.setParameter( theParameter);
		determineOctetType();
	}
	
	private void determineOctetType() { 
		String parameterType = getParameter().getType();
		octetType = OctetType.valueOfTypeName( parameterType);
		octetRangeVerifier = new OctetRangeVerifier( octetType);
	}

	

	
	private static void setWidthForText( Text theTextControl, int visibleChars) {
		 GC gc = new GC( theTextControl);
		 int charWidth = gc.getFontMetrics().getAverageCharWidth();
		 gc.dispose();

		 int minWidth = visibleChars * charWidth;
		 Object layout = theTextControl.getLayoutData();
		 if ( layout instanceof GridData)
			 ((GridData) layout).minimumWidth = minWidth;
		 if ( layout instanceof RowData)
			 ((RowData) layout).width = minWidth;		
		 else
			 theTextControl.setSize( theTextControl.computeSize( minWidth, SWT.DEFAULT));
	}
	
	private void reloadAndSetParameterValue() {
		String parameterString = ParameterValueUtil.getValue( getParameter());
		String currentString = getInputControl().getText();
		if ( !parameterString.equals( currentString)) {
			getInputControl().forceText( ParameterValueUtil.getValue( getParameter()));
			getControl().layout();
		}
	}

	private void createTextInputWidget( Composite theComposite, Object theLayoutData) {
		IVerifyingControl<Text, OctetStringValue> inputControl = new VerifyingText<OctetStringValue>( getParameter(), theComposite, SWT.BORDER | SWT.SINGLE, octetTypeVerifier, octetRangeVerifier);
		setInputControl( inputControl);
		
		setVerifyListenerToControl( inputControl);
		
		final Text text = inputControl.getControl();
		text.setText( ParameterValueUtil.getValue( getParameter()));
		text.setLayoutData( theLayoutData);
		
		text.addListener( SWT.FocusOut, new Listener() {
			
			@Override
			public void handleEvent(Event theEvent) {
				reloadAndSetParameterValue();
			}
		});

		text.addListener( SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(Event theEvent) {
				if ( theEvent.keyCode == SWT.CR) {
					reloadAndSetParameterValue();
				}
			}
		});
	}
	
	private void setVerifyListenerToControl( final IVerifyingControl<Text,OctetStringValue> theInputControl) {
		theInputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(final VerificationEvent<String> theEvent) {}
			
			@Override
			public void afterVerificationStep(final VerificationEvent<String> theEvent) {
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				
				if ( lastResult.verifier instanceof OctetTypeVerifier) {
					if ( !lastResult.verified) {
						getMessageView().flashMessages( lastResult.messages);
				    theEvent.skipVerification = true;
				    theEvent.doit = false;
					} else {
						theEvent.verifierParams = new Object[]{lastResult.output};
						theEvent.doit = true;
					}
				}
				
				if ( lastResult.verifier instanceof OctetRangeVerifier) {
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
			}
			
			@Override
			public void afterVerification(final VerificationEvent<String> theEvent) {
				// get the normalized representation  
				final List<VerificationResult<String>> results = theEvent.verificationResults;
				final VerificationResult<String> lastResult = results.get( results.size() -1);
				String zeroFilledInput = (String) lastResult.output;
				
				// verification passed, then write the value to parameter
				forceParameterValue( zeroFilledInput);
				// and start the validation process
				validateDelayed( theInputControl);
				theEvent.doit = true;
			}
			
			
		});
	}


	@Override
	protected void createEditRow(Composite theContainer) {
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getId());
		label.setLayoutData( layoutData[0]);
		
		String toolTipString = this.getParameter().getName() + ":\n" + this.getParameter().getDescription();
		label.setToolTipText( toolTipString);

		createTextInputWidget( theContainer, layoutData[0]);
		
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
		return new IntegerEditorLookAndBehaviour();
	}
	



}
