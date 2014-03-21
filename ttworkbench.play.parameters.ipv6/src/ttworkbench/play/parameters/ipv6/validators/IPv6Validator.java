package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public class IPv6Validator extends AbstractValidator {

	private static final String TITLE = "IPv6 Validator";
	private static final String DESCRIPTION = "";
	
	public IPv6Validator() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	protected List<ValidationResult> validateParameter(IParameter theParameter, Object theClient) {
		return new ArrayList<ValidationResult>();
	}


}
