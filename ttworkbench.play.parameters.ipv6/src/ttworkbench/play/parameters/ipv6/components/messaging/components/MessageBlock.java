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
package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.IMessageRegistry;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageLabel;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/**
 * Groups several {@link MessageLabel} objects. Discriminates between tagged messages and not tagged messages.
 * If a message has a tag, a prior message with the same tag will be replaced while adding this new message. 
 * Otherwise messages simple be added to the block.    
 * 
 * @author Johannes Dahlke
 *
 */
public class MessageBlock {

	public enum RegisterDirective {
		REGISTER, NO_REGISTRATION 
	}
	
	private Map<String, MessageLabel> taggedMessageElements = new HashMap<String,MessageLabel>();
	private Set<String> agedMessageTags = new HashSet<String>();
	private List<MessageLabel> untaggedMessageElements = new ArrayList<MessageLabel>();
	
	private IMessageContainer messageContainer;
	private IMessageRegistry messageRegistry;
	
	public MessageBlock( final IMessageContainer theMessageContainer, final IMessageRegistry theMessageRegistry) {
		this.messageContainer = theMessageContainer;
		this.messageRegistry = theMessageRegistry;
	}
	
	private boolean tryRegister( MessageLabel theMessageLabel) {
		if ( messageRegistry == null)
			return false;
		messageRegistry.registerMessage( theMessageLabel);		
		return true;
	}
	
	public synchronized void putTaggedMessage( final MessageRecord theMessageRecord, final RegisterDirective theRegisterDirective) {
		MessageRecord msg = theMessageRecord;
		MessageLabel oldMessageLine = taggedMessageElements.remove( msg.tag);
		String oldMessageTag = (oldMessageLine != null) ? oldMessageLine.getMessageRecord().tag : null;
		
		// release old message control first
		if ( oldMessageLine != null)
			oldMessageLine.dispose();
		
		// add a success message only as an answer to a message of a prior cycle. 
		boolean answer = ( oldMessageTag != null && oldMessageTag.equals( msg.tag)) ||
				               agedMessageTags.contains( msg.tag);
		if ( !answer && msg.errorKind.equals( ErrorKind.success))
			return;

		if(!messageContainer.getMessageComposite().isDisposed()) {
			MessageLabel newMessageElement = new MessageLabel( messageContainer, msg);
			taggedMessageElements.put( theMessageRecord.tag, newMessageElement);
			
			if ( theRegisterDirective == RegisterDirective.REGISTER)
				tryRegister( newMessageElement);
		}
	}

	public synchronized void clearTaggedSuccessMessage( final String theTag) {
		MessageLabel messageLabel = taggedMessageElements.get( theTag);
		if ( messageLabel != null &&
				messageLabel.getErrorKind().equals( ErrorKind.success)) {
			taggedMessageElements.remove( theTag);
			messageLabel.dispose();
		}
	}
	
	public synchronized void clearTaggedMessage( final String theTag) {
		MessageLabel messageLabel = taggedMessageElements.remove( theTag);
		if ( messageLabel != null) {
			messageLabel.dispose();
		}
	}
	
	public void addUntaggedMessage( final MessageRecord theMessageRecord, final RegisterDirective theRegisterDirective) {
		MessageRecord msg = theMessageRecord;
		if ( msg.errorKind.equals( ErrorKind.success))
			return;
		
		MessageLabel newMessageLabel = new MessageLabel( messageContainer, theMessageRecord);
		untaggedMessageElements.add( newMessageLabel);
		
		if ( theRegisterDirective == RegisterDirective.REGISTER)
			tryRegister( newMessageLabel);
	}


	private void clearAllTaggedMessages() {
		for (MessageLabel messageLabel : taggedMessageElements.values()) {
			messageLabel.dispose();
		}
		taggedMessageElements.clear();
	}
	
	private void clearAllUntaggedMessages() {
		for (MessageLabel messageLabel : untaggedMessageElements) {
			messageLabel.dispose();
		}
		untaggedMessageElements.clear();
	}
	
	private void retainTags() {
		agedMessageTags.addAll( taggedMessageElements.keySet());
	}
	
	private void clearRetainedTags() {
		agedMessageTags.clear();
	}
		
	
	public synchronized void beginUpdateCycle() {
		retainTags();
		clearAllTaggedMessages();
		clearAllUntaggedMessages();
	}
		
	public void endUpdateCycle() {
		clearRetainedTags();
	}

	public List<String> getMessages( EnumSet<ErrorKind> theMessageKinds) {
		List<String> result = new ArrayList<String>();
		Collection<MessageLabel> messageLabels = taggedMessageElements.values();
		for (MessageLabel messageLabel : messageLabels) {
			if ( theMessageKinds.contains( messageLabel.getErrorKind()))
			  result.add( messageLabel.getMessage());
		}
		return result;
	}		
	
}
