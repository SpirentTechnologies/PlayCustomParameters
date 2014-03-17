package ttworkbench.play.parameters.ipv6.components.messaging.views;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import ttworkbench.play.parameters.ipv6.components.messaging.components.ErrorKindCounter;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.customize.DefaultMessageViewLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IMessageViewLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class DefaultMessageDisplay extends Composite implements IMessageView {
	
	
	
	
	
	private class MessageElement extends Composite {
		private String message;
		private ErrorKind errorKind;
		private CLabel label;
		private boolean flashMessage = false;
		private IMessageContainer messageContainer;
		
		private MessageElement( final IMessageContainer theMessageContainer, final String theMessage, final ErrorKind theErrorKind) {
			super( theMessageContainer.getMessageComposite(), SWT.NONE);
			this.messageContainer = theMessageContainer;
			this.setLayout( new FillLayout());
			
			flashMessage = theMessageContainer.getMessageComposite() == messageHeader;
			
			setDefaultLayoutData();
			
			label = new CLabel( this, SWT.NONE);
		  label.setFont( lookAndBehaviour.getMessageFont( theErrorKind));//new Font( Display.getCurrent(), fontData[0]));
			label.setImage( messageContainer.getMessageImage( theErrorKind));
		  setMessage( theMessage, theErrorKind);
			
			if ( !isFlashMessage())
			  DefaultMessageDisplay.this.errorKindCounter.inc( theErrorKind);
		}
		
		public boolean isFlashMessage() {
			return flashMessage;
		}
		
		private void setDefaultLayoutData() {
			if ( isFlashMessage())
				setLayoutData( new RowData());
			else
				setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));	
		}

		@Override
		public void dispose() {
			if ( !isFlashMessage())
				DefaultMessageDisplay.this.errorKindCounter.dec( errorKind);
			super.dispose();
		}

		public void setMessage( String theMessage, ErrorKind theErrorKind) {
	    // set content
			this.message = theMessage;
	    label.setText( message);

	    
	    // set look
	    this.errorKind = theErrorKind;
	    Color foregroundColor = messageContainer.getMessageForeground( errorKind);
	    Color backgroundColor = messageContainer.getMessageBackground( errorKind);
	    label.setForeground( foregroundColor);
	    label.setBackground( backgroundColor);
	    
	    // set behaviour
	    switch ( errorKind) {
				case error:
				case warning:
					beep();
					break;
			}
			//getParent().layout( true);
		}
		
		private void beep() {
			if ( DefaultMessageDisplay.this.lookAndBehaviour.isBeepEnabled())
			  Toolkit.getDefaultToolkit().beep();
		}

		ErrorKind getErrorKind() {
			return errorKind;
		}
		
		String getMessage() {
			return message;
		}
		
	}
	
	private class MessagePopup extends Composite implements IMessageContainer {
		
		private CLabel label;
		private Shell popupShell;
		private Listener mouseMoveListener = createMouseMoveListener();
		
		public MessagePopup( final Composite theParent, final MessageBlock theMessageBlock) {
			super( theParent, SWT.None);
			this.setLayout( new FillLayout());
		  setLayoutData( new RowData());
		  createPopupShell();

			label = new CLabel( this, SWT.NONE);
		  // show popup when enter the label and...
			label.addListener( SWT.MouseEnter, new Listener() {
				
				@Override
				public void handleEvent(Event theEvent) {
					if ( !popupShell.isVisible()) {
						showPopup( new Point( theEvent.x, theEvent.y));			
					}
				}
			});
		  // ...show popup when click on the label
			label.addListener( SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event theEvent) {
					if ( !popupShell.isVisible()) {
						showPopup( new Point( theEvent.x, theEvent.y));			
					}
				}
			});

			
			updateLabel();			
		}
		
		private void createPopupShell() {
			// TODO: if necessary lay a scrolled composite below the message lines
			popupShell = new Shell( getShell(), SWT.TOOL);
			GridLayout popupLayout = new GridLayout(1, true);
			popupLayout.horizontalSpacing = 0;
			popupLayout.verticalSpacing = 0;
			popupLayout.marginWidth = 0;
			popupLayout.marginHeight = 0;
			popupShell.setLayout( popupLayout);
			
      popupShell.addListener( SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event theArg0) {
					hidePopup();
				}
			});
      
			
		
		}
		
		private void showPopup( final Point theMousePosition) {
			// TODO: handle case if popup is shown out of or intersect with getDisplay.getClientArea()
			popupShell.setLocation( label.toDisplay( new Point( theMousePosition.x -25, theMousePosition.y -25)));
			popupShell.setSize( popupShell.computeSize( SWT.DEFAULT, SWT.DEFAULT));
			for ( Control control : popupShell.getChildren()) {
				control.setEnabled( false);
			}
			popupShell.layout();
			popupShell.open();
		  
			// register handler in display to get mouse moves outside the clientarea too. To close the popup later. 
			getDisplay().addFilter( SWT.MouseMove, mouseMoveListener);
					
		}
		
    private void hidePopup() {
    	getDisplay().removeFilter( SWT.MouseMove, mouseMoveListener);
			popupShell.setVisible( false);
		}
		

		private Listener createMouseMoveListener() {
			return new Listener() {

				@Override
				public void handleEvent(Event theEvent) {
					if ( !popupShell.isVisible())
						return; 

					// hide popup if mouse is out of client area
					if ( theEvent.widget instanceof Control) {
						Point displayCursorPosition = ((Control) theEvent.widget).toDisplay( theEvent.x, theEvent.y);
						Point popupCursorPosition = popupShell.toControl( displayCursorPosition);
						boolean visible = popupShell.getClientArea().contains( popupCursorPosition);
					  if ( !visible) 
              hidePopup();
					}
				}
			};
		}

		@Override
		public void update() {
			updateLabel();
		  updatePopup();
			super.update();
		}
		
		private void updatePopup() {
			popupShell.layout( true);
			popupShell.setSize( popupShell.computeSize( SWT.DEFAULT, SWT.DEFAULT));
			popupShell.update();
			if ( errorKindCounter.getTotalCount() == 0)
			  hidePopup();
		}

		private void updateLabel() {
			// get highest weighted error kind 
			ErrorKind errorKind = DefaultMessageDisplay.this.errorKindCounter.getHighestErrorKind();
			int messageCount = DefaultMessageDisplay.this.errorKindCounter.getCountOfErrorKind( errorKind);

			if ( messageCount == 0) {
				label.setImage( null);
				label.setText( "");
				label.setVisible( false);
				this.setVisible( false);
			} else {
				// get corresponding icon
				Image image = lookAndBehaviour.getMessageImage( errorKind);
				String message;
				switch (errorKind) {
					case error:
						message = String.format( "%d errors.", messageCount);
						// size = 
						break;
					case warning:
						message = String.format( "%d warings.", messageCount);
						break;
					default:
						message = String.format( "%d Informations.", messageCount);
						break;
				}

				label.setFont( lookAndBehaviour.getMessageFont( errorKind));
				label.setImage( image);
				label.setText( message);
				this.setVisible( true);
				label.setVisible( true);
			}
			//setSize( computeSize( SWT.DEFAULT, SWT.DEFAULT));
			//getShell().layout();
		}

	

		@Override
		public Composite getMessageComposite() {
			return popupShell;
		}

		@Override
		public Color getMessageForeground(ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageForeground( theErrorKind);
  	}

		@Override
		public Color getMessageBackground(ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageBackground( theErrorKind);
		}

		@Override
		public Image getMessageImage(ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageImage( theErrorKind);
		}

		@Override
		public Font getMessageFont(ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageFont( theErrorKind);
		}
		
		
	}
	
	
	private class MessageHeader extends Composite implements IMessageContainer {

		MessageHeader( final Composite theParent) {
			super( theParent, SWT.NONE);
			
			RowLayout messageHeaderLayout = new RowLayout();
			messageHeaderLayout.spacing = 0;
			messageHeaderLayout.marginWidth = 1;
			messageHeaderLayout.marginTop = 3;
			messageHeaderLayout.marginBottom = 0;
			setLayout( messageHeaderLayout);			
		}

		@Override
		public Composite getMessageComposite() {
			return this;
		}

		@Override
		public Color getMessageForeground( final ErrorKind theErrorKind) {
			return lookAndBehaviour.getFlashMessageForeground( theErrorKind);
		}

		@Override
		public Color getMessageBackground( final ErrorKind theErrorKind) {
			return lookAndBehaviour.getFlashMessageBackground( theErrorKind);
		}
		
		@Override
		public Image getMessageImage( final ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageImage( theErrorKind);
		}

		@Override
		public Font getMessageFont(ErrorKind theErrorKind) {
			return lookAndBehaviour.getMessageFont( theErrorKind);
		}
		
	}
		
	

	
	
	
	private class MessageBlock {

		private Map<String, MessageElement> taggedMessageElements = new HashMap<String,MessageElement>();
		private Set<String> agedMessageTags = new HashSet<String>();
		private List<MessageElement> untaggedMessageElements = new ArrayList<MessageElement>();
		
		private IMessageContainer messageContainer;
		
		private MessageBlock( IMessageContainer theMessageContainer) {
			this.messageContainer = theMessageContainer;
		}
		
		public synchronized void putTaggedMessage( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
			MessageElement oldMessageLine = taggedMessageElements.remove( theTag);
			
			// release old message control first
			if ( oldMessageLine != null)
				oldMessageLine.dispose();
			
			// add a success message only as an answer to a message of a prior cycle. 
			if ( !agedMessageTags.contains( theTag) &&
					 theErrorKind.equals( ErrorKind.success))
				return;
			
			taggedMessageElements.put( theTag, new MessageElement( messageContainer, theMessage, theErrorKind));
		}

		public synchronized void clearTaggedSuccessMessage( final String theTag) {
			MessageElement messageElement = taggedMessageElements.get( theTag);
			if ( messageElement != null &&
					messageElement.getErrorKind().equals( ErrorKind.success)) {
				taggedMessageElements.remove( theTag);
				messageElement.dispose();
			}
		}
		
		public synchronized void clearTaggedMessage( final String theTag) {
			MessageElement messageElement = taggedMessageElements.remove( theTag);
			if ( messageElement != null) {
				messageElement.dispose();
			}
		}
		
		public void addUntaggedMessage( final String theMessage, final ErrorKind theErrorKind) {
			if ( theErrorKind.equals( ErrorKind.success))
				return;
			
			untaggedMessageElements.add( new MessageElement( messageContainer, theMessage, theErrorKind));
		}

		
		private void clearAllTaggedMessages() {
			for (MessageElement messageElement : taggedMessageElements.values()) {
				messageElement.dispose();
			}
			taggedMessageElements.clear();
		}
		
		private void clearAllUntaggedMessages() {
			for (MessageElement messageElement : untaggedMessageElements) {
				messageElement.dispose();
			}
			untaggedMessageElements.clear();
		}
		
		private void retainTags() {
			agedMessageTags.addAll( taggedMessageElements.keySet());
		}
		
		private void clearRetainedTags() {
			agedMessageTags.clear();
		}
			
		
		public synchronized void beginUpdateCycle() {
			retainTags();
			clearAllTaggedMessages();
			clearAllUntaggedMessages();
		}
			
		public void endUpdateCycle() {
			clearRetainedTags();
		}

		public List<String> getMessages( EnumSet<ErrorKind> theMessageKinds) {
			List<String> result = new ArrayList<String>();
			Collection<MessageElement> messageElements = taggedMessageElements.values();
			for (MessageElement messageElement : messageElements) {
				if ( theMessageKinds.contains( messageElement.getErrorKind()))
				  result.add( messageElement.getMessage());
			}
			return result;
		}		
		
	}
	
	
	
	private final int MESSAGE_CONTAINER_HEIGHT = 24;
	
	private final ErrorKindCounter errorKindCounter = new ErrorKindCounter();
	
	private IMessageViewLookAndBehaviour lookAndBehaviour = new DefaultMessageViewLookAndBehaviour();
	
	private final Map<Object, MessageBlock> messages = new HashMap<Object, MessageBlock>();	 
	private Object currentSenderId;
	
	private static final ScheduledExecutorService messageWorker = Executors.newSingleThreadScheduledExecutor();
	
	
	private Map<String, ScheduledFuture> flashMessageFutures = new HashMap<String, ScheduledFuture>();
	
	private Lock updateLock = new ReentrantLock();
	
	private MessagePopup messagePopup;
	private MessageHeader messageHeader;
	private Composite wrappedComposite;
	
	public DefaultMessageDisplay( final Composite theParent, final int theStyle) {
		super( theParent, theStyle);
		createPanel( theParent);
		initMessageCounting();
		// precreate default block
		MessageBlock theDefaultBlock = new MessageBlock( messageHeader);
		messages.put( getThisId(), theDefaultBlock);
		// insert popup place holder 
		messagePopup = new MessagePopup( messageHeader, theDefaultBlock);
	}

	private void initMessageCounting() {
		errorKindCounter.setListener( new ErrorKindCounter.CountListener() {

			@Override
			public void handleIncrementEvent(ErrorKind theErrorKind, int theCounterReading) {
				ErrorKind highestErrorKind = errorKindCounter.getHighestErrorKind();
				Color frameColor = lookAndBehaviour.getMessageBackground( highestErrorKind);
				DefaultMessageDisplay.this.setBackground( frameColor);
				DefaultMessageDisplay.this.messagePopup.update();
			}

			@Override
			public void handleDecrementEvent(ErrorKind theErrorKind, int theCounterReading) {
				ErrorKind highestErrorKind = errorKindCounter.getHighestErrorKind();
				int messageCount = errorKindCounter.getCountOfErrorKind( highestErrorKind);
				Color messageColor = lookAndBehaviour.getMessageBackground( highestErrorKind);
				Color clearColor	=	getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
				Color frameColor = messageCount > 0 ? messageColor : clearColor;
				DefaultMessageDisplay.this.setBackground( frameColor);
				DefaultMessageDisplay.this.messagePopup.update();
			}
		});
	}

	private void createPanel(Composite theParent) {
		/*
		 *  create panel frame, that wrap messages and editor
		 */
		{
			GridLayout layout = new GridLayout(1, false);
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
			messageHeader = new MessageHeader( this);
			// TODO check layout data. Is compatible? to Flowlayout or Filllayout 
			GridData messageContainerGridData = new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0);
			messageContainerGridData.minimumHeight = MESSAGE_CONTAINER_HEIGHT;
			messageHeader.setLayoutData( messageContainerGridData);
		}

	
		this.setSize( this.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		this.layout();
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
	    addUntaggedMessage( theMessageRecord.message, theMessageRecord.errorKind);
		else
		  putTaggedMessage( theMessageRecord.tag, theMessageRecord.message, theMessageRecord.errorKind);	
	}
	
	@Override
	public void showMessages(Collection<MessageRecord> theMessageRecords) {
		for (MessageRecord messageRecord : theMessageRecords) {
			showMessage( messageRecord);
		}
	}
	
	private void putTaggedMessage( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
		final Object id = ( currentSenderId != null) ? currentSenderId : getThisId();

		if ( !lookAndBehaviour.isFlashingOfTaggedSuccessMessagesEnabled() && 
				 theErrorKind.equals( ErrorKind.success))
			return; 
		
		final MessageBlock messageBlock = messages.get( id);
		messageBlock.putTaggedMessage( theTag, theMessage, theErrorKind);
		
		if ( lookAndBehaviour.isFlashingOfTaggedSuccessMessagesEnabled() && 
				 theErrorKind.equals( ErrorKind.success)) {
			Runnable removeMessageTask = new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							messageBlock.clearTaggedSuccessMessage( theTag);
							doOnChange();
						}
					});		
				}
			};
			messageWorker.schedule( removeMessageTask, lookAndBehaviour.getFlashDurationOfSuccessMessages(), TimeUnit.SECONDS);
		}
		
    tryOnChange();
	}

	private void addUntaggedMessage( final String theMessage, final ErrorKind theErrorKind) {
		final Object id = ( currentSenderId != null) ? currentSenderId : getThisId();

		MessageBlock messageBlock = messages.get( id);
		messageBlock.addUntaggedMessage( theMessage, theErrorKind);
	
    tryOnChange();
	} 
	
	@Override
	public void flashMessage( final MessageRecord theMessageRecord) {
		final String id = getThisId();
		final MessageBlock messageBlock = messages.get( id);
		
		final String tag = theMessageRecord.tag != null && !theMessageRecord.tag.isEmpty() ? theMessageRecord.tag : String.valueOf( System.currentTimeMillis());

		if ( flashMessageFutures.containsKey( tag))
			flashMessageFutures.get( tag).cancel( true);
		messageBlock.clearTaggedMessage( tag);
		messageBlock.putTaggedMessage( tag, theMessageRecord.message, theMessageRecord.errorKind); 	

		Runnable flashWarningTask = new Runnable() {
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBlock.clearTaggedMessage( tag);
						doOnChange();
					}
				});		
			}
		};

		ScheduledFuture flashMessageFuture = messageWorker.schedule( flashWarningTask, lookAndBehaviour.getFlashDuration(), TimeUnit.SECONDS);
		flashMessageFutures.put( tag, flashMessageFuture);
		
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
			messages.put( currentSenderId, new MessageBlock( messagePopup));
		
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
	public void wrapControl(Composite theWrappedComposite) {
		if ( wrappedComposite != null)
			wrappedComposite.dispose();
		this.wrappedComposite = theWrappedComposite;
		wrappedComposite.setParent( this);
		
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0);
		wrappedComposite.setLayoutData( gridData);
		
    this.setSize( this.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		this.layout();
	}
	
}
