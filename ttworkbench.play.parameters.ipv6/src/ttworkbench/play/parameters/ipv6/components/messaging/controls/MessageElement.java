package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.components.messaging.views.MessageDisplay;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageElement extends Composite {
	/**
	 * 
	 */
	private String message;
	private ErrorKind errorKind;
	private CLabel label;
	private IMessageContainer messageContainer;
	private IMessageLookAndBehaviour lookAndBehaviour;
	
	public MessageElement( final IMessageContainer theMessageContainer, final String theMessage, final ErrorKind theErrorKind) {
		super( theMessageContainer.getMessageComposite(), SWT.NONE);
		this.messageContainer = theMessageContainer;
		this.setLayout( new FillLayout());
		this.lookAndBehaviour = messageContainer.getMessageLookAndBehaviour();
		
		setDefaultLayoutData();
		
		label = new CLabel( this, SWT.NONE);
	  label.setFont( lookAndBehaviour.getMessageFont( theErrorKind));//new Font( Display.getCurrent(), fontData[0]));
		label.setImage( lookAndBehaviour.getMessageImage( theErrorKind));
	  setMessage( theMessage, theErrorKind);
	
	}
	
	private void setDefaultLayoutData() {
		setLayoutData( messageContainer.getMessageLayoutData());
	}

	public void setMessage( String theMessage, ErrorKind theErrorKind) {
    // set content
		this.message = theMessage;
    label.setText( message);

    
    // set look
    this.errorKind = theErrorKind;
    Color foregroundColor = lookAndBehaviour.getMessageForeground( errorKind);
    Color backgroundColor = lookAndBehaviour.getMessageBackground( errorKind);
    label.setForeground( foregroundColor);
    label.setBackground( backgroundColor);
    
    // set behaviour
    switch ( errorKind) {
			case error:
			case warning:
				beep();
				break;
		}
		//getParent().layout( true);
	}
	
	private void beep() {
		if ( lookAndBehaviour.isBeepEnabled())
		  Toolkit.getDefaultToolkit().beep();
	}

	public ErrorKind getErrorKind() {
		return errorKind;
	}
	
	public String getMessage() {
		return message;
	}
	
}