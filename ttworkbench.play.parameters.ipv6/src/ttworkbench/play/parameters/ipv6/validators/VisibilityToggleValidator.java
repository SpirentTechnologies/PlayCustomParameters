package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultAction;

public class VisibilityToggleValidator extends ContextualValidator {

	public VisibilityToggleValidator() {
		super("Visibility Toggle Validator", "");
	}
	
	@Override
	protected List<ValidationResult> validateParameter( IParameter parameter, Object theClient) {
		
		String pattern = getAttribute( "pattern");
		String value = String.valueOf( parameter.getValue());

		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		
		final boolean visible = value.matches( pattern);
		String tag = "tag_visible_hint_"+visible;
		
		validationResults.add( new ValidationResultAction( String.format( "%s: visibility="+visible, this.getTitle()), ErrorKind.info, theClient, tag) {

			@Override
			public void triggerEditor(IParameterEditor<?> theEditor) {
				theEditor.setVisible( visible);
			}

			@Override
			public void triggerWidget(IWidget theWidget) {
				theWidget.setVisible( visible);
			}
			
		});

		return validationResults;
	}	

}
