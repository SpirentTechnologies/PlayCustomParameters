package ttworkbench.play.parameters.ipv6.components.messaging.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

public class MessageHeader extends Composite implements IMessageContainer {

	/**
	 * 
	 */
	private final IMessageView<?> messageView;

	public MessageHeader( IMessageView<?> theMessageView, final Composite theParent) {
		super( theParent, SWT.NONE);
		this.messageView = theMessageView;
		
		RowLayout messageHeaderLayout = new RowLayout();
		messageHeaderLayout.spacing = 0;
		messageHeaderLayout.marginWidth = 1;
		messageHeaderLayout.marginTop = 3;
		messageHeaderLayout.marginBottom = 0;
		setLayout( messageHeaderLayout);		
	}

	@Override
	public Composite getMessageComposite() {
		return this;
	}

	@Override
	public IMessageLookAndBehaviour getMessageLookAndBehaviour() {
		return messageView.getLookAndBehaviour().getFlashMessageLookAndBehaviour();
	}
	
	@Override
	public Object getMessageLayoutData() {
		return new RowData();
	}
	

	
	
}