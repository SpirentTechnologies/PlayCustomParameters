package ttworkbench.play.parameters.ipv6.components.messaging.components.registry;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageLabel;


public interface IMessageRegistry {

	void registerMessage( final MessageLabel theMessageLabel);

	@Deprecated
	void deregisterMessage( final MessageLabel theMessageLabel);

}
