package ttworkbench.play.parameters.ipv6.validators.exceptions;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}
	public ValidationException(String msg) {
		super(msg);
	}
	public ValidationException(String msg, Exception e) {
		super(msg, e);
	}
}
