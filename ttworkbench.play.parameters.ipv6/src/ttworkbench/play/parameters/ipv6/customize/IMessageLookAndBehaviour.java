package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageLookAndBehaviour extends ILookAndBehaviour {

	void enableBeep();

	boolean isBeepEnabled();

	void disableBeep();

	Color getMessageForeground( ErrorKind theErrorKind);
	
	Color getMessageBackground( ErrorKind theErrorKind);
	
	Image getMessageImage( ErrorKind theErrorKind);
	
	Font getMessageFont( ErrorKind theErrorKind);	

}
