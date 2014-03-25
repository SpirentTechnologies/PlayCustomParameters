package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.Globals;
import ttworkbench.play.parameters.ipv6.common.IParameterControl;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import ttworkbench.play.parameters.ipv6.widgets.AbstractWidget;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
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
	public boolean isCauserOnWidgetVisible( final IWidget theWidget) {
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsOfParameterMap = getWidgetToEditorsOfParameterMap( messageRecord.causer);
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
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsOfParameterMap = getWidgetToEditorsOfParameterMap( messageRecord.causer);
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
					 isGrantParentOfChild( widget.getControl(), messageRecord.causer.getControl()))
				bestWidget = widget;
			
		}
		
		if ( bestEditor == null)
			return false;
		
		Control bestWidgetControl = bestWidget.getControl();
		if ( !bestWidget.isSelected())
			selectNextParentTabOfControl( bestWidgetControl);
		
		if ( isGrantParentOfChild( bestWidgetControl, messageRecord.causer.getControl()))
			messageRecord.causer.getControl().setFocus();
		else
			bestEditor.setFocus();
		
		return true;
	}

	/**
	 * retrieves a mapping of AbstractWidgets to the AbstractEditors on them. 
	 * @return
	 */
	private Map<AbstractWidget, List<AbstractEditor<?>>> getWidgetToEditorsMap() {
		Map<AbstractWidget, List<AbstractEditor<?>>> result = new HashMap<AbstractWidget, List<AbstractEditor<?>>>();
		List<AbstractEditor<?>> abstractEditors;
		List<IParameterEditor<?>> editors;

		if ( Globals.hasConfiguration()) {
			Set<IWidget> widgets = Globals.getConfiguration().getWidgets();
			for (IWidget widget : widgets) {
				if ( widget instanceof AbstractWidget) {
					abstractEditors = new ArrayList<AbstractEditor<?>>();
					editors = ((AbstractWidget) widget).acquireEditors();
					for (IParameterEditor<?> editor : editors) {
						if ( editor instanceof AbstractEditor)
							abstractEditors.add( (AbstractEditor<?>) editor);
					}
					result.put( (AbstractWidget) widget, abstractEditors);
				}
			}
		}
		return result;
	}

	private Map<AbstractWidget, List<AbstractEditor<?>>> getWidgetToEditorsOfParameterMap( IParameterControl<? extends Control,?> theParameterControl) {
		Map<AbstractWidget, List<AbstractEditor<?>>> result = new HashMap<AbstractWidget, List<AbstractEditor<?>>>();
		Set<IParameterEditor> parameterEditors = Globals.getConfiguration().getEditors( theParameterControl.getParameter());
		List<AbstractEditor<?>> widgetEditors;
		
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsMap = getWidgetToEditorsMap();
		for ( AbstractWidget widget : widgetToEditorsMap.keySet()) {
			widgetEditors = widgetToEditorsMap.get( widget);
			if ( !Collections.disjoint( widgetEditors, parameterEditors)) {
				widgetEditors.retainAll( parameterEditors);
				result.put( widget, widgetEditors);
			}
		}
		return result;
	}

	
	
	private boolean isGrantParentOfChild( final Control theParent, final Control theChild) {
		Control currentControl = theChild;
		while ( currentControl != null && currentControl != theParent) {
			currentControl = currentControl.getParent();
		}
		return currentControl == theParent;
	}

	private boolean selectNextParentTabOfControl(Control theControl) {
		Control currentControl = theControl;
		Control lastControl = null;
		while ( currentControl != null &&
				    !(currentControl instanceof CTabFolder)) {
			lastControl = currentControl;
			currentControl = currentControl.getParent();
		}
		
	  if ( currentControl != null &&
	  		 currentControl instanceof CTabFolder) {
	    CTabFolder tabFolder = (CTabFolder) currentControl;
	    CTabItem[] items = tabFolder.getItems();
	    for (CTabItem item : items) {
	    	if ( item.getControl() == lastControl) {
	    		tabFolder.setSelection( item);
	    		return true;
	    	}
	    }
	  }
	  
	  return false;
	}
	
	
}