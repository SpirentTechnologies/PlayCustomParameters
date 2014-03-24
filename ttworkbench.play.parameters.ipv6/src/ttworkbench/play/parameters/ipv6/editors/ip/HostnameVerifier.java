package ttworkbench.play.parameters.ipv6.editors.ip;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class HostnameVerifier implements IVerifier<String> {

	final String REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {

		List<MessageRecord> messages;
		boolean verified;

		verified = theInput.matches( REGEX);

		if (verified) {
			messages = Arrays.asList( new MessageRecord( "invalid_input_warning",
					String.format( "valid hostname.", theInput), ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( "invalid_input_warning", String.format(
					"\"%s\" is not a valid hostname.", theInput), ErrorKind.warning));
		}

		return new VerificationResult<String>( theInput, verified, messages);
	}

	public String toString() {
		return "hostname";
	}
}
