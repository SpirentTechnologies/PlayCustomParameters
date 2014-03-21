package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.HashMap;
import java.util.Map;

public class Verificators {
	
	private static final Map<Class<?>, IVerificator<?>> verificators = new HashMap<Class<?>, IVerificator<?>>();
	
	private static < T extends IVerificator<?>> T createVerificator( Class<T> theClass) {
  	try {
  		return theClass.newInstance();
  	} catch ( Exception e) {
  		return null;
  	}
	}
	
	
	@SuppressWarnings("unchecked")
	public static < T extends IVerificator<?>> T getVerificator( Class<T> theClass) {
  	if ( verificators.containsKey( theClass))
  		return (T) verificators.get( theClass);
  	
  	return newVerificator( theClass);
  }

  @SuppressWarnings("unchecked")
	public static < T extends IVerificator<?>> T newVerificator( Class<T> theClass) {
  	IVerificator<?> verificator = createVerificator( theClass);
  	if ( !verificators.containsKey( theClass))
  		verificators.put( theClass, verificator);
  	return (T) verificator;
  }

}
