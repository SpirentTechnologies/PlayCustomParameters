package ttworkbench.play.parameters.ipv6.customize;

import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public interface IEditorLookAndBehaviour extends ILookAndBehaviour {

	Layout getLayout();
	
	/**
	 * Provides an array of layout data in the form that the first cell 
	 * contains the layout data for all controls. Each further array cell n set the 
	 * layout for the corresponding control in the grid cell n.
	 *    
	 * @return a field of layout data objects with an asserted minimum length of 1.
	 */
	Object[] getLayoutDataOfControls();
	
	
	void addControlChangedListener(Listener theControlChangedListener);

	void doOnChange();
	
}
