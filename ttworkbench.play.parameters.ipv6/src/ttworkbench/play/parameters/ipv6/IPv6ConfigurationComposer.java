package ttworkbench.play.parameters.ipv6;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.composer.CustomWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.DefaultWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.FibWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.IWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.MacWidgetComposer;
import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;
import ttworkbench.play.parameters.settings.loader.DataLoader;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;

public class IPv6ConfigurationComposer implements IConfigurationComposer {
	
	
	@Override
	// TODO refactor: rename method to "compose()" ?
	
	public void createWidgets(IConfigurator theConfigurator) {
		ParameterMap parametersMap = new ParameterMap( theConfigurator);
		if ( parametersMap.isEmpty())
			return;

		// first added widget will be set automatically as default widget.
		List<IWidgetComposer> widgetComposers = new ArrayList<IWidgetComposer>();

		// default preset widgets
		widgetComposers.add( new DefaultWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new FibWidgetComposer( theConfigurator, parametersMap));
		// widgetComposers.add( new IPv6WidgetComposer( theConfigurator, parametersMap));

		
		// custom widget configuration
		try {
			for (Data.Widget widget : DataLoader.getInstance().getWidgets()) {
				widgetComposers.add( new CustomWidgetComposer( theConfigurator, parametersMap, widget));
			}
		} catch (ParameterConfigurationException e) {
			// TODO handle if needed
			e.printStackTrace();
		}
		
		
		
		theConfigurator.beginConfigure();
		for (IWidgetComposer widgetComposer : widgetComposers) {
			widgetComposer.compose();
		}

		theConfigurator.endConfigure();
	}
}
