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

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.testingtech.ttworkbench.metamodel.muttcn.generator.CLTextGenerator;
import com.testingtech.ttworkbench.ttman.ManagementPlugin;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;



/**
 * This class provides the labels for the parameters table
 */
public class TableViewerLabelProvider implements ITableLabelProvider {
	/**
	 * Returns the image
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return Image
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	private LinkedList<ParameterEditorColumnType> registeredColumns = new LinkedList<ParameterEditorColumnType>(); 
	public void addColumn(ParameterEditorColumnType columnType) {
		registeredColumns.add( columnType);
	}
	
	/**
	 * Returns the column text
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return String
	 */
	public String getColumnText(Object element, int columnIndex) {
		IParameterEditor<?> editor = (IParameterEditor<?>) element;
		Object value = registeredColumns.get( columnIndex).valueOf( editor);
		return getCellText(value);
	}
	
	
	public static String getCellText(Object obj) {
		String out = null;
		if (obj instanceof IParameter<?>) {
			out = getCellText( ( (IParameter<?>) obj).getValue());
		}
		else if (obj instanceof EObject) {
			out = CLTextGenerator.getUnformattedText( (EObject) obj, ManagementPlugin.getRepositoryView());
		}
		else {
			out = String.valueOf( obj);
		}
		if(out.startsWith( "\"") && out.endsWith( "\"")) {
			out = out.substring( 1, out.length()-1);
		}
		return out;
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(ILabelProviderListener listener) {
		// Ignore it
	}

	/**
	 * Disposes any created resources
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * Returns whether altering this property on this element will affect
	 * the label
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener listener) {
		// Ignore
	}

}
