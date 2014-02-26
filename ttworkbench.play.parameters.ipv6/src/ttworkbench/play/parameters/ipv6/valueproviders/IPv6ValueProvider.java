package ttworkbench.play.parameters.ipv6.valueproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class IPv6ValueProvider implements IParameterValueProvider {

	@Override
	public void setAttribute(String theName, String theValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Set<String> demoValues() {
		Set<String> values = new HashSet<String>();
		values.add( "0:0:0:0:0:ffff:192.1.56.10");
		values.add( "::ffff:192.1.56.10/96");
		values.add( "3ffe:1900:4545:3:200:f8ff:fe21:67cf");
		values.add( "fe80:0:0:0:200:f8ff:fe21:67cf");
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T>Set<T> getAvailableValues( IParameter<T> theParameter) {
		// TODO Auto-generated method stub
		return (Set<T>) demoValues();
	}

}
