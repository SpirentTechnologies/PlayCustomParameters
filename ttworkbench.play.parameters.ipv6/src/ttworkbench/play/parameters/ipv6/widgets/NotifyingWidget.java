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
package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;

import com.testingtech.ttworkbench.ttman.parameters.api.IActionHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultAction;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public abstract class NotifyingWidget extends AbstractWidget implements IMessageHandler, IActionHandler {

	public NotifyingWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	
	protected abstract IMessageView<?> getMessageView();
	
	
	

	@Override
	public void report( final IParameterValidator theValidator, final List<ValidationResultMessage> theValidationResults, final IParameter<?> theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IMessageView<?> messageView = getMessageView();
				String senderId = String.valueOf( theValidator.getId());
				messageView.beginUpdateForSender( senderId);
				MessageRecord messageRecord;
				for (ValidationResult validationResult : theValidationResults) {
					messageRecord = new MessageRecord( validationResult.getTag(), validationResult.getErrorMessage(), validationResult.getErrorKind());
					messageView.showMessage( messageRecord);
				}
				messageView.endUpdate();
			}
		});
		
	}


	@Override
	public void trigger(final IParameterValidator theValidator, final List<ValidationResultAction> theActions, IParameter<?> theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				for (ValidationResultAction action : theActions) {
					action.triggerWidget( NotifyingWidget.this);
				}
			}
		});
	}
	
}
