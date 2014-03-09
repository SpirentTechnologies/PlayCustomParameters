package ttworkbench.play.parameters.ipv6.components;

import java.util.EnumSet;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessagePanel {

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

	void flashMessage(String theTag, String theWarning, ErrorKind theErrorKind);

	void addUntaggedMessage(String theMessage, ErrorKind theErrorKind);

	void putTaggedMessage(String theTag, String theMessage, ErrorKind theErrorKind);

	/**
	 * Fetches all messages on this panel of specified error kind.
	 * 
	 * @param theMessageKinds Set of error kinds considered in the result.
	 * @return a list of messages compiled out of messages matches the specified error kinds.  
	 */
	List<String> getMessages(EnumSet<ErrorKind> theMessageKinds);

}
