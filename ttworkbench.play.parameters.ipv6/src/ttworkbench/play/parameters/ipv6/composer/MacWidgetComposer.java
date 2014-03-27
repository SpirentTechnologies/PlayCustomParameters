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

package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.macaddr.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.widgets.MacWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class MacWidgetComposer extends WidgetComposer {

	// get relevant parameters
	final IParameter<StringValue> parameter_MacAddress = getParametersMap().getParameterById( "PX_MAC_UCA_HS01");

	public MacWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// declare a Mac Address Widget
		IWidget macWidget = new MacWidget();
		// add the Mac widget to the frame work
		getConfigurator().addWidget( macWidget);

		getConfigurator().assign( new MacAddressEditor(), macWidget, parameter_MacAddress);

	}
}
