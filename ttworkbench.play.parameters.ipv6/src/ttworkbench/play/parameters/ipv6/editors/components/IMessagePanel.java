package ttworkbench.play.parameters.ipv6.editors.components;

import java.util.EnumSet;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessagePanel {

	void endUpdate();

	void beginUpdateForSender(Object theSenderId);

	void flashMessage(String theTag, String theWarning, ErrorKind theErrorKind);

	void addUntaggedMessage(String theMessage, ErrorKind theErrorKind);

	void putTaggedMessage(String theTag, String theMessage, ErrorKind theErrorKind);

	List<String> getMessages(EnumSet<ErrorKind> theMessageKinds);

}
