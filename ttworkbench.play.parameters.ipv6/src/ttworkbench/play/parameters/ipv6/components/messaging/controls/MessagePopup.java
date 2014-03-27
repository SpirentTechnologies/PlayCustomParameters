package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import java.util.EnumSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import ttworkbench.play.parameters.ipv6.components.messaging.components.registry.IMessageRegistry;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessagePopup extends Composite implements IMessageContainer {
	
	private static final int MAX_POPUP_HEIGHT = 100;
	private static final Point POPUP_SHIFT = new Point( 12, 12);
	/**
	 * 
	 */
	private final IMessageView messageView;
	private CLabel label;
	private Shell popupShell;
	private Composite messageContainer;
	private ScrolledComposite scrolledComposite;
	private Listener mouseMoveListener = createMouseMoveListener();
	private String labelTextFormat = "%d %s.";  //were %d is replaced by the count and %s by the mesage kind.
	
	public MessagePopup( IMessageView theMessageView, final Composite theParent) {
		super( theParent, SWT.None);
		this.messageView = theMessageView;
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
		popupShell = new Shell( getShell(),  SWT.NO_TRIM | SWT.TOOL | SWT.NO_FOCUS);
		// layout of popup shell
		GridLayout popupLayout = new GridLayout();
		popupLayout.horizontalSpacing = 0;
		popupLayout.verticalSpacing = 0;
		popupLayout.marginWidth = 1;
		popupLayout.marginHeight = 1;
		popupShell.setLayout( popupLayout);
		// set frame color
		popupShell.setBackground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_NORMAL_SHADOW));
	
		// insert a scrollbox
	  scrolledComposite = new ScrolledComposite( popupShell, SWT.H_SCROLL | SWT.V_SCROLL);
	  scrolledComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 0, 0));
		scrolledComposite.setLayout( new FillLayout());
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(false);
		
		// insert message container into scrollbox
		messageContainer = new Composite( scrolledComposite, SWT.None);
		
		// layout of message container shell
		GridLayout messageContainerLayout = new GridLayout();
		messageContainerLayout.horizontalSpacing = 0;
		messageContainerLayout.verticalSpacing = 0;
		messageContainerLayout.marginWidth = 0;
		messageContainerLayout.marginHeight = 0;
		messageContainer.setLayout( messageContainerLayout);
		
		scrolledComposite.setContent( messageContainer);
		scrolledComposite.setMinSize( messageContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		
		messageContainer.addListener( SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event theEvent) {
				hidePopup();

				if ( theEvent.button == 1) {  
					Control underlayingControl = getUnterlayingControl( theEvent);
					if ( underlayingControl instanceof MessageLabel) {
						MessageLabel messageLabel = (MessageLabel) underlayingControl;
						messageLabel.navigateToCauser();
					}
				}
			}

			private Control getUnterlayingControl( Event theEvent) {
				for ( Control control : messageContainer.getChildren()) {
					if ( control.getBounds().contains( theEvent.x,theEvent.y))
						return control;
				}
				return null;
			}
		});
  
	}
		
	
	private void showPopup( final Point theMousePosition) {
		// TODO: handle case if popup is shown out of or intersect with getDisplay.getClientArea()
		popupShell.setLocation( label.toDisplay( new Point( theMousePosition.x -POPUP_SHIFT.x, theMousePosition.y -POPUP_SHIFT.y)));
		for ( Control control : messageContainer.getChildren()) {
			control.setEnabled( false);
		}
		updatePopup();
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
				  if ( !visible) {
				  	Point labelCursorPosition = label.toControl( displayCursorPosition);
				  	if (label.getClientArea().contains( labelCursorPosition)) {
				  	  Point popupLocation = popupShell.getLocation();
				  	  Point newPopupPosition = new Point( popupLocation.x, popupLocation.y);
				  	  if ( displayCursorPosition.x - popupLocation.x < 0)
				  	  	newPopupPosition.x = displayCursorPosition.x;
				  	  if ( displayCursorPosition.y - popupLocation.y < 0)
				  	  	newPopupPosition.y = displayCursorPosition.y;
				  	  popupShell.setLocation( newPopupPosition);
				  	} else
				  	  hidePopup();
				  }
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
		messageContainer.pack( true);
		messageContainer.layout( true);
		
		Point computedScrollSize = scrolledComposite.computeSize( SWT.DEFAULT, messageContainer.getSize().y);
		scrolledComposite.setMinSize( computedScrollSize);
		
		Point computedPopupSize = popupShell.computeSize( SWT.DEFAULT, messageContainer.getSize().y +2);
		popupShell.setSize( computedPopupSize.x, Math.min( computedPopupSize.y, MAX_POPUP_HEIGHT));
		
		popupShell.update();
		if ( messageView.getMessageInformation().getTotalCount() == 0)
		  hidePopup();
	}

	private void updateLabel() {
		// get highest weighted error kind 
		ErrorKind errorKind = messageView.getMessageInformation().getHighestErrorKind();
		int messageCount = messageView.getMessageInformation().getCountOfMessagesWithErrorKind( EnumSet.of( errorKind));

		if ( messageCount == 0) {
			label.setImage( null);
			label.setText( "");
			label.setVisible( false);
			this.setVisible( false);
		} else {
			// get corresponding icon
			Image image = getMessageLookAndBehaviour().getMessageImage( errorKind);
			String message;
			switch (errorKind) {
				case error:
					message = String.format( labelTextFormat, messageCount, "errors");
					// size = 
					break;
				case warning:
					message = String.format( labelTextFormat, messageCount, "warings");
					break;
				default:
					message = String.format( labelTextFormat, messageCount, "informations");
					break;
			}

			label.setFont( getMessageLookAndBehaviour().getMessageFont( errorKind));
			label.setImage( image);
			label.setText( message);
			this.setVisible( true);
			label.setVisible( true);
		}
		setSize( computeSize( SWT.DEFAULT, SWT.DEFAULT));
		layout( true);
		//System.out.println( label.getBounds() + "  " + this.getParent().getParent().getClass().getSimpleName());
	}

	public void setLabelTextFormat(String theLabelTextFormat) {
		labelTextFormat = theLabelTextFormat;
	}

	@Override
	public Composite getMessageComposite() {
		return messageContainer;
	}


	@Override
	public IMessageLookAndBehaviour getMessageLookAndBehaviour() {
		return messageView.getLookAndBehaviour().getMessageLookAndBehaviour();
	}

	@Override
	public Object getMessageLayoutData() {
    return new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
	}
	
	
}