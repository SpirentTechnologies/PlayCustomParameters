package ttworkbench.play.parameters.ipv6.editors.floatingpoint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;

public class FloatingPointEditorLookAndBehaviour extends DefaultEditorLookAndBehaviour {

	@Override
	public Layout getLayout() {
		GridLayout gridLayout = new GridLayout( 3, false);
		return gridLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		GridData[] layoutData = new GridData[6];
		layoutData[0] = new GridData( 200, SWT.DEFAULT);
		layoutData[1] = new GridData( SWT.LEFT, SWT.TOP, false, false, 0, 0);
		layoutData[2] = new GridData( SWT.LEFT, SWT.TOP, true, false, 0, 0);
		layoutData[3] = new GridData( SWT.LEFT, SWT.TOP, false, false, 0, 0);
		layoutData[4] = new GridData( SWT.LEFT, SWT.TOP, false, false, 0, 0);
		layoutData[5] = new GridData( SWT.LEFT, SWT.TOP, true, false, 0, 0);
		return layoutData;
	}

}
