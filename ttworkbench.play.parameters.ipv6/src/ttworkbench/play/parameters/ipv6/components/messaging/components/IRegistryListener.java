package ttworkbench.play.parameters.ipv6.components.messaging.components;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IRegistryListener {

	void handleRegisterEvent( final RegistryEvent theEvent);

	void handleDeregisterEvent( final RegistryEvent theEvent);	
}