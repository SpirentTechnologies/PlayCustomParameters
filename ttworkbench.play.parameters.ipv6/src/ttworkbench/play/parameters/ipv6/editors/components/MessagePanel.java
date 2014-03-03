package ttworkbench.play.parameters.ipv6.editors.components;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessagePanel extends Composite {
	
	
	
	private class MessageLine extends Composite {

		private final Object id;
		private String message;
		private ErrorKind errorKind;
		private Label label;
		
		public MessageLine( final Object theId, final String theMessage, final ErrorKind theErrorKind) {
			super( MessagePanel.this, SWT.NONE);
			this.id = theId;
			this.setLayout( new FillLayout());
			setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
			label = new Label( this, SWT.NONE);
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
	}
	
	
	
	
	
	
	
	
	

	private final Map<Object, MessageLine> messages = new HashMap<Object, MessageLine>();	 
	
			
	public MessagePanel( final Composite theParent, final int theStyle) {
		super( theParent, theStyle);
		setLayout(new GridLayout(1, true));
	}
	
	public void addMessage( final Object theId, final String theMessage, final ErrorKind theErrorKind) {
		if ( !updateMessage( theId, theMessage, theErrorKind) && 
				 !theErrorKind.equals( ErrorKind.success)) {
			MessageLine newMessageLine = new MessageLine( theId, theMessage, theErrorKind);
			messages.put( theId, newMessageLine);
		}
	}

	
	
	private boolean updateMessage( final Object theId, final String theMessage, final ErrorKind theErrorKind) {
		if ( !messages.containsKey( theId))
			return false;
		
		MessageLine messageLine = messages.get( theId);
		messageLine.setMessage( theMessage, theErrorKind);
		
	  return true;
	}
	
	
	// messages.remove( theId);
	
	
	

}
