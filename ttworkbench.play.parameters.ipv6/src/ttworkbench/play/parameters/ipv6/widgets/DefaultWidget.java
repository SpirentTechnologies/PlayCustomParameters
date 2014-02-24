package ttworkbench.play.parameters.ipv6.widgets;

import java.awt.Label;
import java.util.Set;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class DefaultWidget extends AbstractWidget {

	private static final String TITLE = "Default Widget";
	private static final String DESCRIPTION = "";
	private static final Image IMAGE = null;
	
	public DefaultWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}
	
	@Override
	public Control createControl(Composite theParent) {
		
		theParent.setLayout( new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite( theParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal( true);
		scrolledComposite.setExpandVertical( true);
		
		
		Composite editorContainer = new Composite( scrolledComposite, SWT.None);
		
		/*
		 * TODO replace this later with the TableViewer component
		 * final TableViewer tableViewer = new TableViewer( editorContainer);
		 */
		
		GridData gridData = new GridData( SWT.FILL, SWT.FILL, true, false);
		editorContainer.setLayout( new GridLayout( 2, false));
	    Set<IParameterEditor> editors = getEditors();
	    for ( IParameterEditor editor : editors) {
			
		    CLabel label = new CLabel( editorContainer, SWT.LEFT);
			label.setText( editor.getParameter().getName());
			label.setLayoutData( gridData);
			
			label = new CLabel( editorContainer, SWT.LEFT);
			label.setText( editor.getParameter().getValue().toString());
			label.setLayoutData( gridData);
			
		}

		scrolledComposite.setContent( editorContainer);
		scrolledComposite.setMinSize( editorContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
				// TODO Auto-generated method stub
		return scrolledComposite;
	}

	@Override
	protected Set<IParameter> filterRelevantParameters(
			Set<IParameter> theParameters) {
		return theParameters;
	}

}
