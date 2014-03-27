package ttworkbench.play.parameters.ipv6.editors.macaddr;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.RegexVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacPatternVerifier extends RegexVerifier{

	private static final String MAC_PATTERN1 = "^(?:[a-fA-F0-9]{2}([-:]))(?:[a-fA-F0-9]{2}\\1){4}[a-fA-F0-9]{2}$";
	
	private static final String  MAC_VALID_ENTRY_MESSAGE = "valid entries: Alphanumerical (a-f) seperated with \":\" or \"-\"";

	@Override
	protected String regex() {
		// TODO Auto-generated method stub
		return MAC_PATTERN1;
	}
	@Override
	protected String validMessageText(String theInput) {
		// TODO Auto-generated method stub
		String validMessage = "This entry is valid MAC-Address";
		return validMessage;
	}
	@Override
	protected String notValidMessageText(String theInput) {
		// TODO Auto-generated method stub
		System.out.println("result of my regular expression: " + MAC_PATTERN1.matches( theInput.trim()));
		System.out.println("my input: "+ theInput);
		return String.format( "\"%s\" is not a valid MAC-Address.", theInput);
	}
	@Override
	protected String helpValue() {
		// TODO Auto-generated method stub
		return "MAC-Address";
	}

}
