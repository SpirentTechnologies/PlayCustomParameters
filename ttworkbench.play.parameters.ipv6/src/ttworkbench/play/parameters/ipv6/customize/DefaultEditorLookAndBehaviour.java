package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class DefaultEditorLookAndBehaviour implements IValidatingEditorLookAndBehaviour {

	private Listener controlChangedListener;

	@Override
	public Layout getLayout() {
		GridLayout gridLayout = new GridLayout( 3, false);
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
	public IMessagePanelLookAndBehaviour getMessaagePanelLookAndBehaviour() {
		return new DefaultMessagePanelLookAndBehaviour();
	}

	@Override
	public void setControlChangedListener(Listener theControlChangedListener) {
		this.controlChangedListener = theControlChangedListener;
	}

	@Override
	public void doOnChange() {
		if ( controlChangedListener != null)
	  	controlChangedListener.handleEvent( new Event());
	}

}
