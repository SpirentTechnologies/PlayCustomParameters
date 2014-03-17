package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class DefaultEditorLookAndBehaviour implements IValidatingEditorLookAndBehaviour {

	private Set<Listener> controlChangedListeners = new HashSet<Listener>();

	@Override
	public Layout getLayout() {
		GridLayout gridLayout = new GridLayout( 3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;	
		return gridLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		GridData[] gridData = new GridData[1];
		gridData[0] = new GridData( SWT.FILL, SWT.FILL, true, false);
		return gridData;
	}

	@Override
	public IEditorLookAndBehaviour getEditorLookAndBehaviour() {
		return this;
	}

	@Override
	public IMessageViewLookAndBehaviour getMessaagePanelLookAndBehaviour() {
		return new DefaultMessageViewLookAndBehaviour();
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
