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
package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.widgets.Control;

import com.testingtech.muttcn.kernel.Value;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.components.design.EditorStateFlag;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationEvent;

public abstract class VerifyingEditor<C extends Control, P extends Value> extends ValidatingEditor<P> {
	
	private IVerifyingControl<? extends C,P> inputControl;
	

	public VerifyingEditor( final String theTitle, final String theDescription) {
		super( theTitle, theDescription);
	}
	
	public VerifyingEditor( final String theTitle, final String theDescription, final IVerifyingControl<C, P> theInputControl) {
		this( theTitle, theDescription);
		setInputControl( theInputControl);
	}
	
	/**
	 * Starts verification process and sets the value after successful verification.
	 * @param theValue
	 */
	public void setInputValue( final String theValue) {
		inputControl.setText( theValue);
	}
	
	/**
	 * Sets the parameter value direct without verification. 
	 * @param theValue
	 */
	protected void forceParameterValue( final String theParameterValue) {
		getState().setFlag( EditorStateFlag.SET_PARAMETER);
		ParameterValueUtil.setValue( getParameter(), theParameterValue);
		getState().unsetFlag( EditorStateFlag.SET_PARAMETER);
	}
	
	protected void setInputControl( final IVerifyingControl<? extends C, P> theInputControl) {
		this.inputControl = theInputControl;
		this.inputControl.addListener( new IVerificationListener<String>() {
			
			@Override
			public void beforeVerification(VerificationEvent<String> theEvent) {
				getState().setFlag( EditorStateFlag.VERIFYING);
			}
			
			@Override
			public void afterVerificationStep(VerificationEvent<String> theEvent) {
				// nothing
			}
			
			@Override
			public void afterVerification(VerificationEvent<String> theEvent) {
				getState().unsetFlag( EditorStateFlag.VERIFYING);
			}
		});
	}
	
	public IVerifyingControl<? extends C, P> getInputControl() {
		return inputControl;
	}
	
	@Override
	public void setFocus() {
		if ( hasControl())
		  inputControl.getControl().setFocus();
	}

  @Override
  public void reloadParameter() {
  	if ( hasControl() && 
  			 !getState().isset( EditorStateFlag.VERIFYING) &&
  			 !getState().isset( EditorStateFlag.SET_PARAMETER)) {
  		String updatedValue = ParameterValueUtil.getValue( getParameter());
  	  inputControl.forceText( updatedValue);
  	}
  }

}
