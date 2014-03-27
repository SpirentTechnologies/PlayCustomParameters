
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

import java.util.ArrayList;
import java.util.Arrays;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class SimpleValidatorContext implements IValidatorContext {
	private ArrayList<IParameter<?>> parameters = new ArrayList<IParameter<?>>();
	private ArrayList<IWidget> widgets = new ArrayList<IWidget>();
	private final IConfigurator configurator;

	public SimpleValidatorContext() {
		this.configurator = null;
	}
	public SimpleValidatorContext(IConfigurator widgetConfigurator, IParameter<?>... contextParameters) {
		this.parameters.addAll( Arrays.asList( contextParameters));
		this.configurator = widgetConfigurator;
	}

	@Override
	public IParameter<?>[] getParameters() {
		return parameters.toArray(new IParameter<?>[0]);
	}

	@Override
	public IWidget[] getWidgets() {
		return widgets.toArray(new IWidget[0]);
	}
	
	public void addParameter(IParameter<?> parameter) {
		parameters.add( parameter);
	}
	
	public void addWidget(IWidget theWidget) {
		widgets.add( theWidget);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Value> IParameterEditor<T>[] getEditorsForParameter(IParameter<T> theParameter) {
		if(configurator!=null) {
			return (IParameterEditor<T>[]) configurator
					.getAvailableEditors( theParameter).toArray( new IParameterEditor<?>[0]);
		}
		return (IParameterEditor<T>[]) new IParameter<?>[0];
	}
	


}
