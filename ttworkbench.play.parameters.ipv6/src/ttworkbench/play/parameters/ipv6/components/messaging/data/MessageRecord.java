package ttworkbench.play.parameters.ipv6.components.messaging.data;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageRecord {
	
	public final String tag;
	
	public final String message;
	
	public final ErrorKind errorKind;
	
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
		super();
		this.tag = theTag;
		this.message = theMessage;
		this.errorKind = theErrorKind;
	}
	
	public MessageRecord( final String theMessage, final ErrorKind theErrorKind) {
		super();
		this.tag = null;
		this.message = theMessage;
		this.errorKind = theErrorKind;
	}
	
	public boolean isTagged() {
		return tag != null;
	}
	
	
	
	
}
