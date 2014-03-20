package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageChangeEvent {
	
	public final String message; 
	public final ErrorKind errorKind;
	public final MessageLabel sender;
	
	MessageChangeEvent( final MessageLabel theSender, final String theMessage, final ErrorKind theErrorKind) {
		super();
		this.sender = theSender;
		this.message = theMessage;
		this.errorKind = theErrorKind;
	}

}
