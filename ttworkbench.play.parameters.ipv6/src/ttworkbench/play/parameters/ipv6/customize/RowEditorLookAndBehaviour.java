package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Layout;

public class RowEditorLookAndBehaviour extends DefaultEditorLookAndBehaviour {

	@Override
	public Layout getLayout() {
		RowLayout rowLayout = new RowLayout();
		// rowLayout.wrap = false;
		rowLayout.pack = false;
		rowLayout.justify = false;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 5;
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 5;
		rowLayout.spacing = 10;
		return rowLayout;
	}

	@Override
	public Object[] getLayoutDataOfControls() {
		/*
		 * Die Methode macht keinen Sinn, da nicht feststeht welche
		 * Data-Informationen wo in dem Feld stehen und somit das unter Verwendung
		 * der Methode nicht mehr austauschbar ist.
		 */
		throw new UnsupportedOperationException();
	}

}
