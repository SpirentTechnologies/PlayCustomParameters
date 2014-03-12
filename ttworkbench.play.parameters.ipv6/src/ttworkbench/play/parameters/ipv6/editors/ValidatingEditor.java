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

import ttworkbench.play.parameters.ipv6.components.IMessageView;
import ttworkbench.play.parameters.ipv6.components.MessageDisplay;
import ttworkbench.play.parameters.ipv6.components.MessagePanel;
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


	private MessageDisplay messageDisplay = null;
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
	
	
	public IMessageView getMessageView() {
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
   * TODO remove parameter theDelayInSeconds -> put into behavior class
   * @param theDelayInSeconds 
   */
	protected void validateDelayed( final int theDelayInSeconds) {
		if ( validationTaskFuture != null) 
			validationTaskFuture.cancel( true);
		if ( validationMessageTaskFuture != null) 
			validationMessageTaskFuture.cancel( true);
		

		Runnable validationMessageTask = new Runnable() {
			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getMessageView().putTaggedMessage( "run_validator", "Validation process in progress.", ErrorKind.info);
					}
				});
			}
		};
		validationMessageTaskFuture = validationMessageWorker.schedule( validationMessageTask, theDelayInSeconds +1, TimeUnit.SECONDS);	
		
		Runnable validationTask = new Runnable() {
			@Override
			public void run() {
				
				validate();	
				
				validationMessageTaskFuture.cancel( false);
				
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						getMessageView().putTaggedMessage( "run_validator", "Validation finished.", ErrorKind.success);
					}
				});
			}
		};
		validationTaskFuture = validationWorker.schedule( validationTask, theDelayInSeconds, TimeUnit.SECONDS);
	}



	
	
	protected abstract void createEditRow(Composite theContainer);
	
	private void createMessageRow(Composite theParent) {
		// TODO Auto-generated method stub
		messageDisplay = new MessageDisplay( theParent, SWT.NONE);
		messageDisplay.setLookAndBehaviour( getLookAndBehaviour().getMessaagePanelLookAndBehaviour());
		messageDisplay.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0));
		messageDisplay.getLookAndBehaviour().setChangedListener( new Listener() {
			@Override
			public void handleEvent(Event theArg0) {
				updateControl();
			}
		});
	}
	
	
	@Override
	public final Composite createControl(Composite theParent) {
	
	  Composite container = new Composite( theParent, SWT.None);
		container.setLayout( new GridLayout( 1, true));
	  // TODO check layout data. Is compatible? to Flowlayout or Filllayout 
		container.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
		
		createMessageRow( container);

		Composite editRowContainer = new Composite( container, SWT.None);
		editRowContainer.setLayout( getLookAndBehaviour().getEditorLookAndBehaviour().getLayout());
		editRowContainer.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
		createEditRow( editRowContainer);
		getMessageView().wrapControl( editRowContainer);
		
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
	
	@Override
	public void trigger(IParameterValidator theValidator, List<ValidationAction> theValidationActions,
			IParameter theParameter) {
		// TODO Auto-generated method stub
	}
	

}
