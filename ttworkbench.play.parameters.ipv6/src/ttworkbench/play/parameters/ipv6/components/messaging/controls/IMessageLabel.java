package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageLabel {
	
	void setMessage( final String theMessage, final ErrorKind theErrorKind);

	ErrorKind getErrorKind();
	
	String getMessage();
	
	boolean hasTag();

	/**
	 * Navigates to the control in which the faulty input was made and focus it. 
	 * Is this Control placed on an not active respectively non visible IWidget, 
	 * the corresponding widget will be activated and shown.  
	 * Unless there is an parameter editor on the current active page, that handles the same parameter. 
	 * Then this editor is given the focus. 
	 */
	void navigateToCauser();

	boolean isCauserOnWidgetVisible(IWidget theWidget);
}
