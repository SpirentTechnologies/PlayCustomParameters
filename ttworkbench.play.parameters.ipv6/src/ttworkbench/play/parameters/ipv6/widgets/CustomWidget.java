package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class CustomWidget extends NotifyingWidget {
	
	public CustomWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
	}

	private Composite editorsContainer;
	private ScrolledComposite scrolledComposite;
	
	
	
	@Override
	public Control createControl(Composite theParent) {

		theParent.setLayout( new FillLayout(SWT.HORIZONTAL));

	  scrolledComposite = new ScrolledComposite( theParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout( new FillLayout( SWT.HORIZONTAL));
		scrolledComposite.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		editorsContainer = new Composite( scrolledComposite, SWT.None);
		editorsContainer.setLayout( new GridLayout( 1, true));
		editorsContainer.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
		
		createParameterEditors();
		
		scrolledComposite.setContent( editorsContainer);
		scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		
		return scrolledComposite;
	}	


	public void update() {
		// TODO better add editors incremental
		deleteParameterEditors();
		createParameterEditors();
	}	
	
	protected void deleteParameterEditors() {
		if ( editorsContainer != null) {	
		 Control[] controls = editorsContainer.getChildren();
			for (Control control : controls) {
			  control.dispose();	
			}
		}
	}

	protected void createParameterEditors() {
		//GridData gridData = new GridData( SWT.BEGINNING, SWT.FILL, true, true);
		//GridLayout gridLayout = new GridLayout( 1, false);
		//gridLayout.makeColumnsEqualWidth = false;
		if ( editorsContainer != null) {	
			Set<IParameterEditor> editors = getEditors();
			for ( IParameterEditor editor : editors) {
				Control editorControl = editor.createControl( editorsContainer/*, gridData, gridLayout*/);
				editorControl.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0));
				
				// react on dynamically insertion/deletion of controls when messages occur
				if ( editor instanceof AbstractEditor<?>)
					((AbstractEditor<?>) editor).setControlChangedListener( new Listener() {
						
						@Override
						public void handleEvent(Event theArg0) {
							scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
							scrolledComposite.layout( true, true);
						}
					});
			}
			editorsContainer.setSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
			editorsContainer.layout();
		}
	}

}
