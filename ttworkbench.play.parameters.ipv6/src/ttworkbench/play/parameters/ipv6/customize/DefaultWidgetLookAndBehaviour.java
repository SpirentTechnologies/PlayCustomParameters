package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.editors.integer.IntegerEditorLookAndBehaviour;

public class DefaultWidgetLookAndBehaviour implements IWidgetLookAndBehaviour {

	private Set<Listener> controlChangedListeners = new HashSet<Listener>();

	@Override
	public IValidatingEditorLookAndBehaviour getEditorLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}

	@Override
	public IMessageViewLookAndBehaviour getMessaagePanelLookAndBehaviour() {
		return getEditorLookAndBehaviour().getMessagePanelLookAndBehaviour();
	}

	@Override
	public void addControlChangedListener(Listener theControlChangedListener) {
		this.controlChangedListeners.add( theControlChangedListener);
	}

	@Override
	public void doOnChange() {
		for (Listener controlChangedListener : controlChangedListeners) {
			controlChangedListener.handleEvent( new Event());
		}
	}

}
