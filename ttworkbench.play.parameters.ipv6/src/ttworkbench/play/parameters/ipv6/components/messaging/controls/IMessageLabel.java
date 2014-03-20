package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageLabel {
	
	void setMessage( final String theMessage, final ErrorKind theErrorKind);

	ErrorKind getErrorKind();
	
	String getMessage();
	
	boolean hasTag();
}
