package ttworkbench.play.parameters.ipv6.composer;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.macaddr.MacAddressEditor;
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
	final IParameter<StringValue> parameter_MacAddress = getParametersMap().getParameterById( "PC_MAC_UCA_HS01");

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

		ValidatingEditor<?> editor_MacAddress = new MacAddressEditor();

		// The MAC validator
		IParameterValidator macValidator = new AbstractValidator( "MAC Address Validator", "") {
			@Override
			protected List<ValidationResult> validateParameter(IParameter parameter, Object theClient) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				String theValue = ( (StringValue) parameter.getValue()).getTheContent();
				System.out.println( "this is my parameter value:  " + theValue);
				if (isMacAddress( theValue))
					validationResults.add( new ValidationResultMessage( "This entry has a valid MAC Address format.", ErrorKind.success, "tag_is_mac"));
				else {
					validationResults.add( new ValidationResultMessage( "This entry does not have a valid MAC Address format.",
							ErrorKind.error, "tag_is_mac"));
				}
				return validationResults;
			}

		};

		// assign each parameter to the corresponding editor in this widget
		getConfigurator().assign( editor_MacAddress, macWidget, parameter_MacAddress);

		// Register the mac validator to the editor
		macValidator.registerForMessages( editor_MacAddress);

		// assign the validator to the parameter
		getConfigurator().assign( macValidator, macWidget, parameter_MacAddress);

	}

	// Chechk if the entered Mac Address has a valid format
	private boolean isMacAddress(String macEntry) {
		final String MAC_PATTERN1 = "^([0-9a-fA-F]{2}[:-]){5}([0-9a-fA-F]{2})$";
		final String MAC_PATTERN2 = "^([0-9a-fA-F]{2}[-]){5}([0-9a-fA-F]{2})$";
		final String MAC_PATTERN3 = "^([0-9a-fA-F]{12})$";
		if (macEntry.matches( MAC_PATTERN1) || macEntry.matches( MAC_PATTERN2) || macEntry.matches( MAC_PATTERN3)) {
			return true;
		} else {
			return false;
		}
	}
}