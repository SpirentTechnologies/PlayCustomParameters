package ttworkbench.play.parameters.ipv6.editors.ip;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class IPv6Verifier implements IVerifier<String> {

	/* alternativer Ansatz */
	/* source: http://stackoverflow.com/questions/46146 */
	public static final String IPV6_HEX4DECCOMPRESSED_REGEX = "\\A((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?) ::((?:[0-9A-Fa-f]{1,4}:)*)(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
	public static final String IPV6_6HEX4DEC_REGEX = "\\A((?:[0-9A-Fa-f]{1,4}:){6,6})(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
	public static final String IPV6_HEXCOMPRESSED_REGEX = "\\A((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)\\z";
	public static final String IPV6_REGEX = "\\A(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\\z";

	/* source: http://stackoverflow.com/questions/53497 */
	final String REGEX = "("
			+
			// 1:2:3:4:5:6:7:8
			"([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|"
			+
			// 1:: 1:2:3:4:5:6:7::
			"([0-9a-fA-F]{1,4}:){1,7}:|"
			+
			// 1::8 1:2:3:4:5:6::8 1:2:3:4:5:6::8
			"([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|"
			+
			// 1::7:8 1:2:3:4:5::7:8 1:2:3:4:5::8
			"([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|"
			+
			// 1::6:7:8 1:2:3:4::6:7:8 1:2:3:4::8
			"([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|"
			+
			// 1::5:6:7:8 1:2:3::5:6:7:8 1:2:3::8
			"([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|"
			+
			// 1::4:5:6:7:8 1:2::4:5:6:7:8 1:2::8
			"([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|"
			+
			// 1::3:4:5:6:7:8 1::3:4:5:6:7:8 1::8
			"[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|"
			+
			// ::2:3:4:5:6:7:8 ::2:3:4:5:6:7:8 ::8 ::
			":((:[0-9a-fA-F]{1,4}){1,7}|:)|"
			+
			// fe80::7:8%eth0 fe80::7:8%1 (link-local IPv6 addresses with zone index)
			"e80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|"
			+ "::(ffff(:0{1,4}){0,1}:){0,1} "
			+ "((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3} "
			+
			// ::255.255.255.255 ::ffff:255.255.255.255 ::ffff:0:255.255.255.255
			// (IPv4-mapped IPv6 addresses and IPv4-translated addresses)
			"(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|" + "([0-9a-fA-F]{1,4}:){1,4}:"
			+ "((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}" +
			// 2001:db8:3:4::192.0.2.33 64:ff9b::192.0.2.33 (IPv4-Embedded IPv6
			// Address)
			"(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])" + ")";

	final Pattern pattern = Pattern.compile( REGEX);

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {

		List<MessageRecord> messages;
		boolean verified;

		verified = theInput.matches( REGEX);
		if (verified) {
			messages = Arrays.asList( new MessageRecord( "invalid_input_warning", "valid IPv6-Address.", ErrorKind.success));
		} else {
			messages = Arrays.asList( new MessageRecord( "invalid_input_warning", String.format(
					"\"%s\" is not a valid IPv6-Address.", theInput), ErrorKind.warning));
		}

		return new VerificationResult<String>( theInput, verified, messages);
	}

	public String toString() {

		return "IPv6-Address";
	}

}
