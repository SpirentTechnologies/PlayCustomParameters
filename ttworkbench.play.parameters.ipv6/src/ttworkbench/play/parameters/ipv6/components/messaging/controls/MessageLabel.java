package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import java.awt.Toolkit;
import java.util.EnumSet;
import java.util.HashSet;
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

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
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
			focusEditorForParameter( messageRecord.causer, true);
	}

	private boolean focusEditorForParameter(IParameterControl<? extends Control,?> theParameterControl, boolean tryJumpToItem) {
		if ( theParameterControl.getControl().isVisible()) {
			theParameterControl.getControl().setFocus();
			return true;
		}
		
		// try to find an editor for this parameter on the current active widget (tab)
		if ( Globals.hasConfiguration()) {
			Set<IParameterEditor> editors = Globals.getConfiguration().getEditors( theParameterControl.getParameter());
			for ( IParameterEditor editor : editors) {
				if ( editor.isVisible() && editor instanceof AbstractEditor) {
					((AbstractEditor)editor).setFocus();
					return true;
				}
			}
		}
		
		// if there was no suitable editor, we try to activate the tab 
		// where the causer control is placed on.
		if ( tryJumpToItem && selectNextParentTabOfControl( theParameterControl.getControl()))
		  // try again
		  focusEditorForParameter( theParameterControl, false);
		return false;
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