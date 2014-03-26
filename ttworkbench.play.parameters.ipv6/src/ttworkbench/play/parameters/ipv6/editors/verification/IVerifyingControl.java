package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

public interface IVerifyingControl<C extends Control, P> extends IParameterControl<C, P> {

	/**
	 * Set the text after a successfully passed verification process under use of
	 * the given verifier.
	 * 
	 * @param theText
	 */
	void setText(String theText);

	/**
	 * Set the text, without a verification.
	 * 
	 * @param theText
	 *          unverified text, to be set.
	 */
	void forceText(String theText);

	/**
	 * Get the actual text of the control.
	 * 
	 * @return the actual text
	 */
	String getText();

	/**
	 * Return the results of a verification for informational purpose.
	 * 
	 * @return Results of the last verification.
	 */
	List<VerificationResult<String>> getVerificationResults();

	/**
	 * Set an ActionListener that will be invoked, if some actions on the Control
	 * happens.
	 * 
	 * @param theListener
	 *          the Listener to be invoked.
	 */
	void addListener(IVerificationListener<String> theListener);

	/**
	 * Add a verifier to an event. If the event is raised, the verifier will be
	 * triggered.
	 * 
	 * @param theVerifier
	 *          verifier to add for verification
	 * @param theEventType
	 *          the event, which triggers the verification
	 * 
	 * @see org.eclipse.swt.SWT
	 * @see org.eclipse.swt.widgets.Control
	 */
	void addVerifierToEvent(IVerifier<String> theVerifier, int theEventType);

}
