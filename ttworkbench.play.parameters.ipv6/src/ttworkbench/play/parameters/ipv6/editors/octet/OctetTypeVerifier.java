package ttworkbench.play.parameters.ipv6.editors.octet;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.UNKNOWN;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import de.tu_berlin.cs.uebb.muttcn.runtime.Default;

public class OctetTypeVerifier implements IVerifier<String> {

//	// allow negative values 
//	private static final String REGEX_OCTAL = "^(-|\\+)?0([0-7]+)$";
//	private static final String REGEX_DECIMAL = "^(-|\\+)?([1-9][0-9]*)$";
//	private static final String REGEX_HEXADECIMAL = "^(-|\\+)?([0-9A-Fa-f]+)$";
//	private static final String REGEX_HEX_0x = "(-|\\+)?0[x|X]([0-9A-Fa-f]+)$";
//	private static final String REGEX_HEX_h = "^(-|\\+)?([0-9A-Fa-f])+h$";
//	private static final String REGEX_HEX_SHARP = "^(-|\\+)?#([0-9A-Fa-f])+$";
//	private static final String REGEX_HEX_STRING = "^'(-|\\+)?([0-9A-Fa-f]+)'O$";
//
	private static final String REGEX_OCTAL = "^0([0-7]+)$";
	private static final String REGEX_DECIMAL = "^([1-9][0-9]*)$";
	private static final String REGEX_HEXADECIMAL = "^([0-9A-Fa-f]+)$";
	private static final String REGEX_HEX_0x = "0[x|X]([0-9A-Fa-f]+)$";
	private static final String REGEX_HEX_h = "^([0-9A-Fa-f])+h$";
	private static final String REGEX_HEX_SHARP = "^#([0-9A-Fa-f])+$";
	private static final String REGEX_HEX_STRING = "^'([0-9A-Fa-f]+)'O$";
	
	private static String extract( final String theValue, final String thePattern) {
		Pattern pattern = Pattern.compile( thePattern);
    Matcher matcher = pattern.matcher( theValue);
    if (matcher.find() &&
      matcher.groupCount() > 0) {
    	return matcher.group( 1);
    }
    return null;
	}
	
	private static BigInteger extractOctalOctet( String theValue) {
		String valueString = extract( theValue, REGEX_OCTAL);
		if ( valueString == null)
			return null;
		return new BigInteger( valueString, 8);
	}

	private static BigInteger extractDecimalOctet( String theValue) {
		String valueString = extract( theValue, REGEX_DECIMAL);
		if ( valueString == null)
			return null;
		return new BigInteger( valueString);
	}
	
	private static BigInteger extractHexOctet( String theValue) {
		String valueString = extract( theValue, REGEX_HEXADECIMAL);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_0x);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_h);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_SHARP);
		if ( valueString == null)
			valueString = extract( theValue, REGEX_HEX_STRING);		
		if ( valueString == null)
			return null;
		return new BigInteger( valueString, 16);
	}
	
	private static BigInteger extractOctet( String theValue) {
		BigInteger bigInteger = extractOctalOctet( theValue);
		if ( bigInteger == null)
			bigInteger = extractDecimalOctet( theValue);
		if ( bigInteger == null)
			bigInteger = extractHexOctet( theValue);
		if ( bigInteger == null)
			return null;
		return bigInteger;
	}


	
	
	
	@Override
	public VerificationResult<String> verify( String theInput, Object... theParams) {
		BigInteger integerRepresentation = extractOctet( theInput);
	 
		boolean typeVerified = integerRepresentation != null;
	
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" rejected.", theInput), ErrorKind.warning); 
		MessageRecord acceptedValuesInfo = new MessageRecord( "valid_chars_info", "Accept 0[0-7]+ as octal, [1-9][0-9]* as decimal and [0-9A-F]+h, #[0-9A-F], 0x[0-9A-F], '[0-9A-F]+'O as hexadecimal representations.", ErrorKind.info); 
		List<MessageRecord> messages = Arrays.asList( inputRejectedWarning, acceptedValuesInfo); 
		return new VerificationResult<String>( this, theInput, integerRepresentation, typeVerified, messages);

	}

	
}