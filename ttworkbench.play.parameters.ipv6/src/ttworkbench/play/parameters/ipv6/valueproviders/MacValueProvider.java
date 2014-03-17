package ttworkbench.play.parameters.ipv6.valueproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacValueProvider implements IParameterValueProvider{

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

	@Override
	public <T> Set<T> getAvailableValues(IParameter<T> theParameter) {
		// TODO Auto-generated method stub
		Set<T> values = new HashSet();
		values.add( theParameter.getValue());
		values.add( theParameter.getDefaultValue());
		return values;
	}

}
