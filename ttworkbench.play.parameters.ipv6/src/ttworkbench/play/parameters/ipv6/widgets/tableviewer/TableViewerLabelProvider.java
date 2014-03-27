package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.testingtech.ttworkbench.metamodel.muttcn.generator.CLTextGenerator;
import com.testingtech.ttworkbench.ttman.ManagementPlugin;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;



/**
 * This class provides the labels for the parameters table
 */
public class TableViewerLabelProvider implements ITableLabelProvider {
	/**
	 * Returns the image
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return Image
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	private LinkedList<ParameterEditorColumnType> registeredColumns = new LinkedList<ParameterEditorColumnType>(); 
	public void addColumn(ParameterEditorColumnType columnType) {
		registeredColumns.add( columnType);
	}
	
	/**
	 * Returns the column text
	 * 
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return String
	 */
	public String getColumnText(Object element, int columnIndex) {
		IParameterEditor<?> editor = (IParameterEditor<?>) element;
		Object value = registeredColumns.get( columnIndex).valueOf( editor);
		if (value instanceof EObject) {
			return CLTextGenerator.getUnformattedText( (EObject) value, ManagementPlugin.getRepositoryView());
		}
		else {
			return String.valueOf( value);
		}
	}

	/**
	 * Adds a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(ILabelProviderListener listener) {
		// Ignore it
	}

	/**
	 * Disposes any created resources
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * Returns whether altering this property on this element will affect
	 * the label
	 * 
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener listener) {
		// Ignore
	}

}
