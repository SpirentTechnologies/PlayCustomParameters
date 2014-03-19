package ttworkbench.play.parameters.ipv6.validators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public class FibValidator_SUCC extends FibValidator {

	public FibValidator_SUCC() {
		super("Fibonacci Successor Validator");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter) {

		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		BigInteger fibSuccValue = this.<IntegerValue>getParameter( ParameterKey.PX_FIB_SUCC_NUMBER).getValue().getTheNumber();
		BigInteger fibValue = this.<IntegerValue>getParameter( ParameterKey.PX_FIB_NUMBER).getValue().getTheNumber();
		BigInteger fibNextValue = nextFibonacciNumber( fibValue);

		if ( fibSuccValue.compareTo( fibNextValue) == 0) {
			validationResults.add( new ValidationResult(  String.format( "%s: %s is the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.success, "tag_succ_fib"));
		} else {
			validationResults.add( new ValidationResult(  String.format( "%s: %s is NOT the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.error, "tag_succ_fib"));
			validationResults.add( new ValidationResult(  String.format( "%s: %s is successor of %s.", this.getTitle(), fibNextValue, fibValue), ErrorKind.info, "tag_succ_fib_hint"));    	
		}
		return validationResults;
	}	

}
