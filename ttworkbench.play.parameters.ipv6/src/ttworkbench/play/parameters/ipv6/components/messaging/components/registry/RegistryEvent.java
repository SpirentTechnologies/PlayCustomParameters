package ttworkbench.play.parameters.ipv6.components.messaging.components.registry;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageLabel;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class RegistryEvent {
	
	public final IMessageInformation registry;
	public final IMessageLabel messageLabel;
	public final ErrorKind errorKind;
	
	public RegistryEvent( final IMessageInformation theRegistry, final IMessageLabel theMessageLabel) {
		super();
		this.registry = theRegistry;
		this.messageLabel = theMessageLabel;
		this.errorKind = messageLabel.getErrorKind();
	}

}
