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
package ttworkbench.play.parameters.ipv6.factories;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.editors.ip.IPEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv6Verifier;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditorFactory;

public class IPv6EditorFactory implements IParameterEditorFactory {

	private String title;
	private String description;
	private List<IAttribute> attributes = new ArrayList<IAttribute>();

	public IPv6EditorFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isApplicable(IParameter<? extends Object> theParameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTitle(String theTitle) {
		this.title = theTitle;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setDescription(String theDescription) {
		this.description = theDescription;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<IAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public IParameterEditor create() {
		return new IPEditor( new IPv6Verifier());
	}

}
