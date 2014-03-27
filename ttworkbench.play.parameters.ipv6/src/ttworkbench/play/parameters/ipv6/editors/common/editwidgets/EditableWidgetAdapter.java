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
package ttworkbench.play.parameters.ipv6.editors.common.editwidgets;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

@Deprecated
public class EditableWidgetAdapter {
	
	private static final Map<Class<? extends Widget>,IEditableWidgetAdapter> supportedWidgets = new HashMap<Class<? extends Widget>,IEditableWidgetAdapter>();
	private static final IEditableWidgetAdapter[] widgetAdapters = {
	  new TextAdapter(),
	  new SpinnerAdapter()
	};
	
	static {
		for (IEditableWidgetAdapter widgetAdapter : widgetAdapters) {
			supportedWidgets.put( widgetAdapter.getSupportedWidget(), widgetAdapter);
		
		}
	}
	
	

	public static void setTextForWidget( final String theText, final Widget theWidget) throws UnsupportedWidgetException {
		if ( !isWidgetSupported( theWidget))
			throw new UnsupportedWidgetException();
		supportedWidgets.get( theWidget.getClass()).setTextForWidget( theText, theWidget);
	}
	
	public static String getTextFromWidget( final Widget theWidget) throws UnsupportedWidgetException {
		if ( !isWidgetSupported( theWidget))
			throw new UnsupportedWidgetException();
		return supportedWidgets.get( theWidget.getClass()).getTextFromWidget( theWidget);
	}
	
	public static String getTextByEvent( final Event theEvent) throws UnsupportedWidgetException, UnhandledEventException {
		if ( !isWidgetSupported( theEvent.widget))
			throw new UnsupportedWidgetException();
		return supportedWidgets.get( theEvent.widget.getClass()).getTextByEvent( theEvent);
	}

	private static boolean isWidgetSupported( Widget theWidget) {
		return supportedWidgets.containsKey( theWidget.getClass());
	}
	

}
