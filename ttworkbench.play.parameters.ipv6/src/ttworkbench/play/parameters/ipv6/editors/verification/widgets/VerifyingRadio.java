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
package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;

import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class VerifyingRadio<P extends Expression> extends VerifyingAdapter<Group,P>{

	public VerifyingRadio( final IParameter<P> theParameter, Composite theParent, int theStyle, final IVerifier<String> ... theVerifiers) {
		super( theParameter, theParent, theStyle, theVerifiers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setText(String theText) {
		// TODO Auto-generated method stub
		getControl().setText( theText);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return getControl().getText();
	}

	@Override
	protected String getModifiedTextByEvent(Event theEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Group createControl(Composite theParent, int theStyle) {
		// TODO Auto-generated method stub
		return new Group(theParent, theStyle);
	}

}
