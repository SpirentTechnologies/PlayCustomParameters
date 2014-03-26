package ttworkbench.play.parameters.ipv6.editors.floatingpoint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class FloatTypeVerifier implements IVerifier<String> {
	
	private final Locale locale;
	
	public FloatTypeVerifier( final Locale theLocale) {
	  super();
		this.locale = theLocale;	
	}
	
	public FloatTypeVerifier() {
		this(  Locale.getDefault());
	}

  private boolean isValueAnFloat( String theValue) {
		try {
		  NumberFormat.getNumberInstance(locale).parse( theValue);
		  return true;
		} catch ( ParseException e) {
			return false;
		}
	}
	
	@Override
	public VerificationResult<String> verify( String theInput, Object... theParams) {
		boolean verified = isValueAnFloat( theInput);
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
		MessageRecord acceptedValuesInfo = new MessageRecord( "valid_chars_info", "Only floating point values accepted.", ErrorKind.info); 
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, acceptedValuesInfo); 
		return new VerificationResult<String>( theInput, verified, messages);
	}
	
}