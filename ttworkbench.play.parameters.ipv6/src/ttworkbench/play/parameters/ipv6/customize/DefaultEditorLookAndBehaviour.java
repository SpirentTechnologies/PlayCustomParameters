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
package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class DefaultEditorLookAndBehaviour implements IValidatingEditorLookAndBehaviour {

	private Set<Listener> controlChangedListeners = new HashSet<Listener>();

	@Override
	public Layout getLayout() {
		GridLayout gridLayout = new GridLayout( 3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;	
		return gridLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		GridData[] gridData = new GridData[1];
		gridData[0] = new GridData( SWT.LEFT, SWT.TOP, true, false);
		return gridData;
	}

	@Override
	public IEditorLookAndBehaviour getEditorLookAndBehaviour() {
		return this;
	}

	@Override
	public IMessageViewLookAndBehaviour getMessagePanelLookAndBehaviour() {
		return new DefaultMessageViewLookAndBehaviour();
	}

	@Override
	public void addControlChangedListener(Listener theControlChangedListener) {
		this.controlChangedListeners.add( theControlChangedListener);
	}

	@Override
	public void doOnChange() {
		for (Listener controlChangedListener : controlChangedListeners) {
			controlChangedListener.handleEvent( new Event());
		}
	}

	@Override
	public long getStartValidationDelay() {
		// TODO Auto-generated method stub
		return 1500;
	}

	@Override
	public long getShowValidationInProgressMessageDelay() {
		// TODO Auto-generated method stub
		return 2000;
	}

}
