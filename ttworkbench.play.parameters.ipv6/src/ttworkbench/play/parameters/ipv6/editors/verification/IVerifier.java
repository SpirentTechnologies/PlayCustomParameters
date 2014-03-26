package ttworkbench.play.parameters.ipv6.editors.verification;

public interface IVerifier<T> {

	/**
	 * Start a parameterized verification of an Input.
	 * 
	 * @param theInput
	 *          to verify input
	 * @param theParams
	 *          parameters for the verification
	 * @return results of the verification
	 */
	VerificationResult<T> verify(T theInput, Object... theParams);

}
