package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.settings.Data;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;


public class CustomWidgetComposer extends WidgetComposer {

	private Data.Widget widget;

	public CustomWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap, Data.Widget theWidget) {
		super( theConfigurator, theParametersMap);
		this.widget = widget;
	}
	
	@Override
	public void compose() {
		// TODO Auto-generated method stub
		
	}

	
}
