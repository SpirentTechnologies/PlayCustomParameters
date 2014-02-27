package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.text.MessageFormat;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class CellParameterEditor extends DialogCellEditor {
	
	private static final boolean SHOW_ONLY_VALUE_IN_TABLE = true;
	
	private Composite editorComposite;


	public CellParameterEditor( Composite parent) {
		super( parent);
	}

	public abstract IParameterEditor<Object> getEditor();


	@Override
	protected Control createContents(Composite cell) {
		if(SHOW_ONLY_VALUE_IN_TABLE) {
			Label label = new Label(cell, SWT.NONE);
			label.setText( String.valueOf( getEditor().getParameter().getValue()));
			return label;
		}
		else {
			editorComposite = getEditor().createControl( cell);
			editorComposite.setBackground(cell.getBackground());
			editorComposite.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent event) {
					 setValueToModel();
				}
			});
			editorComposite.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent event) {
					keyReleaseOccured(event);
				}
			});
			return editorComposite;
		}
	}

	@Override
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.keyCode == SWT.CR || keyEvent.keyCode == SWT.KEYPAD_CR) {
			setValueToModel();
		}
		super.keyReleaseOccured(keyEvent);
	}

	protected void setValueToModel() {
		Object value = getEditor().getParameter().getValue();
        boolean newValidState = isCorrect(value);
        if (newValidState) {
            markDirty();
            doSetValue(value);
        } else {
            setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { value.toString() }));
        }
	}

	@Override
	protected void updateContents(Object value) {
		if (editorComposite != null) {
			getEditor().getParameter().setValue( value);
		}
	}

	@Override
	protected void doSetFocus() {
		if (editorComposite != null) {
			editorComposite.setFocus();
		}
	}


	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		IParameterEditor<Object> editor = getEditor();
		
		Dialog dialog = new CellParameterEditorDialog(cellEditorWindow.getShell(), editor);
		if (dialog.open() == Window.OK) {
			return editor.getParameter().getValue();
		} else {
			return null;
		}
	}
}
