package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.validators.FibValidator.ParameterKey;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class MacAddressValidator extends ContextualValidator {
	
	private static final String TITLE = "MAC Address Validator";
	private static final String DESCRIPTION = "";
	
	public MacAddressValidator() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		String mac1 = this.<StringValue>getParameter( 0).getValue().getTheContent();
		String mac2 = this.<StringValue>getParameter( 1).getValue().getTheContent();
		
		if(mac1.equalsIgnoreCase( mac2)){
			validationResults.add( new ValidationResultMessage(  "This configuration is not valid.", ErrorKind.error, theClient, "tag_val_mac"));
			validationResults.add( new ValidationResultMessage(  "The MAC-Addresses cannot have the same value", ErrorKind.info, theClient, "tag_val_mac_hint"));
		}else{
			validationResults.add( new ValidationResultMessage(  "This configuration is valid.", ErrorKind.success, theClient, "tag_val_mac"));
		}
		return validationResults;
	}

}
