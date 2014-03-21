package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;


public interface IMessageHydra {
	
	/**
	 * Creates a new client label that is bound to the master label.
	 * Changes in the master label take effect in the client labels, but not vice versa.
	 * 
	 * @param theMessageContainer
	 * @return
	 */
	public IMessageLabel newLabel( final IMessageContainer theMessageContainer);

	public void disposeLabels();

	void messageChanged(MessageLabel theSender);
	
	int getCountOfLabels();

	ErrorKind getErrorKind();
	
	boolean hasTag();

	String getMessage(); 
	
	// public void navigateToSource(); or focusSourceControl()

}
