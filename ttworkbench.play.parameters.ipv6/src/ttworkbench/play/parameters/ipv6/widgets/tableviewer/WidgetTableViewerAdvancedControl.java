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
package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.HashSet;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.impl.ParameterChangedListener;

public class WidgetTableViewerAdvancedControl extends WidgetTableViewerControl {
	
	private HashSet<IParameterEditor<?>> registeredListeners = new HashSet<IParameterEditor<?>>();
	
	public WidgetTableViewerAdvancedControl(Composite parent) {
		super(parent);
	}
	

	@Override
	protected TableColumn createTableViewerColumn(final ColumnDefinition coldef) {
		TableColumn tc = super.createTableViewerColumn( coldef);
		final TableViewerColumn tvc = new TableViewerColumn( getTableViewer(), tc);
		tvc.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Object obj = cell.getElement();
				if(obj instanceof IParameterEditor<?>) {
					IParameterEditor<?> editor = (IParameterEditor<?>) obj;
					checkCellListener(editor);
					cell.setText( String.valueOf( coldef.columnType.valueOf( editor)));
				}
			}
		});
		
		
		if(coldef.columnType == ParameterEditorColumnType.COLUMN_PARAMETER_VALUE) {
			tvc.setEditingSupport( new EditingSupport(getTableViewer()) {

				@Override
				protected void setValue(Object entity, Object value) {
					if(entity instanceof IParameterEditor<?>) {
						IParameterEditor<?> editor = (IParameterEditor<?>) entity;
						Object currValue = editor.getParameter().getValue();
						if(!currValue.equals( value)) {
							ParameterValueUtil.setValue( editor.getParameter(), String.valueOf( value));
						}
					}
					getTableViewer().refresh();
				}
				
				@Override
				protected Object getValue(Object obj) {
					Object out = null;
					if(obj instanceof IParameterEditor<?>) {
						IParameterEditor<?> editor = (IParameterEditor<?>) obj;
						out = coldef.columnType.valueOf(editor);
					}
					return out;
				}
				
				@Override
				protected CellEditor getCellEditor(final Object obj) {
					Table table = getTableViewer().getTable();
					if(obj instanceof IParameterEditor) {
						return new CellParameterEditor(table) {
							
							@SuppressWarnings("unchecked")
							public IParameterEditor<Object> getEditor() {
								return (IParameterEditor<Object>) obj;
							}
						};
					}
					return new TextCellEditor(table);
				}
				
				@Override
				protected boolean canEdit(Object obj) {
					boolean canEdit = obj instanceof IParameterEditor<?>; // && ((IParameterEditor<?>) obj).isEnabled();
					return canEdit;
				}
			});
		}
		
		return tc;
	}


	protected <T> void checkCellListener(IParameterEditor<T> editor) {
		if(registeredListeners.add( editor)) {
			IParameter<T> parameter = editor.getParameter();
			parameter.addParameterChangedListener( new ParameterChangedListener<T>() {
				@Override
				public void parameterChanged(IParameter<T> theTheParameter) {
					getTableViewer().refresh();
				}
			});
		}
		
	}
}
