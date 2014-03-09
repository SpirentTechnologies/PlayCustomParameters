package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

public class DefaultEditorLookAndBehaviour implements IValidatingEditorLookAndBehaviour {

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

}
