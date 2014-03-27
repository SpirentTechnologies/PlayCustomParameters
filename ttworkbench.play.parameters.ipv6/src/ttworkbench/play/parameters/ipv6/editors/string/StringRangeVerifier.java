package ttworkbench.play.parameters.ipv6.editors.string;

import java.util.Arrays;
import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class StringRangeVerifier implements IVerifier<String> {

	final StringType stringType;


	public StringRangeVerifier( final StringType theStringType) {
		super();
		this.stringType = theStringType;
	}

	private static boolean isValueInRange( final String theValue, final StringType theStringType) {
		if ( theValue == null)
			return false;

		Long valueLength = new Long( theValue.length());
		Long minLength = theStringType.getMinChars();
		Long maxLength = theStringType.getMaxChars();
		
		if ( minLength == null && maxLength == null)
			return true;

		if ( minLength != null && maxLength != null)
			return ( minLength.compareTo( valueLength) <= 0) &&
	    			 ( maxLength.compareTo( valueLength) >= 0);

		if ( minLength == null && maxLength != null)
			return maxLength.compareTo( valueLength) >= 0;

  	if ( minLength != null && maxLength == null)
		  return minLength.compareTo( valueLength) <= 0;

		return false;
	}
	
	
	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
    boolean verified = isValueInRange( theInput, stringType);
		
		Long minLength = stringType.getMinChars() == null ? 0 : stringType.getMinChars();
		Long maxLength = stringType.getMaxChars() == null ? Long.MAX_VALUE : stringType.getMaxChars();
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input \"%s\" violates format.", theInput), ErrorKind.warning); 
		MessageRecord codomainInfo = new MessageRecord( "valid_chars_info", String.format( "Only strings with a length between %d and %d accepted.", minLength, maxLength), ErrorKind.info);
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, codomainInfo); 
		return new VerificationResult<String>( this, theInput, verified, messages);
	}
}

	