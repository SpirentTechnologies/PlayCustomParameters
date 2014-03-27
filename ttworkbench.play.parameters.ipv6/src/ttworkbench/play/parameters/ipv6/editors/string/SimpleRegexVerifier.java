package ttworkbench.play.parameters.ipv6.editors.string;

import java.util.Arrays;
import java.util.Locale;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

public class SimpleRegexVerifier implements IVerifier<String> {
	
	private final String regex;
	
	public SimpleRegexVerifier( final String theRegex) {
		super();
		this.regex = theRegex;
	}

	@Override
	public VerificationResult<String> verify(String theInput, Object... theParams) {
		boolean verified = theInput.matches( regex);
		
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Input %s rejected.", theInput), ErrorKind.warning); 
		MessageRecord inputFormatHint = new MessageRecord( "input_info", String.format( "Only strings that matches regex \"%s\" accepted.", regex), ErrorKind.info); 
		MessageRecord inputAcceptedSuccess = new MessageRecord( "invalid_input_warning", String.format( "Input \"%s\" accepted.", theInput), ErrorKind.success); 	
		
		if ( verified) 
			return new VerificationResult<String>( this, theInput, verified, Arrays.asList( inputAcceptedSuccess));
		else
			return new VerificationResult<String>( this, theInput, verified, Arrays.asList( inputRejectedWarning, inputFormatHint));	
	}

}
