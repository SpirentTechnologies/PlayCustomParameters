package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;

import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResultMessage;

public abstract class NotifyingWidget extends AbstractWidget implements IMessageHandler {

	public NotifyingWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	
	protected abstract IMessageView getMessageView();
	
	
	

	@Override
	public void report( final IParameterValidator theValidator, final List<ValidationResultMessage> theValidationResults,
			final IParameter<?> theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IMessageView<?> messageView = getMessageView();
				String senderId = String.valueOf( theValidator.getId());
				messageView.beginUpdateForSender( senderId);
				MessageRecord messageRecord;
				for (ValidationResult validationResult : theValidationResults) {
					messageRecord = new MessageRecord( validationResult.getTag(), validationResult.getErrorMessage(), validationResult.getErrorKind());
					messageView.showMessage( messageRecord);
				}
				messageView.endUpdate();
			}
		});
		
	}


}
