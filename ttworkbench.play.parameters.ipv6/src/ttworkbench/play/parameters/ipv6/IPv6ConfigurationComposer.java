package ttworkbench.play.parameters.ipv6;

import java.util.Set;

import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.editors.IPv6Editor;
import ttworkbench.play.parameters.ipv6.editors.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;
import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class IPv6ConfigurationComposer implements IConfigurationComposer {

	private static void createAndComposeDefaultWidget( IConfigurator theConfigurator) {
		IWidget defaultWidget = new DefaultWidget();
		theConfigurator.addWidget( defaultWidget);
		
		// TODO: replace demo composition 
		Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter parameter : parameters) {
			theConfigurator.assign( new DefaultEditor(), parameter, defaultWidget);	
		}
	}
	
	
	private static void createAndComposeIPv6Widget( IConfigurator theConfigurator) {
		IWidget IPv6Widget = new IPv6Widget();
		theConfigurator.addWidget( IPv6Widget);

		// TODO: replace demo composition 
		Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter parameter : parameters) {
			theConfigurator.assign( new MacAddressEditor(), parameter, IPv6Widget);	
		}
	}
	
	@Override
	public void createWidgets(IConfigurator theConfigurator) {
		// first added widget will be set automatically as default widget.
		createAndComposeDefaultWidget( theConfigurator);
		createAndComposeIPv6Widget( theConfigurator);
		
		
	}

}
