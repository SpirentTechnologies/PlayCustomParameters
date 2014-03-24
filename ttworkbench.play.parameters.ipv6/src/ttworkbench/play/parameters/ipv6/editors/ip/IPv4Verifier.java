package ttworkbench.play.parameters.ipv6.editors.ip;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class IPv4Verifier implements IVerifier<String> {

	/**
	 * TODO Nur vollständige IP-Adressen werden als korrekt angezeigt. Kann man
	 * dies noch verbessern?
	 */

	final String REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
	final Pattern pattern = Pattern.compile( REGEX);

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {

		List<MessageRecord> messages;
		boolean verified;

		Matcher matcher = pattern.matcher( theInput);
		verified = matcher.lookingAt();
		if (verified) {
			messages = Arrays.asList( new MessageRecord( "not_valid", "valid IPv4-Address.", ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( "not_valid", String.format( "\"%s\" is not a valid IPv4-Address.",
					theInput), ErrorKind.warning));
		}

		return new VerificationResult<String>( theInput, verified, messages);
	}

	public String toString() {
		return "IPv4-Address";
	}

}
