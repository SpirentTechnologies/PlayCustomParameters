package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Listener;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageViewLookAndBehaviour extends ILookAndBehaviour {

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
	
	Color getMessageForeground( ErrorKind theErrorKind);
	
	Color getMessageBackground( ErrorKind theErrorKind);

	Color getFlashMessageForeground( ErrorKind theErrorKind);
	
	Color getFlashMessageBackground( ErrorKind theErrorKind);
	
	Image getMessageImage( ErrorKind theErrorKind);
	
	Font getMessageFont( ErrorKind theErrorKind);	

}
