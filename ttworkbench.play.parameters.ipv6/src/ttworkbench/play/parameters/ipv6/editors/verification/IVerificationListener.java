package ttworkbench.play.parameters.ipv6.editors.verification;


public interface IVerificationListener<T> {

	void beforeVerification( VerificationEvent<T> theEvent);
	
	void afterVerificationStep( VerificationEvent<T> theEvent);
	
	void afterVerification( VerificationEvent<T> theEvent);
	
}