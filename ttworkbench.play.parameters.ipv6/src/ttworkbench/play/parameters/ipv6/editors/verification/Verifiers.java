package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Verifiers {
	
	private static final Map<Class<?>, IVerifier<?>> verifiers = new HashMap<Class<?>, IVerifier<?>>();
	
	private static < T extends IVerifier<?>> T createVerifier( Class<T> theClass) {
  	try {
  		return theClass.newInstance();
  	} catch ( Exception e) {
  		return null;
  	}
	}
	
	
	@SuppressWarnings("unchecked")
	public static < T extends IVerifier<?>> T getVerifier( Class<T> theClass) {
  	if ( verifiers.containsKey( theClass))
  		return (T) verifiers.get( theClass);
  	
  	return newVerifier( theClass);
  }

  @SuppressWarnings("unchecked")
	public static < T extends IVerifier<?>> T newVerifier( Class<T> theClass) {
  	IVerifier<?> verifier = createVerifier( theClass);
  	if ( !verifiers.containsKey( theClass))
  		verifiers.put( theClass, verifier);
  	return (T) verifier;
  }

}
