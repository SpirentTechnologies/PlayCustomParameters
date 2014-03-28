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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.NotImplementedException;
import com.testingtech.muttcn.compilerkernel.Name;
import com.testingtech.muttcn.compilerkernel.impl.NameImpl;
import com.testingtech.muttcn.kernel.Element;
import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.muttcn.values.impl.ConstantValueImpl;
import com.testingtech.ttcn.tools.runtime.TypeClass;
import com.testingtech.ttworkbench.metamodel.core.repository.IRepositoryView;
import com.testingtech.ttworkbench.metamodel.muttcn.generator.ValueGenerator;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.TemplateKind;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.TypeInterpreter;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.ValueInterpreter;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.ValueKind;
import com.testingtech.ttworkbench.metamodel.muttcn.util.ModelElementUtils;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;


public class EnumValueProvider<T extends Expression> implements IParameterValueProvider<T> {

	private static ConcurrentHashMap<String, Expression> cachedTypes = new ConcurrentHashMap<String, Expression>();
	
	private static final NamedConstantValue[] BOOLEAN_VALUES = new NamedConstantValue[] {
		new NamedConstantValue("true"),
		new NamedConstantValue("false")
	};

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

	public static class NamedConstantValue extends ConstantValueImpl {
		public NamedConstantValue() {
			super();
		}
		public NamedConstantValue(String newName) {
			Name name = new NameImpl() {
				
			};
			name.setTheName( newName);
			setTheName( name);
		}
		@Override
		public Name getTheName() {
			Name name = super.getTheName();
			return name;
		}
	}
	
	
	@Override
	public Set<T> getAvailableValues(IParameter<T> theParameter) {
		Set<T> enumValues = new HashSet<T>();
		
		try {
			IRepositoryView repositoryView = com.testingtech.ttworkbench.ttman.ManagementPlugin.getRepositoryView();
			if(repositoryView==null) {
				throw new Exception("Could not get repository view. Maybe there is no testing project open.");
			}

			final Element element = theParameter.getValue();
	    final ValueInterpreter valueInterpreter = ValueInterpreter.create(repositoryView, element);
      final Expression type = getType(theParameter);
      final TypeInterpreter typeInterpreter;
      final TypeClass typeClass;
      
      
			if (type != null) {
        typeInterpreter = TypeInterpreter.create(repositoryView, type);
        typeClass = typeInterpreter.getKind();
      }
			else { // if no type found (maybe operator)
        typeInterpreter = null;
        typeClass = TypeClass.Any;
      }

			// show ticks only on a literal value
			ValueKind valueKind = valueInterpreter.getValueKind();
			TemplateKind templateKind = valueInterpreter.getTemplateKind();
		
			switch (valueKind) {
				case literal:
					if (!ModelElementUtils.isEditableType( typeClass) || valueInterpreter.isPattern()) {
						valueKind = ValueKind.freetext;
					}
					break;

				case invalidLiteral:
					valueKind = ValueKind.literal;
					switch (typeClass) {
						case Objid:
							valueKind = ValueKind.freetext;
							break;
					}
					break;

				case operation:
					valueKind = ValueKind.freetext;
					break;

				case template:
					switch (templateKind) {
						case unknown:
							valueKind = ValueKind.freetext;
							break;

					}
					break;
			}

      if ((TypeClass.Union.equals(typeClass) || TypeClass.AnyType.equals(typeClass)) && ValueKind.literal.equals(valueKind)) {
				// TODO not yet implemented
				throw new NotImplementedException();
      }
      
      if (!ValueKind.reference.equals(valueKind)) {
        if (TypeClass.Boolean.equals(typeClass)) {
          for (NamedConstantValue bl : BOOLEAN_VALUES) {
          	enumValues.add( (T) bl);
          }
        }
        else if (TypeClass.VerdictType.equals(typeClass)) {
					// TODO not yet implemented
					throw new NotImplementedException();
        }
				else if (TypeClass.Enumerated.equals( typeClass)) {
					// TODO not yet implemented
					throw new NotImplementedException();
				}
				else if (TypeClass.Union.equals( typeClass)) {
					// TODO not yet implemented
					throw new NotImplementedException();
				}
				else if (TypeClass.AnyType.equals( typeClass)) {
					// TODO not yet implemented
					throw new NotImplementedException();
				}
			}
      

      if(enumValues.isEmpty()) {
		    ArrayList<String> listNames = typeInterpreter.getAllEnumNames();
		    for(String nam : listNames) {
			    ValueGenerator gen = new ValueGenerator(repositoryView);
			    Expression exp = gen.generateEnumeratedValue( typeInterpreter, nam);
			    enumValues.add( (T) exp);
		    }
		    if(listNames.size()<1) {
		    	throw new Exception("The TypeInterpreter could not find any enum names.");
		    }
      }
		}
		catch(Exception e) {
			System.err.println("Could not find available values for \""+theParameter.getName()+"\" (type: "+theParameter.getType()+"): "+e.getMessage());
			e.printStackTrace();
			enumValues.add( theParameter.getValue());
			enumValues.add( theParameter.getDefaultValue());
		}
		
  
		return enumValues;
	}

	private Expression getType(IParameter<T> theParameter) throws Exception {
		Expression type = cachedTypes.get( theParameter.getId());
		if(type==null) {
			IRepositoryView repositoryView = com.testingtech.ttworkbench.ttman.ManagementPlugin.getRepositoryView();
			if(repositoryView==null) {
				throw new Exception("Could not get repository view. Maybe there is no testing project open.");
			}
	
			Element element = theParameter.getValue();
	    ValueInterpreter valueInterpreter = ValueInterpreter.create(repositoryView, element);
	    cachedTypes.put( theParameter.getId(), type = valueInterpreter.getType());
		}
    return type;
	}
}
