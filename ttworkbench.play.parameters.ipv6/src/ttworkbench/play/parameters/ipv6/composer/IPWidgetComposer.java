package ttworkbench.play.parameters.ipv6.composer;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.ip.IPv4Editor;
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
		// declare a Mac Address Widget
		IWidget ipWidget = new IPWidget();
		// add the Mac widget to the frame work
		getConfigurator().addWidget( ipWidget);

		ValidatingEditor<?> ipv4Editor = new IPv4Editor();

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
		getConfigurator().assign( ipv4Editor, ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPv4Editor(), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPv4Editor(), ipWidget, ipv4Parameter);
		getConfigurator().assign( new IPv4Editor(), ipWidget, ipv4Parameter);

		// //Register the mac validator to the editor
		// macValidator.registerForMessages( editor_MacAddress);

		// assign the validator to the parameter
		// getConfigurator().assign( macValidator, macWidget, parameter_MacAddress);

	}
}
