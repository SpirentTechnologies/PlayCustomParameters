package ttworkbench.play.parameters.ipv6.editors.string;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;

public class StringEditorLookAndBehaviour extends DefaultEditorLookAndBehaviour {

	@Override
	public Layout getLayout() {
		RowLayout rowLayout = new RowLayout( SWT.HORIZONTAL);
		return rowLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		RowData[] layoutData = new RowData[3];
		layoutData[0] = new RowData();
		layoutData[1] = new RowData();
		layoutData[2] = new RowData();
		return layoutData;
	}

}
