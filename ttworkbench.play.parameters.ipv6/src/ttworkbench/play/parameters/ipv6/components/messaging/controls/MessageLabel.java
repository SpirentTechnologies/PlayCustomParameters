package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import java.awt.Toolkit;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.components.messaging.components.MessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.components.messaging.views.EditorMessageDisplay;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

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
	
	
}