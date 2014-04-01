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

import java.awt.Toolkit;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import ttworkbench.play.parameters.ipv6.widgets.AbstractWidget;
import ttworkbench.play.parameters.ipv6.widgets.WidgetUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageLabel extends Composite implements IMessageLabel {
	/**
	 * 
	 */
	private final MessageRecord messageRecord;
	private final CLabel label;
	private final IMessageContainer messageContainer;
	private IMessageLookAndBehaviour lookAndBehaviour;
	private Set<MessageChangeListener> changeListeners = new HashSet<MessageChangeListener>();
	
	public MessageLabel( final IMessageContainer theMessageContainer, final MessageRecord theMessageRecord) {
		super( theMessageContainer.getMessageComposite(), SWT.NONE);
		messageRecord = theMessageRecord;
		
		this.messageContainer = theMessageContainer;
		//this.messageRecord = theMessageRecord;
		this.setLayout( new FillLayout());
		this.lookAndBehaviour = messageContainer.getMessageLookAndBehaviour();

		setDefaultLayoutData();
		label = createLabel();
	  setMessage( getMessage(), getErrorKind());
	  //System.out.println( String.format( "MessageLabel@%d( %s)",hashCode(), messageRecord.toString()));
	}
	
	private void setDefaultLayoutData() {
		setLayoutData( messageContainer.getMessageLayoutData());
	}
	
	private CLabel createLabel() {
		CLabel label = new CLabel( this, SWT.NONE);
	  label.setFont( lookAndBehaviour.getMessageFont( getErrorKind()));//new Font( Display.getCurrent(), fontData[0]));
		label.setImage( lookAndBehaviour.getMessageImage( getErrorKind()));
		return label;
	}
	
	public void addChangeListener( final MessageChangeListener theChangeListener) {
		changeListeners.add( theChangeListener);
	}
	
	public void setMessage( final String theMessage, final ErrorKind theErrorKind) {
		// set content
		messageRecord.message = theMessage;
		label.setText( theMessage);
	

		// set look
		messageRecord.errorKind = theErrorKind;
		Color foregroundColor = lookAndBehaviour.getMessageForeground( theErrorKind);
		Color backgroundColor = lookAndBehaviour.getMessageBackground( theErrorKind);
		label.setForeground( foregroundColor);
		label.setBackground( backgroundColor);

		// set behaviour
		if ( lookAndBehaviour.isBeepEnabled())
			if ( EnumSet.of( ErrorKind.error, ErrorKind.warning).contains( theErrorKind))
				beep();
		
		messageChanged();
	}
	
	private void messageChanged() {
		for ( MessageChangeListener changeListener : changeListeners) {
			changeListener.messageChange( new MessageChangeEvent( this, messageRecord.message, messageRecord.errorKind));
		}
	}

	private void beep() {
		if ( lookAndBehaviour.isBeepEnabled())
		  Toolkit.getDefaultToolkit().beep();
	}

	public ErrorKind getErrorKind() {
		return messageRecord.errorKind;
	}
	
	public String getMessage() {
		return messageRecord.message;
	}

	public boolean hasTag() {
		return messageRecord.hasTag();
	}

	public int getMessageCode() {
		return messageRecord.hashCode();
	}

	public MessageRecord getMessageRecord() {
		return messageRecord;
	}

	@Override
	public void navigateToCauser() {
		System.out.println( "Causer: " + messageRecord.causer);
		if ( messageRecord.causer != null)
			focusBestEditorForCauser();
	}
	
	/**
	 * Tests, if an editor for the message causer is placed and visible on the specified widget. 
	 * @param theWidget
	 * @return
	 */
	@Override
	public boolean isCauserOnWidgetVisible( final IWidget theWidget) {
		if ( messageRecord.causer == null)
			return false;
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsOfParameterMap = WidgetUtil.getWidgetToEditorsOfParameterMap( messageRecord.causer);
		if ( !( theWidget instanceof AbstractWidget))
			return false;
		List<AbstractEditor<?>> widgetEditors = widgetToEditorsOfParameterMap.get( theWidget);
		for (AbstractEditor<?> abstractEditor : widgetEditors) {
			if ( abstractEditor.isVisible())
				return true;
		}
		return false;
	}
	
	
	public boolean focusBestEditorForCauser() {
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsOfParameterMap = WidgetUtil.getWidgetToEditorsOfParameterMap( messageRecord.causer);
		if ( widgetToEditorsOfParameterMap.size() == 0)
			return false;
		
		AbstractWidget bestWidget = null;
		AbstractEditor<?> bestEditor = null;
		for ( AbstractWidget widget : widgetToEditorsOfParameterMap.keySet()) {
			
			// find best editor
			bestEditor = null;
		  List<AbstractEditor<?>> editors = widgetToEditorsOfParameterMap.get( widget);
			for (AbstractEditor<?> abstractEditor : editors) {
				if ( abstractEditor.isVisible()) {
					bestEditor = abstractEditor;
				  break;
				}
			}
			if ( bestEditor == null)
				continue;
			
			// find best widget
			if ( !widget.isVisible())
				continue;
			if ( !widget.hasControl())
				continue;
			
			// best case: an editor for this parameter on the active tab 
			if ( widget.isSelected()) {
				bestWidget = widget;
			  break;
			}
			
			if ( bestWidget == null ||
				   // second best case: the widget with the original error sender
					WidgetUtil.isGrantParentOfChild( widget.getControl(), messageRecord.causer.getControl()))
				bestWidget = widget;
			
		}
		
		if ( bestEditor == null)
			return false;
		
		Control bestWidgetControl = bestWidget.getControl();
		if ( !bestWidget.isSelected())
			WidgetUtil.selectNextParentTabOfControl( bestWidgetControl);
		
		if ( WidgetUtil.isGrantParentOfChild( bestWidgetControl, messageRecord.causer.getControl()))
			messageRecord.causer.getControl().setFocus();
		else
			bestEditor.setFocus();
		
		return true;
	}




	
	
	
	
}
