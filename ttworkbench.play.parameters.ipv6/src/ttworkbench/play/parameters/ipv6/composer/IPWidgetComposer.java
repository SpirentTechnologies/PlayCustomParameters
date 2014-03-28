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
package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ip.HostnameVerifier;
import ttworkbench.play.parameters.ipv6.editors.ip.IPEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv4Verifier;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv6Verifier;
import ttworkbench.play.parameters.ipv6.editors.verification.OrVerifier;
import ttworkbench.play.parameters.ipv6.widgets.IPWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class IPWidgetComposer extends WidgetComposer {

	// get relevant parameters
	final IParameter<StringValue> ipv4Parameter = getParametersMap().getParameterById( "PX_IPv4_ADDR_IUT");
	final IParameter<StringValue> floatParameter = getParametersMap().getParameterById( "PX_FLOAT_NUMBER");

	public IPWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// declare a IP Address Widget
		IWidget ipWidget = new IPWidget();
		// add the IP widget to the frame work
		getConfigurator().addWidget( ipWidget);

		// assign each parameter to the corresponding editor in this widget
		getConfigurator().assign( new IPEditor( new IPv4Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new OrVerifier( new IPv4Verifier(), new IPv6Verifier())), ipWidget,
				ipv4Parameter);
		getConfigurator().assign( new IPEditor( new IPv6Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new HostnameVerifier()), ipWidget, ipv4Parameter);

		//getConfigurator().assign( new FloatingPointEditor(), ipWidget, floatParameter);

	}
}
