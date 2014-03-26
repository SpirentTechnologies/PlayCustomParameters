package ttworkbench.play.parameters.ipv6.components.messaging.data;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageRecord {
	
	public final String tag;
	
	public String message;
	
	public ErrorKind errorKind;
	
	public final IParameterControl<?,?> causer;
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind, final IParameterControl<?,?> theCauser) {
		super();
		this.tag = theTag;
		this.message = theMessage;
		this.errorKind = theErrorKind;
		this.causer = theCauser;
	}
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
	  this( theTag, theMessage, theErrorKind, null);
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
    hash = hash * 17 + (tag == null ? 0 : tag.hashCode());
    hash = hash * 31 + message.hashCode();
    hash = hash * 13 + errorKind.hashCode();
    hash = hash * 23 + (causer == null ? 0 : causer.hashCode()); 
    return hash;
	}
	
	@Override
	public String toString() {
		 return String.format( "MessageRecord@%d( tag: %s, message: %s, errorkind: %s, causer: %s)",hashCode(), tag, message, errorKind.toString(), causer);
	}
	
	
	
}
