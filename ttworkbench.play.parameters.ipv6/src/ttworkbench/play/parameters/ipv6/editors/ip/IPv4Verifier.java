package ttworkbench.play.parameters.ipv6.editors.ip;

import ttworkbench.play.parameters.ipv6.editors.verification.RegexVerifier;

public class IPv4Verifier extends RegexVerifier {

	/*
	 * TODO Nur vollständige IP-Adressen werden als korrekt angezeigt. Kann man
	 * dies noch verbessern?
	 */

	final String REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

	@Override
	protected String regex() {
		return REGEX;
	}

	@Override
	protected String validMessageText(String theInput) {
		return "valid IPv4-Address.";
	}

	@Override
	protected String notValidMessageText(String theInput) {
		return String.format( "\"%s\" is not a valid IPv4-Address.", theInput);
	}

	@Override
	protected String helpValue() {
		return "IPv4-Address";
	}

}
