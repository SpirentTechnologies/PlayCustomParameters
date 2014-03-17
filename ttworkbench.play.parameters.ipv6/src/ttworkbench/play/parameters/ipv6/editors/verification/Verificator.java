package ttworkbench.play.parameters.ipv6.editors.verification;


public interface Verificator<T> {
	
	VerifyResult<T> verify( T theInput, Object ... theParams);
	
	
	

}
