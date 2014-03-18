package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.EnumSet;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageInformation {

	ErrorKind getHighestErrorKind();

	Integer getCountOfMessagesWithErrorKind(EnumSet<ErrorKind> theErrorKindSet);

	int getTotalCount();

	void addListener( IRegistryListener theListener);

}
