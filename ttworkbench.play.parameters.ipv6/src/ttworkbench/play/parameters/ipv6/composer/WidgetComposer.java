package ttworkbench.play.parameters.ipv6.composer;


import ttworkbench.play.parameters.ipv6.ParameterMap;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;

public abstract class WidgetComposer implements IWidgetComposer {
	
	private final IConfigurator configurator;
	private final ParameterMap parametersMap;
	
	public WidgetComposer( final IConfigurator theConfigurator, final ParameterMap theParametersMap) {
	  this.configurator = theConfigurator;
	  this.parametersMap = theParametersMap;				
	}
	
	protected IConfigurator getConfigurator() {
		return configurator;
	}
	
	protected ParameterMap getParametersMap() {
		return parametersMap;
	}
	
	public abstract void compose();
	
	public void resolve() {
		// nothing to resolve
	}
	
}