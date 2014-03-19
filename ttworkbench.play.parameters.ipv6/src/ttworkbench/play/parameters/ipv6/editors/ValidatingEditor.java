package ttworkbench.play.parameters.ipv6.editors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.components.messaging.views.EditorMessageDisplay;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.components.messaging.views.MessagePanel;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.ILookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IActionHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationAction;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public abstract class ValidatingEditor<T> extends AbstractEditor<T> implements IMessageHandler, IActionHandler {


	private EditorMessageDisplay messageDisplay = null;
	private static final ScheduledExecutorService validationWorker = Executors.newSingleThreadScheduledExecutor();
	private static final ScheduledExecutorService validationMessageWorker = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> validationTaskFuture;
	private ScheduledFuture<?> validationMessageTaskFuture;	
	private IValidatingEditorLookAndBehaviour lookAndBehaviour;
	
	public ValidatingEditor( String theTitle, String theDescription) {
		super( theTitle, theDescription);
		setLookAndBehaviour( getDefaultLookAndBehaviour());
	}
	
	public IValidatingEditorLookAndBehaviour getLookAndBehaviour() {
		return this.lookAndBehaviour;
	}
		
	protected void setLookAndBehaviour( IValidatingEditorLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
		super.setLookAndBehaviour( theLookAndBehaviour.getEditorLookAndBehaviour());
	}
	
	public abstract IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour();
	
	
	public IMessageView<Composite> getMessageView() {
		return messageDisplay;
	}
	

  
	
	/**
	 * Validates the current parameter value in the main thread.
	 * @return
	 */
	protected List<ValidationResult> validate() {
		IConfiguration configuration = getConfiguration();
		IParameter<?> parameter = getParameter();
		List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
		if ( configuration != null) {			
			Set<IParameterValidator> validators = configuration.getValidators( parameter);
			for (IParameterValidator validator : validators) {
				if ( !Thread.currentThread().isInterrupted())
				  validationResults.addAll( validator.validate( parameter));
			}
		}
		return validationResults;
	}


  /**
   * Validates the current parameter value delayed in another thread.
   */
	protected void validateDelayed() {
		if ( validationTaskFuture != null) 
			validationTaskFuture.cancel( true);
		if ( validationMessageTaskFuture != null) 
			validationMessageTaskFuture.cancel( true);
		
		Runnable validationMessageTask = new Runnable() {
			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getMessageView().showMessage( new MessageRecord( "run_validator", "Validation process in progress.", ErrorKind.info));
					}
				});
			}
		};
		final long showValidationMessageDelay = getLookAndBehaviour().getShowValidationInProgressMessageDelay();
		validationMessageTaskFuture = validationMessageWorker.schedule( validationMessageTask, showValidationMessageDelay, TimeUnit.MILLISECONDS);	
		
		Runnable validationTask = new Runnable() {
			@Override
			public void run() {
				
				validate();	
				
				validationMessageTaskFuture.cancel( false);
				
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getMessageView().showMessage( new MessageRecord( "run_validator", "Validation finished.", ErrorKind.success));
					}
				});
			}
		};
		final long startValidationDelay = getLookAndBehaviour().getStartValidationDelay();		
		validationTaskFuture = validationWorker.schedule( validationTask, startValidationDelay, TimeUnit.MILLISECONDS);
	}



	
	
	protected abstract void createEditRow(Composite theContainer);
	
	private void createMessageRow(Composite theParent) {
		// TODO Auto-generated method stub
		messageDisplay = new EditorMessageDisplay( theParent, SWT.NONE);
		messageDisplay.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0));
		messageDisplay.setLookAndBehaviour( getLookAndBehaviour().getMessagePanelLookAndBehaviour());
		messageDisplay.getLookAndBehaviour().addChangedListener( new Listener() {
			@Override
			public void handleEvent(Event theArg0) {
				updateControl();
			}
		});
	}
	
	
	@Override
	public final Composite createControl(Composite theParent) {
	
	  Composite container = new Composite( theParent, SWT.None);
	  GridLayout containerLayout = new GridLayout();
	  containerLayout.marginHeight = 0;
	  containerLayout.marginWidth = 0;
	  container.setLayout( containerLayout);
	  // TODO check layout data. Is compatible? to Flowlayout or Filllayout 
		container.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
		
		createMessageRow( container);

		Composite editRowContainer = new Composite( container, SWT.None);
		editRowContainer.setLayout( getLookAndBehaviour().getEditorLookAndBehaviour().getLayout());
		createEditRow( editRowContainer);
		getMessageView().setClientComponent( editRowContainer);
		
		container.setSize( container.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		container.layout();
		
		return container;
	}
	

	@Override
	public synchronized void report( final IParameterValidator theValidator, final List<ValidationResult> theValidationResults,
			final IParameter theParameter) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {				
				IMessageView messageView = getMessageView();
				if ( messageView != null) {
					String senderId = String.format( "%s@%s", theValidator.getClass().getName(), theValidator.hashCode());
					messageView.beginUpdateForSender( senderId);
					for (ValidationResult validationResult : theValidationResults) {
						if ( validationResult.isTagged()) {
							messageView.showMessage( new MessageRecord( validationResult.getTag(), validationResult.getErrorMessage(), validationResult.getErrorKind()));
						} else {
							messageView.showMessage( new MessageRecord( validationResult.getErrorMessage(), validationResult.getErrorKind()));					
						}
					}
					messageView.endUpdate();
				}
			}
		});
	}
	
	@Override
	public void trigger(IParameterValidator theValidator, List<ValidationAction> theValidationActions,
			IParameter theParameter) {
		// TODO Auto-generated method stub
	}
	

}
