package ttworkbench.play.parameters.ipv6.components.messaging.components;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageElement;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class RegistryEvent {
	
	public final IMessageInformation registry;
	public final MessageElement messageElement;
	public final ErrorKind errorKind;
	
	public RegistryEvent( final IMessageInformation theRegistry, final MessageElement theMessageElement) {
		super();
		this.registry = theRegistry;
		this.messageElement = theMessageElement;
		this.errorKind = messageElement.getErrorKind();
	}

}
