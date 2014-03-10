package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class DefaultWidgetLookAndBehaviour implements IWidgetLookAndBehaviour {

	private Set<Listener> controlChangedListeners = new HashSet<Listener>();

	@Override
	public IValidatingEditorLookAndBehaviour getEditorLookAndBehaviour() {
		return new IntegerEditorLookAndBehaviour();
	}

	@Override
	public IMessagePanelLookAndBehaviour getMessaagePanelLookAndBehaviour() {
		return getEditorLookAndBehaviour().getMessaagePanelLookAndBehaviour();
	}

	@Override
	public void setControlChangedListener(Listener theControlChangedListener) {
		this.controlChangedListeners.add( theControlChangedListener);
	}

	@Override
	public void doOnChange() {
		for (Listener controlChangedListener : controlChangedListeners) {
			controlChangedListener.handleEvent( new Event());
		}
	}

}
