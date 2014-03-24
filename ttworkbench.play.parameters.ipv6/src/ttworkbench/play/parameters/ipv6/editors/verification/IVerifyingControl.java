package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

public interface IVerifyingControl<C extends Control,P> extends IParameterControl<C,P> {

	/**
	 * Set the text after a successfully passed verification process under use of the given verifier.
	 * @param theText
	 */
  void setText( String theText);
  
  void forceText( String theText);
  
  String getText();
	
	List<VerificationResult<String>> getVerificationResults();

	void setListener(IVerificationListener<String> theListener);

	void addVerifierToEvent(IVerifier<String> theVerifier, int theEventType);
	
}
