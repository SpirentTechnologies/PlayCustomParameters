package ttworkbench.play.parameters.ipv6.components.messaging.data;

import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageRecord {
	
	public final String tag;
	
	public String message;
	
	public ErrorKind errorKind;
	
	public final Control errorSource;
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
		super();
		this.tag = theTag;
		this.message = theMessage;
		this.errorKind = theErrorKind;
		this.errorSource = null;
	}
	
	public MessageRecord( final String theMessage, final ErrorKind theErrorKind) {
		this( null, theMessage, theErrorKind);
	}

	public boolean hasTag() {
		return tag != null && !tag.isEmpty();
	}
	
	
	@Override
	public int hashCode() {
	  int hash = 1;
    hash = hash * 17 + tag.hashCode();
    hash = hash * 31 + message.hashCode();
    hash = hash * 13 + errorKind.hashCode();
    hash = hash * 23 + (errorSource == null ? 0 : errorSource.hashCode()); 
    return hash;
	}
	
	
	
}
