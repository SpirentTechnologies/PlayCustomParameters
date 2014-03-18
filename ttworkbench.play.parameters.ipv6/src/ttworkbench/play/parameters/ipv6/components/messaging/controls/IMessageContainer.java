package ttworkbench.play.parameters.ipv6.components.messaging.controls;


import org.eclipse.swt.widgets.Composite;

import ttworkbench.play.parameters.ipv6.customize.IMessageLookAndBehaviour;

public interface IMessageContainer {
	
	Composite getMessageComposite();

	IMessageLookAndBehaviour getMessageLookAndBehaviour();

	Object getMessageLayoutData();

}