package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

import org.eclipse.swt.widgets.Widget;

public interface IVerifyingWidget<T extends Widget> {

  void setText( String theText);
  
  String getText();
	
	T getEncapsulatedWidget();

	List<VerificationResult<String>> getVerificationResults();

	void setListener(IVerificationListener<String> theListener);

	void addVerificatorToEvent(IVerificator<String> theVerificator, int theEventType);
	
}
