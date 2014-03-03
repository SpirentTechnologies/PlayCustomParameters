package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class CellParameterEditorDialog extends Dialog {

	private IParameterEditor<Object> editor;

	public CellParameterEditorDialog( Shell parentShell, IParameterEditor<Object> editor) {
		super( parentShell);
		this.editor = editor;
	}

	@Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText(editor.getTitle());
 }

	@Override
	protected Control createDialogArea(Composite parent) {
		if (parent instanceof Shell) {
			((Shell) parent).setText( editor.getTitle());
		}
		
		Composite container = (Composite) super.createDialogArea( parent);
		editor.createControl( container);
		container.setSize( container.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		container.layout();
		
		return container;
	}
}
