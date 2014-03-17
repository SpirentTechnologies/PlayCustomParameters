package ttworkbench.play.parameters.settings.exceptions;

public class ValidatorClassNotFoundException extends ParameterConfigurationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatorClassNotFoundException() {
		super();
	}
	public ValidatorClassNotFoundException(String msg) {
		super(msg);
	}
	public ValidatorClassNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}

}
