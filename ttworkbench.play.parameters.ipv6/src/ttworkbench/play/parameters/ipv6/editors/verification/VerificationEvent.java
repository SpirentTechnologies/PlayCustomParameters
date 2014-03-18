package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.ArrayList;
import java.util.List;

public class VerificationEvent<T> {
	
	public T inputToVerify;
	
	public boolean skipVerification = false;
	
	public List<VerificationResult<T>> verificationResults; 
	
	public boolean doit = true;
	
	public Object[] verificatorParams = {};
	
	public VerificationEvent( T theInputToVerify, List<VerificationResult<T>> theVerificationResults) {
		this.inputToVerify = theInputToVerify;
		this.verificationResults = theVerificationResults;
	}
	
}
