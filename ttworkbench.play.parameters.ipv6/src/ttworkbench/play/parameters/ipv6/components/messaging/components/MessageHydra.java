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
package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageLabel;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageLabel;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageHydra implements IMessageHydra {

	private MessageRecord messageRecord;
	
	private final Set<MessageLabel> messageLabels = new HashSet<MessageLabel>();
	
	public MessageHydra( final MessageRecord theMessageRecord) {
		super();
		this.messageRecord = theMessageRecord;
	}
	
	
	@Override
	public IMessageLabel newLabel( IMessageContainer theMessageContainer) {
		MessageLabel newMessageLabel = new MessageLabel( theMessageContainer, messageRecord);
		messageLabels.add( newMessageLabel);
		newMessageLabel.addDisposeListener( new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent theDisposeEvent) {
				messageLabels.remove( theDisposeEvent.widget);
				disposeLabels();
			}
		});
		return newMessageLabel;
	}
	
	@Override
	public void disposeLabels() {
		for ( Widget widget : messageLabels) {
			if ( !widget.isDisposed())
		  	widget.dispose();
		}
	}
	
	@Override
	public int getCountOfLabels() {
		return messageLabels.size();
	}

	@Override
	public ErrorKind getErrorKind() {
		return messageRecord.errorKind;
	}
	
	@Override
	public String getMessage() {
		return messageRecord.message;
	}
	
	@Override
	public boolean hasTag() {
		return messageRecord.hasTag();
	}
	
	public MessageRecord getMessageRecord() {
		return messageRecord;
	}

  @Override
	public void messageChanged( final MessageLabel theSender) {
		messageRecord = theSender.getMessageRecord();
		for ( MessageLabel label : messageLabels) {
			if ( label != theSender)
		  	label.setMessage( getMessage(), getErrorKind());
		}
	}
	
	
	
	

}
