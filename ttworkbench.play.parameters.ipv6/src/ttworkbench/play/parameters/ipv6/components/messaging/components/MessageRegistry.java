package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageElement;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/**
 * ErrorKindCounter enables counting of messages that occur. Thereby ErrorKindCounter distinguishes by the {@link ErrorKind}. 
 * Furthermore registered observers will be informed about counting events.  
 * 
 * @author Johannes Dahlke
 *
 */
public class MessageRegistry implements IMessageRegistry, IMessageInformation, IRegistryListener {

	
	private Map<ErrorKind, Set<MessageElement>> messageMap = new HashMap<ErrorKind, Set<MessageElement>>();
	private Set<IRegistryListener> listeners = new HashSet<IRegistryListener>();
	
	public MessageRegistry() {
		for (ErrorKind errorKind : EnumSet.allOf( ErrorKind.class)) {
			messageMap.put( errorKind, new HashSet<MessageElement>());
		}
	}
	
	
	@Override
	public void registerMessage( final MessageElement theMessageElement) {
		Set<MessageElement> messages = messageMap.get( theMessageElement.getErrorKind());
		messages.add( theMessageElement);
		for ( IRegistryListener listener : listeners) {
			RegistryEvent registryElement = new RegistryEvent( this, theMessageElement);
			listener.handleRegisterEvent( registryElement );
		}
	}
	
	@Override
	public void deregisterMessage( final MessageElement theMessageElement) {
		Set<MessageElement> messages = messageMap.get( theMessageElement.getErrorKind());
		if ( theMessageElement != null && messages.remove( theMessageElement)) {
			for ( IRegistryListener listener : listeners) {
				RegistryEvent registryElement = new RegistryEvent( this, theMessageElement);
				listener.handleDeregisterEvent( registryElement );
			}
		}
	}
	
	
	@Override
	public int getTotalCount() {
		int total = 0;
		for ( Set<?> messages : messageMap.values()) {
			total += messages.size();
		}
		return total;
	}
	
	@Override
	public Integer getCountOfMessagesWithErrorKind( EnumSet<ErrorKind> theErrorKindSet) {
		int count = 0;
		for (ErrorKind errorKind : theErrorKindSet) {
			count += messageMap.get( errorKind).size();
		}
		return count;
	}
	
	@Override
	public ErrorKind getHighestErrorKind() {
		if ( 0 < getCountOfMessagesWithErrorKind( EnumSet.of( ErrorKind.error)))
		  return ErrorKind.error;
		if ( 0 < getCountOfMessagesWithErrorKind( EnumSet.of( ErrorKind.warning)))
		  return ErrorKind.warning;
		if ( 0 < getCountOfMessagesWithErrorKind( EnumSet.of( ErrorKind.info)))
		  return ErrorKind.info;
		return ErrorKind.success;
	}

	@Override
	public void handleRegisterEvent(RegistryEvent theEvent) {
		registerMessage( theEvent.messageElement);
	}

	@Override
	public void handleDeregisterEvent(RegistryEvent theEvent) {
		deregisterMessage( theEvent.messageElement);	
	}


	public void addChildRegistry( IMessageInformation theMessageRegistry) {
		theMessageRegistry.addListener( this);
	}
	
	@Override
	public void addListener( IRegistryListener theListener) {
		listeners.add( theListener);
	}


	
}