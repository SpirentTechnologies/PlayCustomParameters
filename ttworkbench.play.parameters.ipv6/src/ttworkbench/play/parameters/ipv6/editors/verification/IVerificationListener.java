package ttworkbench.play.parameters.ipv6.editors.verification;

public interface IVerificationListener<T> {

	/**
	 * Hook, that will be executed once, before a verification.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 */
	void beforeVerification(VerificationEvent<T> theEvent);

	/**
	 * Hook, that will be executed, after verification of one verifier. If their
	 * are more then one verifier to be processed.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 * 
	 * @see IVerifier
	 */
	void afterVerificationStep(VerificationEvent<T> theEvent);

	/**
	 * Hook, that will be executed once, after all verifications.
	 * 
	 * @param theEvent
	 *          the triggering event of a verification
	 */
	void afterVerification(VerificationEvent<T> theEvent);

}