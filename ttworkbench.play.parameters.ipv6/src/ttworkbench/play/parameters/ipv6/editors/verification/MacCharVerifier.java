package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

public class MacCharVerifier implements IVerificator<String> {

	private static final String VALID_CHAR_PATTERN = "^[0-9a-fA-F:-]$";
	
	private boolean isValidChar(String theInput){
		if(theInput.matches( VALID_CHAR_PATTERN)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		// TODO Auto-generated method stub
		boolean isValidChar = isValidChar(theInput);
		
		MessageRecord invalidCharWarning = new MessageRecord("invalid_char_warning", "The character is invalid", ErrorKind.warning);
		MessageRecord validCharInfo = new MessageRecord("valid_char_info", "the characters allowed are Alphanumerical (0-9, a-f), \":\" and \"-\"", ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( invalidCharWarning, validCharInfo);
		return new VerificationResult<String>(theInput, isValidChar, messages);
	}

}