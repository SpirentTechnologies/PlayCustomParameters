package ttworkbench.play.parameters.ipv6.components.messaging.views;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.IMessageViewLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageView {

	/**
	 * Finishes an update cycle. 
	 */
	void endUpdate();

	/**
	 * Starts an update cycle for a specific sender.
	 *  
	 * @param theSenderId
	 */
	void beginUpdateForSender(Object theSenderId);

	/**
	 * Flashes a message for a short term. Methods {@link #beginUpdateForSender()} and {@link #endUpdate()} didn't affect {@link #flashMessage()}.
	 */
	void flashMessage( MessageRecord theMessageRecord);
	void flashMessages( Collection<MessageRecord> theMessageRecords);
	
	void showMessage( MessageRecord theMessageRecord);
	void showMessages( Collection<MessageRecord> theMessageRecords);

		/**
	 * Fetches all messages on this panel of specified error kind.
	 * 
	 * @param theMessageKinds Set of error kinds considered in the result.
	 * @return a list of messages compiled out of messages matches the specified error kinds.  
	 */
	List<String> getMessages(EnumSet<ErrorKind> theMessageKinds);
	
	IMessageViewLookAndBehaviour getLookAndBehaviour();
	
	void setLookAndBehaviour( IMessageViewLookAndBehaviour theLookAndBehaviour);
	
	void wrapControl( Composite theWrappedComposite);

}
