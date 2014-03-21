package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacPatternVerifier implements IVerificator<String>{

	private static final String MAC_PATTERN1 = "^([0-9a-fA-F]{2}[:]{5}[0-9a-fA-F]{2})$";
	private static final String MAC_PATTERN2 = "^([0-9a-fA-F]{2}[-]{5}[0-9a-fA-F]{2})$";
	private static final String MAC_PATTERN3 = "^[0-9a-fA-F]{12}$";
	
	private static final String  MAC_VALID_ENTRY_MESSAGE = "valid entries: Alphanumerical (a-f) seperated with \":\" or \"-\"";
	
	private boolean isMac( String theInput){
		if ( theInput.matches( MAC_PATTERN1) || theInput.matches( MAC_PATTERN2) || theInput.matches( MAC_PATTERN3)){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		// TODO Auto-generated method stub
		boolean isMac = isMac(theInput);
		
		MessageRecord invalidEntryMessage = new MessageRecord("invalid_input_warning", "The Entry is rejected", ErrorKind.warning);
		MessageRecord macValidEntryInfo = new MessageRecord("valid_entry_info", MAC_VALID_ENTRY_MESSAGE, ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( invalidEntryMessage, macValidEntryInfo);
		
		return new VerificationResult<String>(theInput, isMac, messages);
	}

}
