package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.components.messageviews.IMessageView;

import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public abstract class NotifyingWidget extends AbstractWidget implements IMessageHandler {

	public NotifyingWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	
	protected abstract IMessageView getMessageView();
	
	
	

	@Override
	public void report( final IParameterValidator theValidator, final List<ValidationResult> theValidationResults,
			IParameter theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IMessageView messageView = getMessageView();
				String senderId = String.format( "%s@%s", theValidator.getClass().getName(), theValidator.hashCode());
				messageView.beginUpdateForSender( senderId);
				for (ValidationResult validationResult : theValidationResults) {
					if ( validationResult.isTagged()) {
						messageView.putTaggedMessage( validationResult.getTag(), validationResult.getErrorMessage(), validationResult.getErrorKind());
					} else {
						messageView.addUntaggedMessage( validationResult.getErrorMessage(), validationResult.getErrorKind());					
					}
				}
				messageView.endUpdate();
			}
		});
		
	}


}
