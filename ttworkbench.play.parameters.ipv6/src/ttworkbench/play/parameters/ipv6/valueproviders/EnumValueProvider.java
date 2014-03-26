package ttworkbench.play.parameters.ipv6.valueproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.impl.StringValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class EnumValueProvider implements IParameterValueProvider{

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
		boolean isRadio = false;
		
		Set<T> testEnumRadio = new HashSet<T>();
		Set<T> testEnumCombo = new HashSet<T>();
		
		testEnumRadio.add( theParameter.getValue());
		testEnumRadio.add( theParameter.getDefaultValue());
		
		testEnumCombo.add((T) newString("value1"));
		testEnumCombo.add((T) newString("value2"));
		testEnumCombo.add((T) newString("value3"));
		
		return (isRadio)?testEnumRadio:testEnumCombo;
	}
	
	private StringValue newString( String theString) {
		StringValue stringValue = new StringValueImpl() {};
		stringValue.setTheContent( theString);
	  return stringValue;
	}
}
