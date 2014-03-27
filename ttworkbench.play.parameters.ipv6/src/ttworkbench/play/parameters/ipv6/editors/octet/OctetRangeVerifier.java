package ttworkbench.play.parameters.ipv6.editors.octet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class OctetRangeVerifier implements IVerifier<String> {

	final OctetType octetType;
	
	public OctetRangeVerifier( final OctetType theOctetType) {
		super();
		this.octetType = theOctetType;
	}
	
	private boolean isValueInRange( final String theValue, final OctetType theOctetType) {
		if ( theValue == null)
			return false;

		BigInteger value; 
		try {
			value = new BigInteger( theValue);
		} catch ( NumberFormatException e) {
			return false;
		}
		
//		
//		BigInteger minValue = theOctetType.getMinOctets();
//		BigInteger maxValue = theOctetType.getMaxOctets();
//		
//		if ( minValue == null && maxValue == null)
//			return true;
//
//		if ( minValue != null && maxValue != null)
//			return ( minValue.compareTo( value) <= 0) &&
//	    			 ( maxValue.compareTo( value) >= 0);
//
//		if ( minValue == null && maxValue != null)
//			return maxValue.compareTo( value) >= 0;
//
//  	if ( minValue != null && maxValue == null)
//		  return minValue.compareTo( value) >= 0;

		return false;
	}
	
	
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		boolean verified = isValueInRange( theInput, octetType);
//		
//		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
//		MessageRecord codomainInfo = new MessageRecord( "valid_chars_info", String.format( "Only integers in range [%s,%s] accepted.", integerType.getMinValue(), integerType.getMaxValue()), ErrorKind.info);
		List<MessageRecord> messages = null;// Arrays.asList( inputRejectedWarning, codomainInfo); 
		return new VerificationResult<String>( this, theInput, verified, messages);
	}

	

}