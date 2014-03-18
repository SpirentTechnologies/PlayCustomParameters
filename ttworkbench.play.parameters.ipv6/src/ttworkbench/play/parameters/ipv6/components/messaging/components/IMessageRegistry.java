package ttworkbench.play.parameters.ipv6.components.messaging.components;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageElement;


public interface IMessageRegistry {

	void registerMessage( final MessageElement theMessageElement);

	void deregisterMessage( final MessageElement theMessageElement);

}
