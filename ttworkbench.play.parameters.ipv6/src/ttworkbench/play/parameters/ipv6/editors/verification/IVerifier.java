package ttworkbench.play.parameters.ipv6.editors.verification;


public interface IVerifier<T> {
	
	VerificationResult<T> verify( T theInput, Object ... theParams);

}
