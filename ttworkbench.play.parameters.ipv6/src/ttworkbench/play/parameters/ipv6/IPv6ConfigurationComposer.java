package ttworkbench.play.parameters.ipv6;

import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;

public class IPv6ConfigurationComposer implements IConfigurationComposer {

	@Override
	public void createWidgets(IConfigurator configurator) {
		// TODO implement method
		configurator.addWidget(new IPv6Widget());
	}

}
