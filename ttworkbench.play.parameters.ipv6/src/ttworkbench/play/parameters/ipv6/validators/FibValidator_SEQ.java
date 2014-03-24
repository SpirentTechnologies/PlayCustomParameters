package ttworkbench.play.parameters.ipv6.validators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class FibValidator_SEQ extends FibValidator {
	public FibValidator_SEQ() {
		super("Fibonacci Sequence Validator");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
	
		BigInteger inputSeqenceNumber = this.<IntegerValue>getParameter( ParameterKey.PX_N).getValue().getTheNumber();
		BigInteger inputFibValue = this.<IntegerValue>getParameter( ParameterKey.PX_FIB_NUMBER).getValue().getTheNumber();
		BigInteger fibValue = getFibonacciNumber( inputSeqenceNumber);

    if ( inputFibValue.compareTo( fibValue) != 0) {
    	validationResults.add( new ValidationResultMessage( String.format( "%s: %s is NOT the fibonacci number of %s.", this.getTitle(), inputFibValue, inputSeqenceNumber), ErrorKind.error, theClient, "tag_is_not_fib_of_n"));		
      validationResults.add( new ValidationResultMessage(  String.format( "%s: %s is the fibonacci number of %s.", this.getTitle(), fibValue, inputSeqenceNumber), ErrorKind.info, theClient, "tag_is_fib_of_hint")); 
    } else {
    	validationResults.add( new ValidationResultMessage( String.format( "%s: %s is the fibonacci number of %s.", this.getTitle(), inputFibValue, inputSeqenceNumber), ErrorKind.success, theClient, "tag_is_not_fib_of_n"));		
    }
    	
    return validationResults;
	}

}
