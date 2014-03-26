package ttworkbench.play.parameters.ipv6.validators.exceptions;

public class ParameterValidationException extends ValidationException  {
	private static final long serialVersionUID = 1L;

	public ParameterValidationException() {
		super();
	}
	public ParameterValidationException(String msg) {
		super(msg);
	}
	public ParameterValidationException(String msg, Exception e) {
		super(msg, e);
	}
}
