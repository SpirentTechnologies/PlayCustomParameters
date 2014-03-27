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

package ttworkbench.play.parameters.ipv6;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class ParameterMap {
	
	private Map<String,IParameter<?>> idToParameterMap;
	private Collection<IParameter<?>> parameters;
	
	public ParameterMap( final IConfigurator theConfigurator) {
		loadParameters( theConfigurator);
	}
	
	private void loadParameters( IConfigurator theConfigurator) {
    this.idToParameterMap = new ConcurrentHashMap<String, IParameter<?>>();
		this.parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter<?> parameter : parameters) {
			idToParameterMap.put( parameter.getId(), parameter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Value> IParameter<T> getParameterById( final String theId) {
		return (IParameter<T>) idToParameterMap.get( theId);
	}
	
	public Collection<IParameter<?>> getAllParameters() {
		return parameters;
	}

	public boolean isEmpty() {
		return idToParameterMap.isEmpty();
	}
	
}
