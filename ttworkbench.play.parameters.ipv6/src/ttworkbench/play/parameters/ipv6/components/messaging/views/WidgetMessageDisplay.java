package ttworkbench.play.parameters.ipv6.components.messaging.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import ttworkbench.play.parameters.ipv6.components.messaging.components.MessageBlock;
import ttworkbench.play.parameters.ipv6.components.messaging.components.MessageBlock.RegisterDirective;
import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.IMessageInformation;
import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.IRegistryListener;
import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.MessageRegistry;
import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.RegistryEvent;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageHydra;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessageHeader;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessagePopup;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.DefaultMessageViewLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IMessageViewLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class WidgetMessageDisplay extends Composite implements IMessageView<IWidget> {
	
	
	
	
	
	private final int MESSAGE_CONTAINER_HEIGHT = 24;
	
	IMessageViewLookAndBehaviour lookAndBehaviour = new DefaultMessageViewLookAndBehaviour();
	
	private final Map<Object, MessageBlock> messages = new HashMap<Object, MessageBlock>();	 
	private Object currentSenderId;
	private final MessageRegistry messageRegistry = new MessageRegistry();
	
	private static final ScheduledExecutorService messageWorker = Executors.newSingleThreadScheduledExecutor();
	
	private Map<String, ScheduledFuture<?>> flashMessageFutures = new HashMap<String, ScheduledFuture<?>>();
	
	private Lock updateLock = new ReentrantLock();
	
	private MessagePopup messageOverviewPopup;
	private MessagePopup messagePopup;
	private MessageHeader messageHeader;
	private Composite wrappedComposite;
	private ToolBar toolBar;
	
	public WidgetMessageDisplay( final Composite theParent, final int theStyle) {
		super( theParent, theStyle);
		createPanel( theParent);
		initMessageRegistry();
		// precreate default block
		MessageBlock theDefaultBlock = new MessageBlock( messageHeader, messageRegistry);
		messages.put( getThisId(), theDefaultBlock);
		// insert popup place holder 
		messagePopup = new MessagePopup( this, messageHeader);
		messageOverviewPopup = new MessagePopup( this, messageHeader);
	}

	private void initMessageRegistry() {
		messageRegistry.addListener( new IRegistryListener() {
			
			@Override
			public void handleRegisterEvent( RegistryEvent theEvent) {
				WidgetMessageDisplay.this.messagePopup.update();
				//theEvent.messageLabel.navigateToCauser()
			}
			
			@Override
			public void handleDeregisterEvent(RegistryEvent theEvent) {
				if ( !isDisposed()) {
					ErrorKind highestErrorKind = theEvent.registry.getHighestErrorKind();
					int messageCount = theEvent.registry.getCountOfMessagesWithErrorKind( EnumSet.of( highestErrorKind));
					Color messageColor = lookAndBehaviour.getMessageLookAndBehaviour().getMessageBackground( highestErrorKind);
					Color clearColor	=	getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
					Color frameColor = messageCount > 0 ? messageColor : clearColor;
					WidgetMessageDisplay.this.setBackground( frameColor);
					WidgetMessageDisplay.this.messagePopup.update();	
					messageHeader.layout( true);
				}
			}
			
			@Override
			public void handleHydraPublishedEvent( IMessageHydra theMessageHydra) {
				theMessageHydra.newLabel( messagePopup);
				WidgetMessageDisplay.this.messagePopup.update();
			}
			
		});
	}

	private void createPanel(Composite theParent) {
		/*
		 *  create panel frame, that wrap messages and editor
		 */
		{
			GridLayout layout = new GridLayout(2, false);
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.marginHeight = 1;
			layout.marginWidth = 1;
			setLayout( layout);
		}

		/*
		 *  create message container
		 */
		{
			messageHeader = new MessageHeader( this, this);
			messageHeader.setBackground( lookAndBehaviour.getMessageLookAndBehaviour().getMessageBackground( ErrorKind.error));
			GridData messageContainerGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0);
			messageContainerGridData.minimumHeight = MESSAGE_CONTAINER_HEIGHT;
			messageHeader.setLayoutData( messageContainerGridData);
		}

		/*
		 * create toolbar with button to de/activate advanced edit mode 
		 */
		{
			toolBar = new ToolBar( this, SWT.BORDER);
			GridData toolBarGridData = new GridData(SWT.RIGHT, SWT.FILL, false, false, 0, 0);	 
			toolBar.setLayoutData( toolBarGridData);
		}

	
		this.setSize( this.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		this.layout();
	}
	
	public ToolBar getToolBar() {
		return toolBar;
	}

	@Override
	public void setLookAndBehaviour(IMessageViewLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}
	
	@Override
	public IMessageViewLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}
	
	@Override
	public void showMessage( final MessageRecord theMessageRecord) {
		if ( theMessageRecord.tag == null)
	    addUntaggedMessage( theMessageRecord);
		else
		  putTaggedMessage( theMessageRecord);	
	}
	
	@Override
	public void showMessages(Collection<MessageRecord> theMessageRecords) {
		for (MessageRecord messageRecord : theMessageRecords) {
			showMessage( messageRecord);
		}
	}
	
	@Override
	public void clearMessagesByTag( final String theTag) {
		final Object id = ( currentSenderId != null) ? currentSenderId : getThisId();
		final MessageBlock messageBlock = messages.get( id);
		messageBlock.clearTaggedMessage( theTag);
	}
	
	private void putTaggedMessage( final MessageRecord theMessageRecord) {
		final Object id = ( currentSenderId != null) ? currentSenderId : getThisId();

		if ( !lookAndBehaviour.isFlashingOfTaggedSuccessMessagesEnabled() && 
				theMessageRecord.errorKind.equals( ErrorKind.success))
			return; 
		
		final MessageBlock messageBlock = messages.get( id);
		messageBlock.putTaggedMessage( theMessageRecord, RegisterDirective.REGISTER);
		
		if ( lookAndBehaviour.isFlashingOfTaggedSuccessMessagesEnabled() && 
				theMessageRecord.errorKind.equals( ErrorKind.success)) {
			Runnable removeMessageTask = new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							messageBlock.clearTaggedSuccessMessage( theMessageRecord.tag);
							doOnChange();
						}
					});		
				}
			};
			messageWorker.schedule( removeMessageTask, lookAndBehaviour.getFlashDurationOfSuccessMessages(), TimeUnit.SECONDS);
		}
		
    tryOnChange();
	}

	private void addUntaggedMessage( MessageRecord theMessageRecord) {
		final Object id = ( currentSenderId != null) ? currentSenderId : getThisId();

		MessageBlock messageBlock = messages.get( id);
		messageBlock.addUntaggedMessage( theMessageRecord, RegisterDirective.REGISTER);
	
    tryOnChange();
	} 
	
	@Override
	public void flashMessage( final MessageRecord theMessageRecord) {
		final String id = getThisId();
		final MessageBlock messageBlock = messages.get( id);
		
		final MessageRecord msg = theMessageRecord.hasTag() ? theMessageRecord : new MessageRecord( String.valueOf( System.currentTimeMillis()), theMessageRecord.message, theMessageRecord.errorKind);
		
		if ( flashMessageFutures.containsKey( msg.tag))
			flashMessageFutures.get( msg.tag).cancel( true);
		messageBlock.clearTaggedMessage( msg.tag);
		messageBlock.putTaggedMessage( msg, RegisterDirective.NO_REGISTRATION); 	

		Runnable flashWarningTask = new Runnable() {
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBlock.clearTaggedMessage( msg.tag);
						doOnChange();
					}
				});		
			}
		};

		ScheduledFuture<?> flashMessageFuture = messageWorker.schedule( flashWarningTask, lookAndBehaviour.getFlashDuration(), TimeUnit.SECONDS);
		flashMessageFutures.put( msg.tag, flashMessageFuture);
		
		doOnChange();
	}
	
	@Override
	public void flashMessages(Collection<MessageRecord> theMessageRecords) {
		for (MessageRecord messageRecord : theMessageRecords) {
			flashMessage( messageRecord);
		}
	}
	
	@Override
	public void beginUpdateForSender( final Object theSenderId) {
		updateLock.lock();
		
		this.currentSenderId = theSenderId;
		
		// create a message block if no one exist yet
		if ( !messages.containsKey( currentSenderId))
			messages.put( currentSenderId, new MessageBlock( messagePopup, messageRegistry));
		
		MessageBlock messageBlock = messages.get( currentSenderId);
		messageBlock.beginUpdateCycle();
	}

	@Override
  public void endUpdate() {
		try {
			MessageBlock messageBlock = messages.get( currentSenderId);
			messageBlock.endUpdateCycle();
			// TODO remove empty message blocks ? 
			this.currentSenderId = null;

			doOnChange();
		} finally {
			updateLock.unlock();
		}
	}
	
	
	@Override
	public List<String> getMessages( EnumSet<ErrorKind> theMessageKinds) {
		List<String> result = new ArrayList<String>();
		Collection<MessageBlock> messageBlocks = messages.values();
		for (MessageBlock messageBlock : messageBlocks) {
			result.addAll( messageBlock.getMessages( theMessageKinds));
		}
		return result;
	}
	
	
	private void doOnChange() {
		lookAndBehaviour.doOnChange();	
	}
	
	private void tryOnChange() {
		if ( this.currentSenderId == null)
			doOnChange();
		else
			try {
				updateLock.tryLock();
				doOnChange();
			} finally {
				updateLock.unlock();
			}
	}
	
	private String getThisId() {
		return this.getClass().getName() + "@" + this.hashCode(); 
	}


	@Override
	public IMessageInformation getMessageInformation() {
		return messageRegistry;
	}

	@Override
	public void setClientComponent( IWidget theClientComponent) {
		// TODO
	}

	@Override
	public void setSuperiorView(IMessageView<?> theMessageView) {
		// TODO Auto-generated method stub
		
	}

	
}
