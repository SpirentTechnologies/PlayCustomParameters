package ttworkbench.play.parameters.ipv6;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class ParameterMap {
	
	private Map<String,IParameter<?>> idToParameterMap;
	private Collection<IParameter<?>> parameters;
	
	public ParameterMap( final IConfigurator theConfigurator) {
		loadParameters( theConfigurator);
	}
	
	private void loadParameters( IConfigurator theConfigurator) {
    this.idToParameterMap = new ConcurrentHashMap<String, IParameter<?>>();
		this.parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter<?> parameter : parameters) {
			idToParameterMap.put( parameter.getId(), parameter);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Value> IParameter<T> getParameterById( final String theId) {
		return (IParameter<T>) idToParameterMap.get( theId);
	}
	
	public Collection<IParameter<?>> getAllParameters() {
		return parameters;
	}

	public boolean isEmpty() {
		return idToParameterMap.isEmpty();
	}
	
}