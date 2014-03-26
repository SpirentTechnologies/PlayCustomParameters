package ttworkbench.play.parameters.ipv6.components.messaging.components.registry;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageInformation {

	ErrorKind getHighestErrorKind();

	Integer getCountOfMessagesWithErrorKind(EnumSet<ErrorKind> theErrorKindSet);

	int getTotalCount();

	void addListener( IRegistryListener theListener);

	Map<ErrorKind, Set<String>> compileMessagesReport();

}
