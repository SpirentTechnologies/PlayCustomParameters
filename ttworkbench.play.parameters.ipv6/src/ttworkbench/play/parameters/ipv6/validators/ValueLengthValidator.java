package ttworkbench.play.parameters.ipv6.validators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class ValueLengthValidator extends FibValidator {
	public ValueLengthValidator() {
		super("Parameter Value Length Validator");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		BigInteger bigValue = ((IntegerValue) getContext().getParameters()[0].getValue()).getTheNumber(); 
		int value = bigValue.intValue();
		
		String content = ParameterValueUtil.getValue( parameter);
		boolean correctLength = content.length()==value; 
		
    validationResults.add( new ValidationResultMessage(
    		
  		correctLength
  			? String.format( "%s: The value of %s has not correct length. \"%s\" is not %s characters.",
  					this.getTitle(),
  					parameter.getName(),
  					content,
  					value)
  			: String.format( "%s: The value of %s has the correct number of characters.", this.getTitle(), parameter.getName()),
  			
  			correctLength
  			? ErrorKind.success
  			: ErrorKind.error,
  			
  		theClient,
  		
  		"tag_is_correct_length"));
		
    	
    return validationResults;
	}

}

