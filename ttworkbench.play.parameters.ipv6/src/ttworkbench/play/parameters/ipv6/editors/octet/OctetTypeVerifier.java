package ttworkbench.play.parameters.ipv6.editors.octet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class OctetTypeVerifier implements IVerifier<String> {

	
	private boolean isValueAnOctalOctet( String theValue) {
		// 0....
		return false;
	}
	
  private boolean isValueAnDecimalOctet( String theValue) {
		// ...
  	return false;
	}
  
  private boolean isValueAnHexadecimalOctet( String theValue) {
		// 0x...,  ....h, oder A-F in, ''O 
  	return false;
  }
		
  private boolean isValueAnOctet( String theValue) {
		return isValueAnOctalOctet( theValue) ||
			     isValueAnDecimalOctet( theValue) || 
			     isValueAnHexadecimalOctet( theValue);
	}
	
	@Override
	public VerificationResult<String> verify( String theInput, Object... theParams) {
		boolean verified = isValueAnOctet( theInput);
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
		MessageRecord acceptedValuesInfo = new MessageRecord( "valid_chars_info", "Only values with format 0x[0-7]+ for octal, [1-9]*[0-9] for decimal and [0-9A-F]+h or '[0-9A-F]+'O for hexadecimal representations accepted.", ErrorKind.info); 
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, acceptedValuesInfo); 
		return new VerificationResult<String>( this, theInput, verified, messages);
	}
	
}