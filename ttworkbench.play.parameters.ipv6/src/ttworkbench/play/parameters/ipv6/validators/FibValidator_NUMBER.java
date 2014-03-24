package ttworkbench.play.parameters.ipv6.validators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class FibValidator_NUMBER extends FibValidator {
	public FibValidator_NUMBER() {
		super( "Fibonacci Number Validator");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		BigInteger theValue =( (IntegerValue) parameter.getValue()).getTheNumber(); 
		if ( isFibonacciNumber( theValue))
			validationResults.add( new ValidationResultMessage( String.format( "%s: %s is a fibonacci number.", this.getTitle(), theValue), ErrorKind.success, theClient, "tag_is_fib"));
		else {
			validationResults.add( new ValidationResultMessage( String.format( "%s: %s is NOT a fibonacci number.", this.getTitle(), theValue), ErrorKind.error, theClient, "tag_is_fib"));
			validationResults.add( new ValidationResultMessage( String.format( "%s: Next succeeding fibonacci number to %s is %s.", this.getTitle(), theValue, nextFibonacciNumber( theValue)), ErrorKind.info, theClient, "tag_fib_hint"));
		}
		return validationResults;
	}

};