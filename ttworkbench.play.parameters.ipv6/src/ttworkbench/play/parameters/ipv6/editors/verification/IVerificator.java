package ttworkbench.play.parameters.ipv6.editors.verification;


public interface IVerificator<T> {
	
	VerifyResult<T> verify( T theInput, Object ... theParams);
	
	
	

}