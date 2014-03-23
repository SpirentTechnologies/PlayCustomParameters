package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageLabel;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageLabel;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageHydra implements IMessageHydra {

	private MessageRecord messageRecord;
	
	private final Set<MessageLabel> messageLabels = new HashSet<MessageLabel>();
	
	public MessageHydra( final MessageRecord theMessageRecord) {
		super();
		this.messageRecord = theMessageRecord;
	}
	
	
	@Override
	public IMessageLabel newLabel( IMessageContainer theMessageContainer) {
		MessageLabel newMessageLabel = new MessageLabel( theMessageContainer, messageRecord);
		messageLabels.add( newMessageLabel);
		newMessageLabel.addDisposeListener( new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent theDisposeEvent) {
				messageLabels.remove( theDisposeEvent.widget);
				disposeLabels();
			}
		});
		return newMessageLabel;
	}
	
	@Override
	public void disposeLabels() {
		for ( Widget widget : messageLabels) {
			if ( !widget.isDisposed())
		  	widget.dispose();
		}
	}
	
	@Override
	public int getCountOfLabels() {
		return messageLabels.size();
	}

	@Override
	public ErrorKind getErrorKind() {
		return messageRecord.errorKind;
	}
	
	@Override
	public String getMessage() {
		return messageRecord.message;
	}
	
	@Override
	public boolean hasTag() {
		return messageRecord.hasTag();
	}
	
	public MessageRecord getMessageRecord() {
		return messageRecord;
	}

  @Override
	public void messageChanged( final MessageLabel theSender) {
		messageRecord = theSender.getMessageRecord();
		for ( MessageLabel label : messageLabels) {
			if ( label != theSender)
		  	label.setMessage( getMessage(), getErrorKind());
		}
	}
	
	
	
	

}
