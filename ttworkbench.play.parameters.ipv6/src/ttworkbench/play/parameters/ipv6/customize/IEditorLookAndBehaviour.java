package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public interface IEditorLookAndBehaviour extends ILookAndBehaviour {

	Layout getLayout();
	
	/**
	 *  
	 * @return a field of layout data objects with an asserted minimum length of 1.
	 */
	Object[] getLayoutDataOfControls();
	
	
	void addControlChangedListener(Listener theControlChangedListener);

	void doOnChange();
	
}
