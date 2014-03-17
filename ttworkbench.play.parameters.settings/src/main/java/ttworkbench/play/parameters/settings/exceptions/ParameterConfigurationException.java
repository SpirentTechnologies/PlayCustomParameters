package ttworkbench.play.parameters.settings.exceptions;

public class ParameterConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterConfigurationException() {
		super();
	}
	public ParameterConfigurationException(String msg) {
		super(msg);
	}
	public ParameterConfigurationException(String msg, Throwable e) {
		super(msg, e);
	}

}
