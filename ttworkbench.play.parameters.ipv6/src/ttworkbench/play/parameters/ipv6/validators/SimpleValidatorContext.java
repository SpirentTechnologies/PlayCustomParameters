package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.Arrays;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class SimpleValidatorContext implements IValidatorContext {
	private ArrayList<IParameter<?>> parameters = new ArrayList<IParameter<?>>();
	private ArrayList<IWidget> widgets = new ArrayList<IWidget>();
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

	@Override
	public IWidget[] getWidgets() {
		return widgets.toArray(new IWidget[0]);
	}
	
	public void addParameter(IParameter<?> parameter) {
		parameters.add( parameter);
	}
	
	public void addWidget(IWidget theWidget) {
		widgets.add( theWidget);
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
	


}
