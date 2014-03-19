package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.Arrays;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;


public class SimpleValidatorContext implements IValidatorContext {
	private ArrayList<IParameter<?>> parameters = new ArrayList<IParameter<?>>();
	private final IConfigurator configurator;

	public SimpleValidatorContext() {
		this.configurator = null;
	}
	public SimpleValidatorContext(IConfigurator widgetConfigurator, IParameter<?>... contextParameters) {
		this.parameters.addAll( Arrays.asList( contextParameters));
		this.configurator = widgetConfigurator;
	}

	@Override
	public IParameter<?>[] getParameters() {
		return parameters.toArray(new IParameter<?>[0]);
	}
	
	public void addParameter(IParameter<?> parameter) {
		parameters.add( parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IParameterEditor<T>[] getEditorsForParameter(IParameter<T> theParameter) {
		if(configurator!=null) {
			return (IParameterEditor<T>[]) configurator
					.getAvailableEditors( theParameter).toArray( new IParameterEditor<?>[0]);
		}
		return (IParameterEditor<T>[]) new IParameter<?>[0];
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( configurator == null) ? 0 : configurator.hashCode());
		result = prime * result + ( ( parameters == null) ? 0 : parameters.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleValidatorContext other = (SimpleValidatorContext) obj;
		if (configurator == null) {
			if (other.configurator != null)
				return false;
		} else if (!configurator.equals( other.configurator))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals( other.parameters))
			return false;
		return true;
	}
}
