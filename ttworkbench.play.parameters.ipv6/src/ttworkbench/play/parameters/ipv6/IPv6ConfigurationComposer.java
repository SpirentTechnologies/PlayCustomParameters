package ttworkbench.play.parameters.ipv6;

import java.util.Set;

import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;
import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class IPv6ConfigurationComposer implements IConfigurationComposer {

	@Override
	public void createWidgets(IConfigurator configurator) {
		// first added widget will be set automatically as default widget.
		IWidget defaultWidget = new DefaultWidget();
		configurator.addWidget( defaultWidget);
		
		// TODO: replace demo composition 
		Set<IParameter> parameters = configurator.getParameterModel().getParameters();
		for (IParameter parameter : parameters) {
			configurator.assign( new DefaultEditor(), parameter, defaultWidget);	
		}
		
		configurator.addWidget(new IPv6Widget());
		
		
	}

}
