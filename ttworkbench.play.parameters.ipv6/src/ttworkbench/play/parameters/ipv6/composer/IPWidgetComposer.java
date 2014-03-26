package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.floatingpoint.FloatingPointEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.HostnameVerifier;
import ttworkbench.play.parameters.ipv6.editors.ip.IPEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv4Verifier;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv6Verifier;
import ttworkbench.play.parameters.ipv6.editors.verification.OrVerifier;
import ttworkbench.play.parameters.ipv6.widgets.IPWidget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class IPWidgetComposer extends WidgetComposer {

	// get relevant parameters
	final IParameter<String> ipv4Parameter = getParametersMap().getParameterById( "PX_IPv4_ADDR_IUT");
	final IParameter<String> floatParameter = getParametersMap().getParameterById( "PX_FLOAT_NUMBER");

	public IPWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// declare a IP Address Widget
		IWidget ipWidget = new IPWidget();
		// add the IP widget to the frame work
		getConfigurator().addWidget( ipWidget);

		// assign each parameter to the corresponding editor in this widget
		getConfigurator().assign( new IPEditor( new IPv4Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new OrVerifier( new IPv4Verifier(), new IPv6Verifier())), ipWidget,
				ipv4Parameter);
		getConfigurator().assign( new IPEditor( new IPv6Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new HostnameVerifier()), ipWidget, ipv4Parameter);

		getConfigurator().assign( new FloatingPointEditor(), ipWidget, floatParameter);

	}
}
