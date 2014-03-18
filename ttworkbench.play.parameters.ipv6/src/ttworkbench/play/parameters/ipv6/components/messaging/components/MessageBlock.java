package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageElement;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.components.messaging.views.MessageDisplay;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/**
 * Groups several {@link MessageElement} objects. Discriminates between tagged messages and not tagged messages.
 * If a message has a tag, a prior message with the same tag will be replaced while adding this new message. 
 * Otherwise messages simple be added to the block.    
 * 
 * @author Johannes Dahlke
 *
 */
public class MessageBlock {

	private Map<String, MessageElement> taggedMessageElements = new HashMap<String,MessageElement>();
	private Set<String> agedMessageTags = new HashSet<String>();
	private List<MessageElement> untaggedMessageElements = new ArrayList<MessageElement>();
	
	private IMessageContainer messageContainer;
	private IMessageRegistry messageRegistry;
	
	public MessageBlock( final IMessageContainer theMessageContainer, final IMessageRegistry theMessageRegistry) {
		this.messageContainer = theMessageContainer;
		this.messageRegistry = theMessageRegistry;
	}
	
	public synchronized void putTaggedMessage( final MessageRecord theMessageRecord, final boolean doRegister) {
		MessageRecord msg = theMessageRecord;
		MessageElement oldMessageLine = taggedMessageElements.remove( msg.tag);
		
		// release old message control first
		if ( oldMessageLine != null)
			oldMessageLine.dispose();
		
		// add a success message only as an answer to a message of a prior cycle. 
		if ( !agedMessageTags.contains( msg.tag) &&
				msg.errorKind.equals( ErrorKind.success))
			return;
		
		MessageElement newMessageElement = new MessageElement( messageContainer, msg.message, msg.errorKind);
		newMessageElement.addDisposeListener( getMessageDisposeListener());
		taggedMessageElements.put( theMessageRecord.tag, newMessageElement);
		
		if ( doRegister)
			messageRegistry.registerMessage( newMessageElement);
	}

	public synchronized void clearTaggedSuccessMessage( final String theTag) {
		MessageElement messageElement = taggedMessageElements.get( theTag);
		if ( messageElement != null &&
				messageElement.getErrorKind().equals( ErrorKind.success)) {
			taggedMessageElements.remove( theTag);
			messageElement.dispose();
		}
	}
	
	public synchronized void clearTaggedMessage( final String theTag) {
		MessageElement messageElement = taggedMessageElements.remove( theTag);
		if ( messageElement != null) {
			messageElement.dispose();
		}
	}
	
	public void addUntaggedMessage( final MessageRecord theMessageRecord, final boolean doRegister) {
		MessageRecord msg = theMessageRecord;
		if ( msg.errorKind.equals( ErrorKind.success))
			return;
		
		MessageElement newMessageElement = new MessageElement( messageContainer, msg.message, msg.errorKind);
		newMessageElement.addDisposeListener( getMessageDisposeListener());
		untaggedMessageElements.add( newMessageElement);
		
		if ( doRegister)
			messageRegistry.registerMessage( newMessageElement);
	}

	
	private DisposeListener getMessageDisposeListener() {
		return new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent theEvent) {
			  messageRegistry.deregisterMessage( (MessageElement) theEvent.widget);	
			}
		};
	}

	private void clearAllTaggedMessages() {
		for (MessageElement messageElement : taggedMessageElements.values()) {
			messageElement.dispose();
		}
		taggedMessageElements.clear();
	}
	
	private void clearAllUntaggedMessages() {
		for (MessageElement messageElement : untaggedMessageElements) {
			messageElement.dispose();
		}
		untaggedMessageElements.clear();
	}
	
	private void retainTags() {
		agedMessageTags.addAll( taggedMessageElements.keySet());
	}
	
	private void clearRetainedTags() {
		agedMessageTags.clear();
	}
		
	
	public synchronized void beginUpdateCycle() {
		retainTags();
		clearAllTaggedMessages();
		clearAllUntaggedMessages();
	}
		
	public void endUpdateCycle() {
		clearRetainedTags();
	}

	public List<String> getMessages( EnumSet<ErrorKind> theMessageKinds) {
		List<String> result = new ArrayList<String>();
		Collection<MessageElement> messageElements = taggedMessageElements.values();
		for (MessageElement messageElement : messageElements) {
			if ( theMessageKinds.contains( messageElement.getErrorKind()))
			  result.add( messageElement.getMessage());
		}
		return result;
	}		
	
}