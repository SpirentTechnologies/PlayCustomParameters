package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.widgets.Listener;

public interface IMessageViewLookAndBehaviour extends ILookAndBehaviour {

	void setFlashDurationOfSuccessMessages(int theFlashDurationInSeconds);

	int getFlashDurationOfSuccessMessages();

	void setFlashDuration(int theFlashDurationInSeconds);

	int getFlashDuration();

	void addChangedListener(Listener theChangedListener);

	void doOnChange();

	void enableFlashingOfTaggedSuccessMessages();

	void disableFlashingOfTaggedSuccessMessages();

	boolean isFlashingOfTaggedSuccessMessagesEnabled();
	
	IMessageLookAndBehaviour getMessageLookAndBehaviour();
	
	IMessageLookAndBehaviour getFlashMessageLookAndBehaviour();
	
}
