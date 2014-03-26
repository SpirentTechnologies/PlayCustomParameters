package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public abstract class RegexVerifier implements IVerifier<String> {

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		List<MessageRecord> messages;
		boolean verified;
		String tag = "validity";

		verified = theInput.matches( regex());
		if (theInput.matches( regex())) {
			messages = Arrays.asList( new MessageRecord( tag, validMessageText( theInput), ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( tag, notValidMessageText( theInput), ErrorKind.warning));
		}
		return new VerificationResult<String>( theInput, verified, messages);
	}

	public String toString() {
		return helpValue();
	}

	protected abstract String regex();

	protected abstract String validMessageText(final String input);

	protected abstract String notValidMessageText(final String input);

	protected abstract String helpValue();

}
