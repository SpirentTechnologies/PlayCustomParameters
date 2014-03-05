package ttworkbench.play.parameters.ipv6.editors.components;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessagePanel extends Composite {
	
	
	private class MessageLine extends Composite {

		private String message;
		private ErrorKind errorKind;
		private Label label;
		
		
		private MessageLine( final String theMessage, final ErrorKind theErrorKind) {
			super( MessagePanel.this, SWT.NONE);
			this.setLayout( new FillLayout());
			setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
			
			label = new Label( this, SWT.NONE);
			FontData[] fontData = label.getFont().getFontData();
			fontData[0].setHeight( fontData[0].getHeight() -2);
			label.setFont( new Font( Display.getCurrent(), fontData[0]));
			
			setMessage( theMessage, theErrorKind);
		}

		public void setMessage( String theMessage, ErrorKind theErrorKind) {
	    // set content
			this.message = theMessage;
	    label.setText( message);
	    
	    // set look
	    Color foregroundColor;
	    Color backgroundColor;
	    Display display = Display.getCurrent();
	    Color colorRed = display.getSystemColor(SWT.COLOR_RED);
	    Color colorYellow = display.getSystemColor(SWT.COLOR_YELLOW);
	    Color colorGreen = display.getSystemColor(SWT.COLOR_GREEN);
	    Color colorWhite = display.getSystemColor(SWT.COLOR_WHITE);
	    Color colorBlack = display.getSystemColor(SWT.COLOR_BLACK);
	    Color colorBtnFace = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	     
	    this.errorKind = theErrorKind;
	    switch ( errorKind) {
				case error:
					foregroundColor = colorWhite;
					backgroundColor = colorRed;
					break;
				case warning:
					foregroundColor = colorRed;
					backgroundColor = colorBtnFace;
					break;
	      case info:
	      	foregroundColor = colorBlack;
					backgroundColor = colorYellow;	
					break;
				default:
	      	foregroundColor = colorBlack;
					backgroundColor = colorGreen;					
					break;
			}
	    label.setForeground( foregroundColor);
	    label.setBackground( backgroundColor);
		}
		
		ErrorKind getErrorKind() {
			return errorKind;
		}
		
	}
	
	
	
	private class MessageBlock {

		private Map<String, MessageLine> taggedMessageLines = new HashMap<String,MessageLine>();
		private Set<String> agedMessageTags = new HashSet<String>();
		private List<MessageLine> untaggedMessageLines = new ArrayList<MessageLine>();
		
		private MessageBlock() {
		}
		
		public synchronized void putTaggedMessage( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
			MessageLine oldMessageLine = taggedMessageLines.remove( theTag);
			
			// release old message control first
			if ( oldMessageLine != null)
				oldMessageLine.dispose();
			
			// add a success message only as an answer to a message of a prior cycle. 
			if ( !agedMessageTags.contains( theTag) &&
					 theErrorKind.equals( ErrorKind.success))
				return;
			
			taggedMessageLines.put( theTag, new MessageLine( theMessage, theErrorKind));
		}

		public synchronized void clearTaggedSuccessMessage( final String theTag) {
			MessageLine messageLine = taggedMessageLines.get( theTag);
			if ( messageLine != null &&
					 messageLine.getErrorKind().equals( ErrorKind.success)) {
				taggedMessageLines.remove( theTag);
				messageLine.dispose();
			}
		}
		
		public synchronized void clearTaggedMessage( final String theTag) {
			MessageLine messageLine = taggedMessageLines.remove( theTag);
			if ( messageLine != null) {
				messageLine.dispose();
			}
		}
		
		public void addUntaggedMessage( final String theMessage, final ErrorKind theErrorKind) {
			if ( theErrorKind.equals( ErrorKind.success))
				return;
			
			untaggedMessageLines.add( new MessageLine( theMessage, theErrorKind));
		}

		
		private void clearAllTaggedMessages() {
			for (MessageLine messageLine : taggedMessageLines.values()) {
				messageLine.dispose();
			}
			taggedMessageLines.clear();
		}
		
		private void clearAllUntaggedMessages() {
			for (MessageLine messageLine : untaggedMessageLines) {
				messageLine.dispose();
			}
			untaggedMessageLines.clear();
		}
		
		private void retainTags() {
			agedMessageTags.addAll( taggedMessageLines.keySet());
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
		
	}
	
	
	
	
	

	private final Map<Object, MessageBlock> messages = new HashMap<Object, MessageBlock>();	 
	private Object currentSenderId;
	
	private static final ScheduledExecutorService messageWorker = Executors.newSingleThreadScheduledExecutor();
	private boolean flashTaggedSuccessMessages = true;
	private int flashDurationInSeconds = 2;
	
	private Map<String, ScheduledFuture> flashMessageFutures = new HashMap<String, ScheduledFuture>();
	
	private Listener changedListener = null;
			
	public MessagePanel( final Composite theParent, final int theStyle) {
		super( theParent, theStyle);
		GridLayout layout = new GridLayout(1, true);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayout( layout);
	}
	
	public void setChangedListener(Listener theChangedListener) {
		changedListener = theChangedListener;
	}
	
	public void flashTaggedSuccessMessages() {
		flashTaggedSuccessMessages = true;
	}
	
	public void hideAllSuccessMessages() {
		flashTaggedSuccessMessages = false;
	}
	
	public void setFlashDurationInSeconds(int theFlashDurationInSeconds) {
		this.flashDurationInSeconds = theFlashDurationInSeconds;
	}
	

	public void putTaggedMessage( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
		if ( currentSenderId == null)
			return;
		
		if ( !flashTaggedSuccessMessages && 
				 theErrorKind.equals( ErrorKind.success))
			return; 
		
		final MessageBlock messageBlock = messages.get( currentSenderId);
		messageBlock.putTaggedMessage( theTag, theMessage, theErrorKind);
		
		if ( flashTaggedSuccessMessages && 
				 theErrorKind.equals( ErrorKind.success)) {
			Runnable removeMessageTask = new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							messageBlock.clearTaggedSuccessMessage( theTag);
							if ( changedListener != null)
								synchronized (changedListener) {
									changedListener.handleEvent( new Event());
								}
						}
					});		
				}
			};
			messageWorker.schedule( removeMessageTask, flashDurationInSeconds, TimeUnit.SECONDS);
		}
	}

	public void addUntaggedMessage( final String theMessage, final ErrorKind theErrorKind) {
		if ( currentSenderId == null)
			return;
		
		MessageBlock messageBlock = messages.get( currentSenderId);
		messageBlock.addUntaggedMessage( theMessage, theErrorKind);
	} 
	
	public void flashMessage( final String theTag, final String theWarning, final ErrorKind theErrorKind) {
		final String id = this.getClass().getName() + "@" + this.hashCode(); 
		if ( !messages.containsKey( id))
			messages.put( id, new MessageBlock());
		final MessageBlock messageBlock = messages.get( id);
		
		final String tag = theTag != null && !theTag.isEmpty() ? theTag : String.valueOf( System.currentTimeMillis());

		if ( flashMessageFutures.containsKey( tag))
			flashMessageFutures.get( tag).cancel( true);
		messageBlock.clearTaggedMessage( tag);
		messageBlock.putTaggedMessage( tag, theWarning, theErrorKind); 	
		
		Runnable flashWarningTask = new Runnable() {
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBlock.clearTaggedMessage( tag);
						if ( changedListener != null)
							synchronized (changedListener) {
								changedListener.handleEvent( new Event());
							}
					}
				});		
			}
		};

		ScheduledFuture flashMessageFuture = messageWorker.schedule( flashWarningTask, Math.round( flashDurationInSeconds * 1.5), TimeUnit.SECONDS);
		flashMessageFutures.put( tag, flashMessageFuture);
		
		if ( changedListener != null)
			synchronized (changedListener) {
				changedListener.handleEvent( new Event());
			}
	}
	
	public void beginUpdateForSender( final Object theSenderId) {
		this.currentSenderId = theSenderId;
		
		// create a message block if no one exist yet
		if ( !messages.containsKey( currentSenderId))
			messages.put( currentSenderId, new MessageBlock());
		
		MessageBlock messageBlock = messages.get( currentSenderId);
		messageBlock.beginUpdateCycle();
	}

  public void endUpdate() {
  	MessageBlock messageBlock = messages.get( currentSenderId);
  	messageBlock.endUpdateCycle();
  	// TODO remove empty message blocks ? 
		this.currentSenderId = null;
		
		if ( changedListener != null)
			synchronized (changedListener) {
				changedListener.handleEvent( new Event());
			}
	}

	public void flashMessage(String theWarning, com.testingtech.ttworkbench.metamodel.muttcn.validator.ErrorKind theInfo) {
		// TODO Auto-generated method stub
		
	}
	
	

}
