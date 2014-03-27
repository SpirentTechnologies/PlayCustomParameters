
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

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class CellParameterEditor<T extends Value> extends DialogCellEditor {
	
	private static final boolean SHOW_ONLY_VALUE_IN_TABLE = true;
	
	private Composite editorComposite;


	public CellParameterEditor( Composite parent) {
		super( parent);
	}

	public abstract IParameterEditor<T> getEditor();


	@Override
	protected Control createContents(Composite cell) {
		if(SHOW_ONLY_VALUE_IN_TABLE) {
			Label label = new Label(cell, SWT.NONE);
			label.setText( String.valueOf( getEditor().getParameter().getValue()));
			return label;
		}
		else {
			editorComposite = getEditor().createControl( cell);
			editorComposite.setBackground(cell.getBackground());
			editorComposite.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent event) {
					 setValueToModel();
				}
			});
			editorComposite.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent event) {
					keyReleaseOccured(event);
				}
			});
			return editorComposite;
		}
	}

	@Override
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.keyCode == SWT.CR || keyEvent.keyCode == SWT.KEYPAD_CR) {
			setValueToModel();
		}
		super.keyReleaseOccured(keyEvent);
	}

	protected void setValueToModel() {
		Object value = getEditor().getParameter().getValue();
        boolean newValidState = isCorrect(value);
        if (newValidState) {
            markDirty();
            doSetValue(value);
        } else {
            setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { value.toString() }));
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void updateContents(Object value) {
		if (editorComposite != null) {
			getEditor().getParameter().setValue( (T) value);
		}
	}

	@Override
	protected void doSetFocus() {
		if (editorComposite != null) {
			editorComposite.setFocus();
		}
	}


	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		IParameterEditor<T> editor = getEditor();
		
		Dialog dialog = new CellParameterEditorDialog(cellEditorWindow.getShell(), editor);
		if (dialog.open() == Window.OK) {
			return editor.getParameter().getValue();
		} else {
			return null;
		}
	}
}
