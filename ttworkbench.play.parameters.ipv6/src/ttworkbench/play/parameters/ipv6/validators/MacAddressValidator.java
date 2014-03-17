package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public class MacAddressValidator extends AbstractValidator {

	private static final String TITLE = "MAC Address Validator";
	private static final String DESCRIPTION = "";
	
	public MacAddressValidator() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter) {
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

		String theValue = ((StringValue)parameter.getValue()).getTheContent();
		System.out.println("this is my parameter value:  "+theValue);
		if ( isMacAddress( theValue))
			validationResults.add( new ValidationResult("This entry has a valid MAC Address format.", ErrorKind.success, "tag_is_mac"));
		else {
			validationResults.add( new ValidationResult( "This entry does not have a valid MAC Address format.", ErrorKind.error, "tag_is_mac"));
		}
		return validationResults;
	}

	// Chechk if the entered Mac Address has a valid format
	private boolean isMacAddress(String macEntry){
		final String MAC_PATTERN = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";
		if (macEntry.matches( MAC_PATTERN)){
			return true;
		} else {
			return false;
		}
	}

}
