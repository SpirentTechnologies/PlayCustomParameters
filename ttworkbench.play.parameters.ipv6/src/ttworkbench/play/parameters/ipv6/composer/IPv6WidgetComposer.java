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

import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.integer.IntegerEditor;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.validators.IPv6Validator;
import ttworkbench.play.parameters.ipv6.valueproviders.IPValueProvider;
import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class IPv6WidgetComposer extends WidgetComposer {

	private static final String TYPE_MATCH_INTEGER = "^(UInt\\d{0,2}|Int\\d{0,2})$";

	public IPv6WidgetComposer( IConfigurator theConfigurator, ParameterMap theParameters) {
		super( theConfigurator, theParameters);
	}

	@Override
	public void compose() {

		IWidget IPv6Widget = new IPv6Widget();
		getConfigurator().addWidget( IPv6Widget);

		IParameterValidator njetValidator = new AbstractValidator( "No Validator", "") {
			@Override
			protected List<ValidationResult> validateParameter(IParameter parameter, Object theClient) {
				List<ValidationResult> l = new ArrayList<ValidationResult>();
				l.add( new ValidationResultMessage( "nay-sayer", ErrorKind.error, theClient));
				return l;
			}

		};

		IParameterValidator yeahValidator = new AbstractValidator( "Yes Validator", "") {

			@Override
			protected List<ValidationResult> validateParameter(IParameter parameter, Object theClient) {
				List<ValidationResult> l = new ArrayList<ValidationResult>();
				l.add( new ValidationResultMessage( "yea-sayer", ErrorKind.success, theClient));
				l.add( new ValidationResultMessage( "gasbag", ErrorKind.info, theClient));
				return l;
			}

		};

		// TODO: replace demo composition
		Set<IParameter<?>> parameters = getConfigurator().getParameterModel().getParameters();
		for (IParameter<?> parameter : parameters) {
			ValidatingEditor<?> editor;
			if (parameter.getType().matches( TYPE_MATCH_INTEGER))
				editor = new IntegerEditor();
			else
				continue;// editor = new IPv6Editor();
			getConfigurator().assign( editor, IPv6Widget, parameter);
			njetValidator.registerForMessages( editor);
			yeahValidator.registerForMessages( editor);
		}

		getConfigurator().assign( new IPv6Validator(), IPv6Widget, new ArrayList<IParameter>( parameters));
		getConfigurator().assign( njetValidator, IPv6Widget, new ArrayList<IParameter>( parameters));
		getConfigurator().assign( yeahValidator, IPv6Widget, new ArrayList<IParameter>( parameters));

		getConfigurator().assign( new IPValueProvider<Inet6Address>( Inet6Address.class), IPv6Widget,
				new ArrayList<IParameter>( parameters));
	}

}
