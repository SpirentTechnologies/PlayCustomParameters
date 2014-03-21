package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

public class VerificationEvent<T> {
	
	public int eventType;
	
	public T inputToVerify;
	
	public boolean skipVerification = false;
	
	public List<VerificationResult<T>> verificationResults; 
	
	public boolean doit = true;
	
	public Object[] verificatorParams = {};
	
	public VerificationEvent( int theEventType, T theInputToVerify, List<VerificationResult<T>> theVerificationResults) {
		this.eventType = theEventType;
		this.inputToVerify = theInputToVerify;
		this.verificationResults = theVerificationResults;
	}
	
}
