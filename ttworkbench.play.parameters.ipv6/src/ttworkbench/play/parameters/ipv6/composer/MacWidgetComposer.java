package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.macaddr.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.widgets.MacWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class MacWidgetComposer extends WidgetComposer {

	// get relevant parameters
	final IParameter<StringValue> parameter_MacAddress = getParametersMap().getParameterById( "PX_MAC_UCA_HS01");

	public MacWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// declare a Mac Address Widget
		IWidget macWidget = new MacWidget();
		// add the Mac widget to the frame work
		getConfigurator().addWidget( macWidget);

		getConfigurator().assign( new MacAddressEditor(), macWidget, parameter_MacAddress);

	}
}