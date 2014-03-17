package ttworkbench.play.parameters.settings.exceptions;

public class ParameterDefinitionNotFoundException extends ParameterConfigurationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterDefinitionNotFoundException(String message) {
		super(message);
	}
	public ParameterDefinitionNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
