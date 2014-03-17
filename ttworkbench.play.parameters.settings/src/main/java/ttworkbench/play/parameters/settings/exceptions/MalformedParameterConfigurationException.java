package ttworkbench.play.parameters.settings.exceptions;

public class MalformedParameterConfigurationException extends ParameterConfigurationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MalformedParameterConfigurationException() {
		super();
	}
	public MalformedParameterConfigurationException(String msg) {
		super(msg);
	}
	public MalformedParameterConfigurationException(String msg, Throwable e) {
		super(msg, e);
	}
}
