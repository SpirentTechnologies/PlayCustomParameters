package ttworkbench.play.parameters.ipv6.components;

import java.util.EnumSet;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.customize.DefaultMessageViewLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IMessageViewLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageDisplay implements IMessageView {

	private IMessageViewLookAndBehaviour lookAndBehaviour = new DefaultMessageViewLookAndBehaviour();
	
	
	@Override
	public void endUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginUpdateForSender(Object theSenderId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flashMessage(String theTag, String theWarning, ErrorKind theErrorKind) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUntaggedMessage(String theMessage, ErrorKind theErrorKind) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putTaggedMessage(String theTag, String theMessage, ErrorKind theErrorKind) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getMessages(EnumSet<ErrorKind> theMessageKinds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMessageViewLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}

	@Override
	public void setLookAndBehaviour(IMessageViewLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}

	@Override
	public void wrapControl(Composite theWrappedComposite) {
		// TODO Auto-generated method stub
		
	}

}
