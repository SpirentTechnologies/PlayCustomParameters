package ttworkbench.play.parameters.ipv6.editors.macaddr;

import java.util.List;
import java.util.Arrays;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MacRangeVerifier implements IVerifier<String> {

	private static final int MAC_LENGTH = 17;
	
	private boolean isInRange(String theInput){
		if(theInput.length() <= MAC_LENGTH){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		// TODO Auto-generated method stub
		boolean isInRange = isInRange(theInput);
		
		MessageRecord invalidRangeWarning = new MessageRecord("invalid_range_warning", "the maximum range of the MAC is reached", ErrorKind.warning);
		MessageRecord validRangeInfo = new MessageRecord("valid_range_info", "MAC range is 17 characters at most", ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( invalidRangeWarning, validRangeInfo);
		
		return new VerificationResult<String>( this, theInput, isInRange, messages);
	}

	
}