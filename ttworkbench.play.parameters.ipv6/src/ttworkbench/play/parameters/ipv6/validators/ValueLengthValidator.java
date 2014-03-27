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

public class ValueLengthValidator extends ContextualValidator {
	public ValueLengthValidator() {
		super("Parameter Value Length Validator", "Checks if the value has the right length");
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
  			? String.format( "%s: This entry of %s doesn't have the correct length. \"%s\" doesn't contain %s characters.",
  					this.getTitle(),
  					parameter.getName(),
  					content,
  					value)
  			: String.format( "%s: This entry of %s has the correct length.", this.getTitle(), parameter.getName()),
  			
  			correctLength
  			? ErrorKind.success
  			: ErrorKind.error,
  			
  		theClient,
  		
  		"tag_is_correct_length"));
		
    	
    return validationResults;
	}

}

