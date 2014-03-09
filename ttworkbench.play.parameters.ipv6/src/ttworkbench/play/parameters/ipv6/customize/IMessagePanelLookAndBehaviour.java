package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.widgets.Listener;

public interface IMessagePanelLookAndBehaviour extends ILookAndBehaviour {

	void enableBeep();

	boolean isBeepEnabled();

	void disableBeep();

	void setFlashDurationOfSuccessMessages(int theFlashDurationInSeconds);

	int getFlashDurationOfSuccessMessages();

	void setFlashDuration(int theFlashDurationInSeconds);

	int getFlashDuration();

	void setChangedListener(Listener theChangedListener);

	void doOnChange();

	void enableFlashingOfTaggedSuccessMessages();

	void disableFlashingOfTaggedSuccessMessages();

	boolean isFlashingOfTaggedSuccessMessagesEnabled();
	
	//enableBeep();
	
	//disableBeep();
	
	//colors
	
	//delays
	
	//fonts
	
	

}
