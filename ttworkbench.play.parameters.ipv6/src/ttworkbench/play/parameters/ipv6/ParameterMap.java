package ttworkbench.play.parameters.ipv6;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class ParameterMap {
	
	private Map<String,IParameter<?>> idToParameterMap;
	
	public ParameterMap( final IConfigurator theConfigurator) {
		loadParameters( theConfigurator);
	}
	
	private void loadParameters( IConfigurator theConfigurator) {
		idToParameterMap = new LinkedHashMap<String, IParameter<?>>();
		Set<IParameter<?>> parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter<?> parameter : parameters) {
			idToParameterMap.put( parameter.getId(), parameter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> IParameter<T> getParameterById( final String theId) {
		return (IParameter<T>) idToParameterMap.get( theId);
	}
	
	public Collection<IParameter<?>> getAllParameters() {
		return idToParameterMap.values();
	}

	public boolean isEmpty() {
		return idToParameterMap.isEmpty();
	}
	
}