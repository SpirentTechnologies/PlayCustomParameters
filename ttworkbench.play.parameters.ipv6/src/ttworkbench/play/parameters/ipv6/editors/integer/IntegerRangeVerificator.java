package ttworkbench.play.parameters.ipv6.editors.integer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class IntegerRangeVerificator implements IVerificator<String> {

	private boolean isValueInRange( final String theValue, final IntegerType theIntegerType) {
		if ( theValue == null)
			return false;

		BigInteger value; 
		try {
			value = new BigInteger( theValue);
		} catch ( NumberFormatException e) {
			return false;
		}
		
		
		BigInteger minValue = theIntegerType.getMinValue();
		BigInteger maxValue = theIntegerType.getMaxValue();
		
		if ( minValue == null && maxValue == null)
			return true;

		if ( minValue != null && maxValue != null)
			return ( minValue.compareTo( value) <= 0) &&
	    			 ( maxValue.compareTo( value) >= 0);

		if ( minValue == null && maxValue != null)
			return maxValue.compareTo( value) >= 0;

  	if ( minValue != null && maxValue == null)
		  return minValue.compareTo( value) >= 0;

		return false;
	}
	
	public VerifyResult<String> verify(String theInput, IntegerType theIntegerType) {
		return verify( theInput, (Object) theIntegerType); 
	}
	
	@Override
	public VerifyResult<String> verify(String theInput, Object... theParams) {
		final IntegerType integerType = (IntegerType) theParams[0];
    boolean verified = isValueInRange( theInput, integerType);
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
		MessageRecord codomainInfo = new MessageRecord( "valid_chars_info", String.format( "Only integers in range [%s,%s] accepted.", integerType.getMinValue(), integerType.getMaxValue()), ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, codomainInfo); 
		return new VerifyResult<String>( theInput, verified, messages);
	}
	

}