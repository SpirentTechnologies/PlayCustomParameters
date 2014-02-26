package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


/**
 * This class provides the content for the table
 */
public class TableViewerContentProvider implements IStructuredContentProvider {

  /**
   * Gets the elements for the table
   * 
   * @param arg0
   *            the model
   * @return Object[]
   */
  public Object[] getElements(Object arg0) {
    // Returns all the players in the specified team
    return ((ParameterEditorHolder) arg0).getParameterEditors().toArray();
  }

  /**
   * Disposes any resources
   */
  public void dispose() {
    // We don't create any resources, so we don't dispose any
  }

  /**
   * Called when the input changes
   * 
   * @param arg0
   *            the parent viewer
   * @param arg1
   *            the old input
   * @param arg2
   *            the new input
   */
  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
    // Nothing to do
  }
}