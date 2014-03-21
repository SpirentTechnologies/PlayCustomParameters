package ttworkbench.play.parameters.ipv6.editors.ip;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class HostnameVerificator implements IVerificator<String> {

	final String REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
	final Pattern pattern = Pattern.compile( REGEX);

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {

		List<MessageRecord> messages;
		boolean verified;

		Matcher matcher = pattern.matcher( theInput);
		verified = matcher.lookingAt();

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
