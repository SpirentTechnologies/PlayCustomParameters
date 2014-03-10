package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class DefaultMessagePanelLookAndBehaviour implements IMessagePanelLookAndBehaviour {
	
	private static final Display CURRENT_DISPLAY = Display.getCurrent();
	private static final Color COLOR_RED = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_RED);
	private static final Color COLOR_YELLOW = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_YELLOW);
	private static final Color COLOR_ORANGE = new Color( CURRENT_DISPLAY, 255, 165, 0);
	private static final Color COLOR_GREEN = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_GREEN);
	private static final Color COLOR_WHITE = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_WHITE);
	private static final Color COLOR_BLACK = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_BLACK);
	private static final Color COLOR_BTN_FACE = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
  

	private int flashDurationInSeconds = 4;
	private int flashDurationOfSuccessMessagesInSeconds = 2;
	
	private Set<Listener> changedListeners = new HashSet<Listener>();
	
	private boolean flashTaggedSuccessMessages = true;
	
	private boolean beep = false;		
	
	@Override
	public void enableBeep() {
		this.beep = true;
	}
	
	@Override
	public boolean isBeepEnabled() {
		return beep;
	}
	
	@Override
	public void disableBeep() {
		this.beep = false;	
	}
	

	@Override
	public void setFlashDuration( int theFlashDurationInSeconds) {
	  this.flashDurationInSeconds = theFlashDurationInSeconds;
	}
	
	@Override
	public int getFlashDuration() {
		return flashDurationInSeconds;
	}
	
	@Override
	public void setFlashDurationOfSuccessMessages(int theFlashDurationInSeconds) {
		flashDurationOfSuccessMessagesInSeconds = theFlashDurationInSeconds;
	}

	@Override
	public int getFlashDurationOfSuccessMessages() {
		return flashDurationOfSuccessMessagesInSeconds;
	}
	
	@Override
	public void setChangedListener(Listener theChangedListener) {
		changedListeners.add( theChangedListener);
	}
	
	@Override
	public void doOnChange() {
		for (Listener changedListener : changedListeners) {
			synchronized (changedListener) {
				changedListener.handleEvent( new Event());
			}
		}
	}
	
	@Override
	public void enableFlashingOfTaggedSuccessMessages() {
		flashTaggedSuccessMessages = true;
	}
	
	@Override
	public void disableFlashingOfTaggedSuccessMessages() {
		flashTaggedSuccessMessages = false;
	}
	
	@Override
  public boolean isFlashingOfTaggedSuccessMessagesEnabled() {
  	return flashTaggedSuccessMessages;
  }

	@Override
	public Color getMessageForeground(ErrorKind theErrorKind) {
	  switch ( theErrorKind) {
			case error:
				return COLOR_WHITE;
			case warning:
				return COLOR_BLACK;
	    case info:
	    	return COLOR_BLACK;
			default:
	    	return COLOR_BLACK;
		}
	}

	@Override
	public Color getMessageBackground(ErrorKind theErrorKind) {
	  switch ( theErrorKind) {
			case error:
				return COLOR_RED;
			case warning:
				return COLOR_ORANGE;
	    case info:
	    	return COLOR_YELLOW;
			default:
	    	return COLOR_GREEN;
		}
	}

	@Override
	public Font getMessageFont(ErrorKind theErrorKind) {
		FontData fontData = new FontData();
		fontData.setHeight( fontData.getHeight() -1);
		return new Font( Display.getCurrent(), fontData);
	}


	
	
}
