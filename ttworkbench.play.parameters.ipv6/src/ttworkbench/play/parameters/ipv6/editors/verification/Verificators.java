package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.HashMap;
import java.util.Map;

public class Verificators {
	
	private static final Map<Class, Verificator> verificators = new HashMap<Class, Verificator>();
	
	private static < T extends Verificator> T createVerificator( Class<T> theClass) {
  	try {
  		return theClass.newInstance();
  	} catch ( Exception e) {
  		return null;
  	}
	}
	
  public static < T extends Verificator> T getVerificator( Class<T> theClass) {
  	if ( verificators.containsKey( theClass))
  		return (T) verificators.get( theClass);
  	
  	return newVerificator( theClass);
  }

  public static < T extends Verificator> T newVerificator( Class<T> theClass) {
  	Verificator verificator = createVerificator( theClass);
  	if ( !verificators.containsKey( theClass))
  		verificators.put( theClass, verificator);
  	return (T) verificator;
  }

}
