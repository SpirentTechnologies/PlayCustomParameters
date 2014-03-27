
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
package ttworkbench.play.parameters.ipv6.validators;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class ContextualValidator extends AbstractValidator implements IWithValidatorContext {
	private IValidatorContext context;

	public ContextualValidator( String theTitle, String theDescription) {
		super( theTitle, theDescription);
	}

	public void setContext(IValidatorContext theContext) {
		this.context = theContext;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Value> IParameter<T> getParameter( int index) {
		return (IParameter<T>) getContext().getParameters()[index];
	}

	public <T extends Value> IParameterEditor<T>[] getEditors( int index) {
		IParameter<T> parameter = getParameter( index);
		return getContext().getEditorsForParameter( parameter);
	}
	
	public IValidatorContext getContext() {
		return context;
	}
	
	@Override
	public int hashCode() {
		// TODO + context
		return super.hashCode() + (context!=null ? 31*context.hashCode() : 0);
	}
	
	@Override
	public long getId() {
		return hashCode();
	}
  

}
