package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
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

		// //The MAC validator
		// IParameterValidator macValidator = new AbstractValidator(
		// "MAC Address Validator", ""){
		// @Override
		// protected List<ValidationResult> validateParameter( IParameter parameter)
		// {
		// List<ValidationResult> validationResults = new
		// ArrayList<ValidationResult>();
		//
		// String theValue = ((StringValue)parameter.getValue()).getTheContent();
		// System.out.println("this is my parameter value:  "+theValue);
		// if ( isMacAddress( theValue))
		// validationResults.add( new
		// ValidationResult("This entry has a valid MAC Address format.",
		// ErrorKind.success, "tag_is_mac"));
		// else {
		// validationResults.add( new ValidationResult(
		// "This entry does not have a valid MAC Address format.", ErrorKind.error,
		// "tag_is_mac"));
		// }
		// return validationResults;
		// }
		//
		// };

		// assign each parameter to the corresponding editor in this widget
		getConfigurator().assign( new IPEditor( new IPv4Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new OrVerifier( new IPv4Verifier(), new IPv6Verifier())), ipWidget,
				ipv4Parameter);
		getConfigurator().assign( new IPEditor( new IPv6Verifier()), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPEditor( new HostnameVerifier()), ipWidget, ipv4Parameter);

		// //Register the mac validator to the editor
		// macValidator.registerForMessages( editor_MacAddress);

		// assign the validator to the parameter
		// getConfigurator().assign( macValidator, macWidget, parameter_MacAddress);

	}
}
