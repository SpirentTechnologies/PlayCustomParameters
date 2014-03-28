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
package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.HashMap;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

/*
 * This class contains constants for the Parameter application
 */
public enum ParameterEditorColumnType {
	COLUMN_EDITOR_TITLE,
	COLUMN_EDITOR_DESCRIPTION,
	COLUMN_PARAMETER_ID,
	COLUMN_PARAMETER_NAME,
	COLUMN_PARAMETER_VALUE, 
	COLUMN_PARAMETER_DEFAULT,
	COLUMN_PARAMETER_TYPE,
	COLUMN_PARAMETER_DESCRIPTION;

	public enum TableColumnEditor {
		TEXTBOX, COMBOBOX, CHECKBOX, NONE
	}

	private interface ParameterEditorSelect {
		Object get(IParameterEditor<?> editor);
	}

	private static HashMap<ParameterEditorColumnType, ParameterEditorSelect> valueSelectors = new HashMap<ParameterEditorColumnType, ParameterEditorSelect>();

	static {
		valueSelectors.put( ParameterEditorColumnType.COLUMN_EDITOR_TITLE,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getTitle();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_EDITOR_DESCRIPTION,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getDescription();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_ID,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getParameter().getId();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_NAME,
				new ParameterEditorSelect() {
					@Override
					public String get(IParameterEditor<?> editor) {
						return editor.getParameter().getName();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_VALUE,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						IParameter<?> parameter = editor.getParameter();
						String value = ParameterValueUtil.getValue( parameter);
						return value;
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_DEFAULT,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						IParameter<?> parameter = editor.getParameter();
						String value = ParameterValueUtil.getDefaultValue( parameter);
						return value;
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_TYPE,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						return editor.getParameter().getType();
					}
				});
		valueSelectors.put( ParameterEditorColumnType.COLUMN_PARAMETER_DESCRIPTION,
				new ParameterEditorSelect() {
					@Override
					public Object get(IParameterEditor<?> editor) {
						return editor.getParameter().getDescription();
					}
				});
	}


	public Object valueOf(IParameterEditor<?> editor) {
		ParameterEditorSelect selector = valueSelectors.get( this);
		return selector != null ? selector.get( editor) : null;
	}
}
