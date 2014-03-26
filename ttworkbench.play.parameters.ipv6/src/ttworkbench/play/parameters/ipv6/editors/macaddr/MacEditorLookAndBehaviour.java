package ttworkbench.play.parameters.ipv6.editors.macaddr;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;

public class MacEditorLookAndBehaviour extends DefaultEditorLookAndBehaviour{

	@Override
	public Layout getLayout() {
		RowLayout rowLayout = new RowLayout( SWT.HORIZONTAL);
		return rowLayout;
	}
	
	@Override
	public Object[] getLayoutDataOfControls() {
		RowData[] layoutData = new RowData[3];
		layoutData[0] = new RowData();
		return layoutData;
	}
}
