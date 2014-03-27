package ttworkbench.play.parameters.ipv6.composer;

import java.util.ArrayList;
import java.util.List;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.editors.enums.EnumEditor;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.widgets.EnumWidget;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public class EnumWidgetComposer extends WidgetComposer{
	
	//get relevant parameters
	final IParameter<StringValue> parameter_MAC_FILTER = getParametersMap().getParameterById( "PC_MAC_FILTER");

	public EnumWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compose() {
		// TODO Auto-generated method stub
			// declare a Enum Address Widget
			IWidget enumWidget = new EnumWidget();
			// add the Enum widget to the frame work
			getConfigurator().addWidget( enumWidget);

			ValidatingEditor<?> editor_MAC_FILTER = new EnumEditor();

			// The Enum validator
			IParameterValidator enumValidator = new AbstractValidator( "Enum Validator", ""){
				@Override
				protected List<ValidationResult> validateParameter(IParameter parameter, Object theClient) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				String theValue = ( (StringValue) parameter.getValue()).getTheContent();
				System.out.println( "this is my Enum parameter value:  " + theValue);
				validationResults.add( new ValidationResultMessage( "This entry is valid.", ErrorKind.success, "tag_is_enum"));
				return validationResults;
			}

		};
		
			// assign each parameter to the corresponding editor in this widget
			getConfigurator().assign( editor_MAC_FILTER, enumWidget, parameter_MAC_FILTER);

			// Register the Enum validator to the editor
			enumValidator.registerForMessages( editor_MAC_FILTER);

			// assign the validator to the parameter
			getConfigurator().assign( enumValidator, enumWidget, parameter_MAC_FILTER);
	}

}
