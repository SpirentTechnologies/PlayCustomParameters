package ttworkbench.play.parameters.ipv6.editors.floatingpoint;

import java.util.LinkedList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class FloatingPointVerifier implements IVerifier<String> {

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		List<MessageRecord> messages = new LinkedList<MessageRecord>();
		boolean verified = true;
		if (theInput.contains( ",")) {
			theInput = theInput.replace( ',', '.');
			messages
					.add( new MessageRecord( ", not as floatingpoint seperator allowed. Automatical changed.", ErrorKind.info));
			verified = true;
		}
		try {
			Float.parseFloat( theInput);
			verified = true;
		} catch (NumberFormatException ex) {
			messages.add( ( new MessageRecord( "Invalid input, only numbers allowed. Input rejected", ErrorKind.error)));
			verified = false;
		}

		return new VerificationResult<String>( theInput, verified, messages);
	}

}