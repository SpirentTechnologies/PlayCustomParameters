package ttworkbench.play.parameters.ipv6.editors.ip;

import ttworkbench.play.parameters.ipv6.editors.verification.RegexVerifier;

public class HostnameVerifier extends RegexVerifier {

	final String REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

	@Override
	protected String regex() {
		return REGEX;
	}

	@Override
	protected String validMessageText(final String theInput) {
		return "valid hostname.";
	}

	@Override
	protected String notValidMessageText(final String theInput) {
		return String.format( "\"%s\" is not a valid hostname.", theInput);
	}

	@Override
	protected String helpValue() {
		return "hostname";
	}
}
