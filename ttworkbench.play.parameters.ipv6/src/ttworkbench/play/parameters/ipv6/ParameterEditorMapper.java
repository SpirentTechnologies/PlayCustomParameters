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

import java.lang.reflect.Constructor;
import java.util.Map.Entry;

import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.settings.Data.EditorTypeMapping;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;
import ttworkbench.play.parameters.settings.loader.DataLoader;

import com.testingtech.ttworkbench.ttman.ManagementPlugin;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class ParameterEditorMapper {
	
	
	private static ParameterEditorMapper INSTANCE = null;
	private static String BLOCKER = "BLOCKER";

	public static ParameterEditorMapper getInstance() {
		if(INSTANCE==null) {
			synchronized (BLOCKER) {
				if(INSTANCE==null) {
					INSTANCE = new ParameterEditorMapper();
				}
			}
		}
		return INSTANCE;
	}
	
	

	public ParameterEditorMapper() {
		
	}

	public IParameterEditor<?> getEditor(IParameter<?> theParameter) {
		IParameterEditor<?> editor = null;
		
		try {
			for(EditorTypeMapping entryParameterEditor : DataLoader.getInstance().getTypeEditorMappings()) {
				if(theParameter.getType().matches(entryParameterEditor.getTypeExpression())) {
					Class<? extends IParameterEditor<?>> editorType = getEditorTypeByClassPath(entryParameterEditor.getType());
					if(editorType!=null) {
						editor = getEditorInstanceByType(editorType);
						
						for(Entry<String, String> attrEntry : entryParameterEditor.getAttributes().entrySet()) {
							editor.setAttribute( attrEntry.getKey(), attrEntry.getValue());
						}
						break;
					}
				}
			}
		} catch (ParameterConfigurationException e) {
			logError( "Could not load editor type mappings: "+e.getMessage(), e);
		}

		if(editor==null) {
			editor = new DefaultEditor();
		}
		return editor;
	}

	private IParameterEditor<?> getEditorInstanceByType(Class<? extends IParameterEditor<?>> theEditorType) {
		IParameterEditor<?> editor = null;
		try {
			editor = theEditorType.newInstance();
		} catch (InstantiationException e) {
			logError( "Could not create an instance of type \""+theEditorType+"\": "+e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logError( "Could not create an instance of type \""+theEditorType+"\": "+e.getMessage(), e);
		}
		return editor;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends IParameterEditor<?>> getEditorTypeByClassPath(Class<?> type) {
		Class<? extends IParameterEditor<?>> editorType = null; 
		// TODO handle case type == null
		if(IParameterEditor.class.isAssignableFrom( type)) {
			
			boolean validConstructor = false;
			for(Constructor<?> constructor : type.getConstructors()) {
				if(constructor.getParameterTypes().length==0) {
					validConstructor = true;
				}
			}
			
			if(validConstructor) {
				editorType = (Class<? extends IParameterEditor<?>>) type;
			}
			else {
				logError( "Class \""+type+"\" has no valid constructor without parameters.", null);
			}
		}
		else {
			logError( "Class \""+type+"\" is not extending \""+IParameterEditor.class+"\".", null);
		}
		return editorType;
	}

	private void logError(String msg, Exception e) {
		ManagementPlugin
		.getSharedInstance()
		.eclipseLog(msg, e);
	}

}
