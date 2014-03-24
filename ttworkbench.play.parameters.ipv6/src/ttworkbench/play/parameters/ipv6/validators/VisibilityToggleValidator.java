package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class VisibilityToggleValidator extends ContextualValidator {

	public VisibilityToggleValidator() {
		super("Visibility Toggle Validator", "");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		
		String pattern = getAttribute( "pattern");
		String value = String.valueOf( parameter.getValue());

		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		
		boolean visible = value.matches( pattern);
		String tag = "tag_visible_hint";
		
		if(visible) {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: visibility=true", this.getTitle()), ErrorKind.info, theClient, tag));
		} else {
			validationResults.add( new ValidationResultMessage(  String.format( "%s: visibitly=false.", this.getTitle()), ErrorKind.info, theClient, tag));
		}
		return validationResults;
	}	

}
