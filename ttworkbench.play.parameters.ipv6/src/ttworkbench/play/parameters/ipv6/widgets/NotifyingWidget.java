package ttworkbench.play.parameters.ipv6.widgets;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.components.IMessagePanel;
import ttworkbench.play.parameters.ipv6.components.MessagePanel;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public abstract class NotifyingWidget extends AbstractWidget implements IMessageHandler {

	public NotifyingWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	
	protected abstract IMessagePanel getMessagePanel();
	
	
	

	@Override
	public void report( final IParameterValidator theValidator, final List<ValidationResult> theValidationResults,
			IParameter theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IMessagePanel messagePanel = getMessagePanel();
				String senderId = String.format( "%s@%s", theValidator.getClass().getName(), theValidator.hashCode());
				messagePanel.beginUpdateForSender( senderId);
				for (ValidationResult validationResult : theValidationResults) {
					if ( validationResult.isTagged()) {
						messagePanel.putTaggedMessage( validationResult.getTag(), validationResult.getErrorMessage(), validationResult.getErrorKind());
					} else {
					  messagePanel.addUntaggedMessage( validationResult.getErrorMessage(), validationResult.getErrorKind());					
					}
				}
				messagePanel.endUpdate();
			}
		});
		
	}


}
