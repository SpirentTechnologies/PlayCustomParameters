package ttworkbench.play.parameters.ipv6.components.messaging.components.registry;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageHydra;


public interface IRegistryListener {

	void handleRegisterEvent( final RegistryEvent theEvent);

	void handleDeregisterEvent( final RegistryEvent theEvent);

	void handleHydraPublishedEvent(IMessageHydra theMessageHydra);	
	
}