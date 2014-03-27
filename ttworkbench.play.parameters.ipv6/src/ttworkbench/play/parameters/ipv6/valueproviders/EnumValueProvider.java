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
package ttworkbench.play.parameters.ipv6.valueproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.impl.StringValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class EnumValueProvider implements IParameterValueProvider{

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
	public <T> Set<T> getAvailableValues(IParameter<T> theParameter) {
		// TODO Auto-generated method stub
		boolean isRadio = false;
		
		//these 2 sets are only meant for Tests
		Set<T> testEnumRadio = new HashSet<T>();
		Set<T> testEnumCombo = new HashSet<T>();
		
		//This set gets the value from the real list
		Set<T> availableValues = new HashSet<T>(); 
		
		testEnumRadio.add( theParameter.getValue());
		testEnumRadio.add( theParameter.getDefaultValue());
		
		testEnumCombo.add((T) newString("value1"));
		testEnumCombo.add((T) newString("value2"));
		testEnumCombo.add((T) newString("value3"));
		
		if(availableValues.size() < 3){
			isRadio = true;
		}
		
		return (isRadio)?testEnumRadio:testEnumCombo;
	}
	
	private StringValue newString( String theString) {
		StringValue stringValue = new StringValueImpl() {};
		stringValue.setTheContent( theString);
	  return stringValue;
	}
}
