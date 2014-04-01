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
package ttworkbench.play.parameters.ipv6.components.messaging.views;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.IMessageInformation;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IMessageViewLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageView<ClientComponent> {

	/**
	 * Finishes an update cycle. 
	 */
	void endUpdate();

	/**
	 * Starts an update cycle for a specific sender.
	 *  
	 * @param theSenderId
	 */
	void beginUpdateForSender(Object theSenderId);

	/**
	 * Flashes a message for a short term. Methods {@link #beginUpdateForSender()} and {@link #endUpdate()} didn't affect {@link #flashMessage()}.
	 */
	void flashMessage( MessageRecord theMessageRecord);
	void flashMessages( Collection<MessageRecord> theMessageRecords);
	
	void showMessage( MessageRecord theMessageRecord);
	void showMessages( Collection<MessageRecord> theMessageRecords);

	/**
	 * Forces removal of the message specified by the given tag.  
	 * @param theTag
	 */
	void clearMessagesByTag(String theTag);
	
		/**
	 * Fetches all messages on this panel of specified error kind.
	 * 
	 * @param theMessageKinds Set of error kinds considered in the result.
	 * @return a list of messages compiled out of messages matches the specified error kinds.  
	 */
	List<String> getMessages(EnumSet<ErrorKind> theMessageKinds);
	
	IMessageViewLookAndBehaviour getLookAndBehaviour();
	
	void setLookAndBehaviour( IMessageViewLookAndBehaviour theLookAndBehaviour);
	
	void setClientComponent( ClientComponent theClientComponent);
	
	IMessageInformation getMessageInformation();

	void setSuperiorView(IMessageView<?> theMessageView);

	void addMessageListener(MessageListener theMessageListener);


}
