package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class OrVerifier implements IVerifier<String> {

	Set<IVerifier<String>> verifier;

	public OrVerifier( final IVerifier<String>... verifier) {
		this.verifier = new HashSet<IVerifier<String>>( Arrays.asList( verifier));
	}

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		List<MessageRecord> messages = new LinkedList<MessageRecord>();
		boolean verified = false;
		IVerifier<String> validVerifier = null;

		for (IVerifier<String> v : verifier) {
			VerificationResult<String> result = v.verify( theInput, theParams);
			verified |= result.verified;
			if (verified) {
				validVerifier = v;
				break;
			}
		}

		if (verified) {
			messages = Arrays
					.asList( new MessageRecord( "validInput", "valid " + validVerifier.toString(), ErrorKind.success));
		} else {
			messages = Arrays
					.asList( new MessageRecord( "validInput", "is not a valid " + this.toString(), ErrorKind.warning));
		}
		return new VerificationResult<String>( theInput, verified, messages);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (IVerifier<String> v : verifier) {
			if (!first) {
				buffer.append( " or ");
			}
			buffer.append( v.toString());
			first = false;
		}
		return buffer.toString();

	}
}
