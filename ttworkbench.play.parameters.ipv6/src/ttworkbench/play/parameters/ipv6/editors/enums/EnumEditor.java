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
package ttworkbench.play.parameters.ipv6.editors.enums;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingCombo;
import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingRadio;
import ttworkbench.play.parameters.ipv6.valueproviders.EnumValueProvider;

import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.muttcn.kernel.Value;
import com.testingtech.muttcn.values.ConstantValue;
import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class EnumEditor<T extends Expression> extends ValidatingEditor<T> {

	private static final String TITLE = "Enum Editor";
	private static final String DESCRIPTION = "Enum Editor";

	private static final int ENUM_MAX_LENGTH = 20;
	private IVerifyingControl<? extends Control, T> inputControl;

	private final EnumValueProvider<T> enumValueProvider = new EnumValueProvider<T>();

	private final EnumContextVerifier enumContextVerifier = new EnumContextVerifier();

	public EnumEditor() {
		super( TITLE, DESCRIPTION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		// TODO Auto-generated method stub
		return new DefaultEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {
		// TODO Auto-generated method stub
		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();

		CLabel comboLabel = new CLabel( theContainer, SWT.LEFT);
		comboLabel.setText( getParameter().getId());
		comboLabel.setLayoutData( layoutData[0]);

		if (isBoolean( enumValueProvider.getAvailableValues( this.getParameter()))) {
			createRadioGroup( theContainer, layoutData[0]);
		} else {
			createCombo( theContainer, layoutData[0]);
		}
	}

	private void createRadioGroup(Composite theContainer, Object theLayoutData) {
		inputControl = new VerifyingRadio<T>( getParameter(), theContainer, SWT.SHADOW_IN, enumContextVerifier);

		final HashMap<String, T> availableValues = getAvailableValues();

		//Make a group for the Radio Buttons
		final Group enumRadioGroup = (Group) inputControl.getControl();
		enumRadioGroup.setLayout( new RowLayout( SWT.VERTICAL));

		//Create radio buttons according to the number of the available values
		for (final String key : availableValues.keySet()) {
			final Button radio = new Button( enumRadioGroup, SWT.RADIO);
			radio.setText( key);
			radio.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent theEvent) {
					if (radio.getSelection() == true) {
						getParameter().setValue( availableValues.get( key));
					}
				}
			});
		}
	}

	

	private HashMap<String, T> getAvailableValues() {
		final IParameter<T> parameter = this.getParameter();
		HashMap<String, T> availableValues = new HashMap<String, T>();
		for(T value : enumValueProvider.getAvailableValues( parameter)) {
			String content = null;
			
			if(value instanceof StringValue) {
				content = ((StringValue) value).getTheContent();
			}
			else if(value instanceof Expression) {
				content = ((Expression) value).getTheName().getTheName();
			}
			else {
				System.err.println("Could not identify \""+value+"\".");
			}
			
			if(value!=null) {
				availableValues.put( content!=null ? content : "", value);
			}
		}	
		return availableValues;
	}
	
	private void createCombo(Composite theContainer, Object theLayoutData) {
		inputControl = new VerifyingCombo<T>( getParameter(), theContainer, SWT.READ_ONLY, enumContextVerifier);

		final Combo enumCombo = (Combo) inputControl.getControl();
		final Rectangle dimensions = new Rectangle( 50, 50, 200, 65);
		enumCombo.setBounds( dimensions);
		setWidthForText( enumCombo, ENUM_MAX_LENGTH);
		enumCombo.setTextLimit( ENUM_MAX_LENGTH);


		final HashMap<String, T> availableValues = getAvailableValues();
		int index = 0;
		for (String key : availableValues.keySet()) {
			if(key!=null) {
				enumCombo.add( key, index++);
				// System.out.println( key);
			}
		}

		enumCombo.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent theEvent) {
					T v = availableValues.get(enumCombo.getText());
					getParameter().setValue( v);
				}
			});
	}

	private void setWidthForText(Combo theComboControl, int visibleChars) {
		GC gc = new GC( theComboControl);
		int charWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		int minWidth = visibleChars * charWidth;
		Object layout = theComboControl.getLayoutData();
		if (layout instanceof GridData)
			( (GridData) layout).minimumWidth = minWidth + 20;
		if (layout instanceof RowData)
			( (RowData) layout).width = minWidth + 20;
		else
			theComboControl.setSize( theComboControl.computeSize( minWidth + 20, SWT.DEFAULT));
	}

	private <T> boolean isBoolean(Set<T> values) {
		return ( values.size() < 3);
	}
	

	@Override
	public void reloadParameter() {
		// TODO Auto-generated method stub
		
	}

}
