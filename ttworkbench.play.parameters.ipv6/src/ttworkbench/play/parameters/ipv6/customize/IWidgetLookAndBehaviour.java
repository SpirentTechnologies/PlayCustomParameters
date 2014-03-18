package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.widgets.Listener;

public interface IWidgetLookAndBehaviour extends ILookAndBehaviour {

	IValidatingEditorLookAndBehaviour getEditorLookAndBehaviour();

	IMessageViewLookAndBehaviour getMessaagePanelLookAndBehaviour();

	
	
	void addControlChangedListener(Listener theControlChangedListener);

	void doOnChange();
	
}
