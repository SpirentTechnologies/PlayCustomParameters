package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public interface IMessageContainer {
	
	Composite getMessageComposite();

	Image getMessageImage(ErrorKind theErrorKind);

	Color getMessageForeground(ErrorKind errorKind);

	Color getMessageBackground(ErrorKind errorKind);

	Font getMessageFont(ErrorKind theErrorKind);
	
}