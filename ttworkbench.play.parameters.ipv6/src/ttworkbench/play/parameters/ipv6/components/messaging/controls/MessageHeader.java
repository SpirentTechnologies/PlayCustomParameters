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
package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

public class MessageHeader extends Composite implements IMessageContainer {

	/**
	 * 
	 */
	private final IMessageView<?> messageView;

	public MessageHeader( IMessageView<?> theMessageView, final Composite theParent) {
		super( theParent, SWT.NONE);
		this.messageView = theMessageView;
		
		RowLayout messageHeaderLayout = new RowLayout();
		messageHeaderLayout.spacing = 0;
		messageHeaderLayout.marginWidth = 1;
		messageHeaderLayout.marginTop = 3;
		messageHeaderLayout.marginBottom = 0;
		setLayout( messageHeaderLayout);		
	}

	@Override
	public Composite getMessageComposite() {
		return this;
	}

	@Override
	public IMessageLookAndBehaviour getMessageLookAndBehaviour() {
		return messageView.getLookAndBehaviour().getFlashMessageLookAndBehaviour();
	}
	
	@Override
	public Object getMessageLayoutData() {
		return new RowData();
	}
	

	
	
}
