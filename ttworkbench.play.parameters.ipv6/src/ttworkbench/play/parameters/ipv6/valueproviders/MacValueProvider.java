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
package ttworkbench.play.parameters.ipv6.valueproviders;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.muttcn.values.impl.OctetStringValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacValueProvider implements IParameterValueProvider<OctetStringValue> {

	@Override
	public void setAttribute(String theName, String theValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OctetStringValue> getAvailableValues(IParameter<OctetStringValue> theParameter) {
		Set<OctetStringValue> values = new HashSet<OctetStringValue>();
		try {
	    Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
	    while(networks.hasMoreElements()) {
	      NetworkInterface network = networks.nextElement();
	      byte[] mac = network.getHardwareAddress();

	      
	      if(mac != null) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	          sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	        }
	        if(sb.toString().length() <= 17){
	        	//to filter the invalid addresses
	        	values.add( newString(sb.toString()));
	        }
	      }
	    }
	  } catch (SocketException e){
	    e.printStackTrace();
	  }
		//Add the default value to the combo box
		values.add( theParameter.getDefaultValue());
		return values;
	}
	
	private OctetStringValue newString( String theString) {
		OctetStringValue stringValue = new OctetStringValueImpl() {};
		stringValue.setTheContent( theString);
	  return stringValue;
	}

}
