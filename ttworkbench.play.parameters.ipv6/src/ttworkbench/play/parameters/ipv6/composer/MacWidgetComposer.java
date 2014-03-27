package ttworkbench.play.parameters.ipv6.composer;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv4Verifier;
import ttworkbench.play.parameters.ipv6.editors.macaddr.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.editors.macaddr.MacPatternVerifier;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.widgets.MacWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class MacWidgetComposer extends WidgetComposer {

	// get relevant parameters
	final IParameter<String> parameter_MacAddress = getParametersMap().getParameterById( "PC_MAC_UCA_HS01");

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

		getConfigurator().assign( new MacAddressEditor( new MacPatternVerifier()), macWidget, parameter_MacAddress);

	}
}