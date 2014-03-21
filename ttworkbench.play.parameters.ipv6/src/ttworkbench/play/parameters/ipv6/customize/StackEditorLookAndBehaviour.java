package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Layout;

public class StackEditorLookAndBehaviour extends DefaultEditorLookAndBehaviour {

	@Override
	public Layout getLayout() {
		GridLayout gridLayout = new GridLayout( 1, false);
		gridLayout.makeColumnsEqualWidth = false;
		return gridLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		GridData[] gridData = new GridData[1]; 
		gridData[0] = new GridData( SWT.BEGINNING, SWT.FILL, true, true);
		return gridData;
	}

}
