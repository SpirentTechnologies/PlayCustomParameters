package ttworkbench.play.parameters.ipv6.components.messaging.components.registry;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import ttworkbench.play.parameters.ipv6.components.messaging.components.MessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageChangeEvent;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageChangeListener;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageLabel;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/**
 * ErrorKindCounter enables counting of messages that occur. Thereby ErrorKindCounter distinguishes by the {@link ErrorKind}. 
 * Furthermore registered observers will be informed about counting events.  
 * 
 * @author Johannes Dahlke
 *
 */
public class MessageRegistry implements IMessageRegistry, IMessageInformation {

	
	private Map<ErrorKind, Set<IMessageHydra>> errorKindMap = new HashMap<ErrorKind, Set<IMessageHydra>>();
	private Map<Integer, IMessageHydra> messageMap = new HashMap<Integer, IMessageHydra>();
	
	private Set<IRegistryListener> listeners = new HashSet<IRegistryListener>();
	
	private MessageRegistry parentRegistry = null;
	
	public MessageRegistry() {
		for (ErrorKind errorKind : EnumSet.allOf( ErrorKind.class)) {
			errorKindMap.put( errorKind, new HashSet<IMessageHydra>());
		}
	}
	
	@Override
	public void setParent( MessageRegistry theParentRegistry) {
	  this.parentRegistry = theParentRegistry;
	  for ( Integer messageCode : messageMap.keySet()) {
			publishHydra( messageCode, (MessageHydra) messageMap.get( messageCode));
		}
	}
	
	
	@Override
	public void registerMessage( final MessageLabel theMessageLabel) {
		int messageId = theMessageLabel.getMessageCode();
		// prevent adding a message multiple times
		if ( messageMap.containsKey( messageId))
			return;
		
		createMessageFromLabel( theMessageLabel);
		
		// lastly we inform all listeners about this new registration 
		for ( IRegistryListener listener : listeners) {
			RegistryEvent registryElement = new RegistryEvent( this, theMessageLabel);
			listener.handleRegisterEvent( registryElement );
		}

	}
	
	@Override
	public void deregisterMessage( final MessageLabel theMessageLabel) {
		int messageCode = theMessageLabel.getMessageCode();
		// first test if this message is registered
		if ( !messageMap.containsKey( messageCode))
			return;
		
		// retrieve corresponding hydra object
		disposeMessageByCode( messageCode);
		
	  // lastly we inform all listeners about this deregistration 
		for ( IRegistryListener listener : listeners) {
			RegistryEvent registryElement = new RegistryEvent( this, theMessageLabel);
			listener.handleDeregisterEvent( registryElement );
		}
	}
	
	

	private void publishHydra( final int theMessageCode, final MessageHydra theMessageHydra) {
		// prevent adding a message multiple times
		if ( messageMap.containsKey( theMessageCode))
			return;
		
		errorKindMap.get( theMessageHydra.getErrorKind()).add( theMessageHydra);
		messageMap.put( theMessageCode, theMessageHydra);
		if ( parentRegistry != null) {
			parentRegistry.publishHydra( theMessageCode, theMessageHydra);
			
		  // we inform all listeners in parent about this new registration 
			for ( IRegistryListener listener : parentRegistry.listeners) {
				listener.handleHydraPublishedEvent( theMessageHydra);
			}
		}
	}
	
	private void createMessageFromLabel( final MessageLabel theMessageLabel) {
		// create a new hydra object for the given message.
		final MessageHydra newMessageHydra = new MessageHydra( theMessageLabel.getMessageRecord());
		
		// handle the given message object as master label
		// therefore we are interested in message changes
		theMessageLabel.addChangeListener( new MessageChangeListener() {

			@Override
			public void messageChange( MessageChangeEvent theEvent) {
				newMessageHydra.messageChanged( theEvent.sender);
			}
		});

		// and we also interested in disposal of this message
		theMessageLabel.addDisposeListener( new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent theDisposeEvent) {
				deregisterMessage( theMessageLabel);
			}
		});

    publishHydra( theMessageLabel.getMessageCode(), newMessageHydra);
	}

	
	private void retrieveHydra(IMessageHydra theMessageHydra) {
	  if ( parentRegistry != null)
	  	parentRegistry.retrieveHydra( theMessageHydra);
		errorKindMap.get( theMessageHydra.getErrorKind()).remove( theMessageHydra);
		messageMap.values().removeAll( Collections.singleton( theMessageHydra));
	}
	

	private void disposeMessageByCode( Integer theMessageCode) {
		IMessageHydra messageHydra = messageMap.get( theMessageCode);
		// retrieves all references from parents
		retrieveHydra( messageHydra);
		// and disposes all other related labels 
		messageHydra.disposeLabels();
	}

	@Override
	public int getTotalCount() {
		int total = 0;
		for ( Set<?> messages : errorKindMap.values()) {
			total += messages.size();
		}
		return total;
	}
	
	@Override
	public Integer getCountOfMessagesWithErrorKind( EnumSet<ErrorKind> theErrorKindSet) {
		int count = 0;
		for (ErrorKind errorKind : theErrorKindSet) {
			count += errorKindMap.get( errorKind).size();
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
	public void addListener( IRegistryListener theListener) {
		listeners.add( theListener);
	}
	
	@Override
	public Map<ErrorKind, Set<String>> compileMessagesReport() {
		Map<ErrorKind, Set<String>> report = new HashMap<ErrorKind, Set<String>>();
		Set<IMessageHydra> messageHydras;
		Set<String> messages;
		for (ErrorKind errorKind : errorKindMap.keySet()) {
			messages = new HashSet<String>();
			messageHydras = errorKindMap.get( errorKind);
			for (IMessageHydra messageHydra : messageHydras) {
				messages.add( messageHydra.getMessage());
			}
			report.put( errorKind, messages);
		} 
		return report;
	}


	
}