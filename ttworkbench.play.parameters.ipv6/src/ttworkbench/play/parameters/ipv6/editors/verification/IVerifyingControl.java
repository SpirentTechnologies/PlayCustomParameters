package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

public interface IVerifyingControl<C extends Control,P> extends IParameterControl<C,P> {

  void setText( String theText);
  
  String getText();
	
	List<VerificationResult<String>> getVerificationResults();

	void setListener(IVerificationListener<String> theListener);

	void addVerificatorToEvent(IVerificator<String> theVerificator, int theEventType);
	
}
