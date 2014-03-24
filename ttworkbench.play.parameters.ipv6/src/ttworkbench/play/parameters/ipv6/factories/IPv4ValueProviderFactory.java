package ttworkbench.play.parameters.ipv6.factories;

import java.net.Inet6Address;
import java.util.List;

import ttworkbench.play.parameters.ipv6.valueproviders.IPValueProvider;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProviderFactory;

public class IPv4ValueProviderFactory implements IParameterValueProviderFactory {

	public IPv4ValueProviderFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isApplicable(IParameter<? extends Object> theParameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTitle(String theTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String theDescription) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IParameterValueProvider create() {
		return new IPValueProvider<Inet6Address>( Inet6Address.class);
	}

}
