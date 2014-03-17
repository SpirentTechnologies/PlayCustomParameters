package ttworkbench.play.parameters.settings.exceptions;

public class ValidatorDefinitionNotFoundException extends ParameterConfigurationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatorDefinitionNotFoundException(String message) {
		super(message);
	}
	public ValidatorDefinitionNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
