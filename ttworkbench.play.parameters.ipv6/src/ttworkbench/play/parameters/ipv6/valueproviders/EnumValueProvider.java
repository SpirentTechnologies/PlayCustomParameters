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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import com.testingtech.muttcn.compilerkernel.Name;
import com.testingtech.muttcn.compilerkernel.impl.NameImpl;
import com.testingtech.muttcn.kernel.Element;
import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.muttcn.values.ConstantValue;
import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.impl.ConstantValueImpl;
import com.testingtech.muttcn.values.impl.OctetStringValueImpl;
import com.testingtech.muttcn.values.impl.StringValueImpl;
import com.testingtech.ttcn.metamodel.Messages;
import com.testingtech.ttcn.tools.runtime.TypeClass;
import com.testingtech.ttworkbench.metamodel.core.repository.IRepositoryView;
import com.testingtech.ttworkbench.metamodel.muttcn.generator.ValueGenerator;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.TemplateKind;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.TypeInterpreter;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.ValueInterpreter;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.ValueKind;
import com.testingtech.ttworkbench.metamodel.muttcn.interpreter.VerdictKind;
import com.testingtech.ttworkbench.metamodel.muttcn.search.CompletionResult;
import com.testingtech.ttworkbench.metamodel.muttcn.util.ModelElementUtils;
import com.testingtech.ttworkbench.metamodel.ui.util.ModelGuiUtils;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;


public class EnumValueProvider<T extends Expression> implements IParameterValueProvider<T> {

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
		@Override
		public String toString() {
			return getTheName().getTheName();
		}
		public NamedConstantValue(String theName) {
			this(new NameImpl() {
				@Override
				public String getTheName() {
					return theName;
				}
			});
		}
		public NamedConstantValue(Name theName) {
			super();
			setTheName( theName);
		}
		@Override
		public Name getTheName() {
			return super.getTheName();
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
			
			//if(TypeClass.Enumerated.equals(typeClass)) {
				
			//}
			
	    Element element = theParameter.getValue();	    
			ValueInterpreter valueInterpreter = ValueInterpreter.create(repositoryView, element);
			
			
			
			
			
			// 1 dunno
      final String typeName;
      final Expression type = valueInterpreter.getType();
      final boolean isEnumerated;
      final TypeInterpreter typeInterpreter;
      final String typeClassStr;
      final TypeClass typeClass;
      
			if (type != null) {
        typeInterpreter = TypeInterpreter.create(repositoryView, type);
        typeClass = typeInterpreter.getKind();
        typeClassStr = typeClass.getTtcn3String();
        typeName = typeInterpreter.getTypeName();
        isEnumerated = TypeClass.Enumerated.equals(typeClass) 
        		&& typeInterpreter.getConstrainedEnumNames().contains(valueInterpreter.getStringContent());
      }
			else { // if no type found (maybe operator)
        typeInterpreter = null;
        typeClass = TypeClass.Any;
        isEnumerated = false;
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
      System.out.println(valueKind);


      // 2 dunno
		  final String stringContent = valueInterpreter.getStringContent();
		  final String name = valueInterpreter.getName();
      if ((TypeClass.Union.equals(typeClass) || TypeClass.AnyType.equals(typeClass)) && ValueKind.literal.equals(valueKind)) {
        final String selectedVariant = valueInterpreter.getSelectedVariant();
        System.out.println(selectedVariant);
      }

      
      
      LinkedList<CompletionResult> resultList = new LinkedList<CompletionResult>();
      if (!ValueKind.reference.equals(valueKind)) {
        if (TypeClass.Boolean.equals(typeClass)) {
          for (NamedConstantValue bl : BOOLEAN_VALUES) {
          	T enumValue = (T) bl;
          	enumValues.add( enumValue);
          	System.out.println(enumValue);
            //resultList.add(new CompletionResult(str, str, TypeClass.Boolean));
          }

        }

        else if (TypeClass.VerdictType.equals(typeClass)) {
          for (VerdictKind verdict : VerdictKind.values()) {
          	System.out.println(verdict);
            // resultList.add(new CompletionResult(verdict.getString(), verdict.getString(), TypeClass.VerdictType));
          }
        }

				else if (TypeClass.Enumerated.equals( typeClass)) {
					final ArrayList<CompletionResult> list = ModelGuiUtils.createEnumCompletitionResult( typeInterpreter);
					resultList.addAll( list);
					System.out.println(list);
				}

				else if (TypeClass.Union.equals( typeClass)) {
					final ArrayList<CompletionResult> variantsList = ModelGuiUtils.createUnionCompletionResult( typeInterpreter, repositoryView);
					resultList.addAll( variantsList);
					System.out.println(variantsList);
				}

				else if (TypeClass.AnyType.equals( typeClass)) {
					//ArrayList<CompletionResult> typeList = ModelGuiUtils.getTypes( repositoryView, "bla?", true, true, false);
					//resultList.addAll( typeList);

					System.out.println("dunno man!");
				}

			}
      

      if(enumValues.isEmpty()) {
		    ArrayList<String> listNames = typeInterpreter.getAllEnumNames();
		    for(String nam : listNames) {
			    ValueGenerator gen = new ValueGenerator(repositoryView);
			    Expression exp = gen.generateEnumeratedValue( typeInterpreter, nam);
			    System.out.println(exp.getTheName());
			    enumValues.add( (T) exp);
		    }
		    if(listNames.size()<1) {
		    	throw new Exception("The TypeInterpreter could not find any enum names.");
		    }
      }
		}
		catch(Exception e) {
			System.err.println("Could not find available values for \""+theParameter.getName()+"\" (type: "+theParameter.getType()+"): "+e.getMessage());
			enumValues.add( theParameter.getValue());
			enumValues.add( theParameter.getDefaultValue());
		}
		
  
		return enumValues;
	}
	
	private StringValue newString( String theString) {
		StringValue stringValue = new StringValueImpl() {};
		stringValue.setTheContent( theString);
	  return stringValue;
	}
}
