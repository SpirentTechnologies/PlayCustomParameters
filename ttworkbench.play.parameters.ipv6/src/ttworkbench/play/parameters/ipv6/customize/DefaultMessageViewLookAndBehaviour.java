package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class DefaultMessageViewLookAndBehaviour implements IMessageViewLookAndBehaviour {
	
	private static final Display CURRENT_DISPLAY = Display.getCurrent();
	private static final Color COLOR_RED = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_RED);
	private static final Color COLOR_YELLOW = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_YELLOW);
	private static final Color COLOR_ORANGE = new Color( CURRENT_DISPLAY, 255, 165, 0);
	private static final Color COLOR_GREEN = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_GREEN);
	private static final Color COLOR_WHITE = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_WHITE);
	private static final Color COLOR_BLACK = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_BLACK);
	private static final Color COLOR_BTN_FACE = CURRENT_DISPLAY.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	
	private static final Color COLOR_TT_RED = new Color( CURRENT_DISPLAY, 158, 26, 56); // #9e1a38
	private static final Color COLOR_PASTEL_CREME = new Color( CURRENT_DISPLAY, 255, 255, 204); // ffffcc
	private static final Color COLOR_PASTEL_GREEN = new Color( CURRENT_DISPLAY, 221,255,204); // ddffcc
	private static final Color COLOR_PASTEL_ROSE = new Color( CURRENT_DISPLAY, 255,204,221); // ffccdd
	private static final Color COLOR_PASTEL_ORANGE = new Color( CURRENT_DISPLAY, 255,221,204); // ffddcc
	private static final Color COLOR_PASTEL_BLUE = new Color( CURRENT_DISPLAY, 204,221,255); // ccddff
	
	private static final Color COLOR_DARK_GREEN = new Color( CURRENT_DISPLAY, 32,96,0); // 206000
	private static final Color COLOR_DARK_RED = new Color( CURRENT_DISPLAY, 96,0,32); // 600020
	private static final Color COLOR_DARK_YELLOW = new Color( CURRENT_DISPLAY, 96,96,0); // 606000
	private static final Color COLOR_DARK_ORANGE = new Color( CURRENT_DISPLAY, 96,32,0); // 602000
	private static final Color COLOR_DARK_BLUE = new Color( CURRENT_DISPLAY, 0,32,96); // 002060
	
	
	private final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
	private final Image ERROR_IMAGE = sharedImages.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	private final Image WARNING_IMAGE = sharedImages.getImage(ISharedImages.IMG_OBJS_WARN_TSK);
	private final Image INFO_IMAGE = sharedImages.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
	
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
	public void setFlashDuration( final int theFlashDurationInSeconds) {
	  this.flashDurationInSeconds = theFlashDurationInSeconds;
	}
	
	@Override
	public int getFlashDuration() {
		return flashDurationInSeconds;
	}
	
	@Override
	public void setFlashDurationOfSuccessMessages( final int theFlashDurationInSeconds) {
		flashDurationOfSuccessMessagesInSeconds = theFlashDurationInSeconds;
	}

	@Override
	public int getFlashDurationOfSuccessMessages() {
		return flashDurationOfSuccessMessagesInSeconds;
	}
	
	@Override
	public void setChangedListener( final Listener theChangedListener) {
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
	public Color getMessageForeground( final ErrorKind theErrorKind) {
	  switch ( theErrorKind) {
			case error:
				return COLOR_DARK_RED;
			case warning:
				return COLOR_DARK_YELLOW;
	    case info:
	    	return COLOR_DARK_BLUE;
			default:
	    	return COLOR_DARK_GREEN;
		}
	}

	@Override
	public Color getMessageBackground( final ErrorKind theErrorKind) {
	  switch ( theErrorKind) {
			case error:
				return COLOR_PASTEL_ROSE;
			case warning:
				return COLOR_PASTEL_CREME;
	    case info:
	    	return COLOR_PASTEL_BLUE;
			default:
	    	return COLOR_PASTEL_GREEN;
		}
	}
	
	
	@Override
	public Color getFlashMessageForeground( final ErrorKind theErrorKind) {
		switch ( theErrorKind) {
			case error:
				return COLOR_BLACK;
			case warning:
				return COLOR_BLACK;
	    case info:
	    	return COLOR_BLACK;
			default:
	    	return COLOR_BLACK;
		}
	}

	@Override
	public Color getFlashMessageBackground( final ErrorKind theErrorKind) {
		 switch ( theErrorKind) {
				case error:
					return COLOR_BTN_FACE;
				case warning:
					return COLOR_BTN_FACE;
		    case info:
		    	return COLOR_BTN_FACE;
				default:
		    	return COLOR_BTN_FACE;
			}
	}

	@Override
	public Image getMessageImage( final ErrorKind theErrorKind) {
		switch (theErrorKind) {
			case error:
				return ERROR_IMAGE;
			case warning:
				return  WARNING_IMAGE;
			default:
				return INFO_IMAGE;
		}
	}
	
	@Override
	public Font getMessageFont( final ErrorKind theErrorKind) {
		FontData fontData = new FontData();
		fontData.setHeight( fontData.getHeight() -1);
		return new Font( Display.getCurrent(), fontData);
	}



	
	
}
