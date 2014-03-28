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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;


/**
 * This class implements the sorting for the Player Table
 */

public class TableViewerSorter extends ViewerSorter {
	private static final int ASCENDING = 0;
	private static final int DESCENDING = 1;
	private ParameterEditorColumnType column;
	private int direction;

	public TableViewerSorter() {
		super();

	}
	
	/**
	 * Does the sort. If it's a different column from the previous sort, do
	 * an ascending sort. If it's the same column as the last sort, toggle
	 * the sort direction.
	 * 
	 * @param column
	 */
	public void doSort(ParameterEditorColumnType column) {
		if (column == this.column) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.column = column;
			direction = ASCENDING;
		}
	}

	/**
	 * Compares the object for sorting
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {
		IParameterEditor<?> p1 = (IParameterEditor<?>) e1;
		IParameterEditor<?> p2 = (IParameterEditor<?>) e2;

		int rc = column!=null
				? String.valueOf( column.valueOf( p1)).compareTo( String.valueOf( column.valueOf( p2)))
				: 0;
				
		// If descending order, flip the direction
		if (direction == DESCENDING)
			rc = -rc;

		return rc;
	}
}

