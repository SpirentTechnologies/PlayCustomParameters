package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public abstract class NotifyingWidget extends AbstractWidget implements IMessageHandler {

	public NotifyingWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	

	@Override
	public void report(IParameterValidator theValidator, List<ValidationResult> theValidationResults,
			IParameter theParameter) {
		// TODO Auto-generated method stub
		
	}


}
