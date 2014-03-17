package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;


import ttworkbench.play.parameters.ipv6.components.messaging.views.DefaultMessageDisplay;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.components.messaging.views.MessagePanel;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class CustomWidget extends NotifyingWidget {

	private Composite mainContainer;
	private Composite editorsContainer;
	private ScrolledComposite scrolledComposite;
	private IWidgetLookAndBehaviour lookAndBehaviour;
	private MessagePanel messagePanel;

	
	
	public CustomWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
		setLookAndBehaviour( getDefaultLookAndBehaviour());
	}
	
	
	private void createMessagePanel( Composite theParent) {
		messagePanel = new MessagePanel( theParent, SWT.NONE);
		messagePanel.setLayoutData( new GridData(GridData.FILL_HORIZONTAL, GridData.BEGINNING, true, false, 0, 0));
		messagePanel.setLookAndBehaviour( lookAndBehaviour.getMessaagePanelLookAndBehaviour());
		messagePanel.getLookAndBehaviour().setChangedListener( new Listener() {
			@Override
			public void handleEvent(Event theArg0) {
				updateControl();
			}
		});
	}
		
	
	@Override
	public Control createControl(Composite theParent) {

		theParent.setLayout(new GridLayout());

		mainContainer = new Composite( theParent, SWT.None);
		mainContainer.setLayoutData( GridData.FILL_BOTH);
		mainContainer.setLayout( new GridLayout());
		//mainContainer.setLayoutData( new GridData(SWT.FILL, SWT.TOP, true, true, 0, 0));
		
	  // display message panel above the scrollbox with the editors 
		createMessagePanel( mainContainer); 
		
		// scrollbox with the editors
	  scrolledComposite = new ScrolledComposite( mainContainer, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout( new FillLayout( SWT.HORIZONTAL));
		scrolledComposite.setLayoutData( new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true, 0, 0));

		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		editorsContainer = new Composite( scrolledComposite, SWT.None);
		editorsContainer.setLayout( new GridLayout( 1, true));
		GridLayout editorsLayout = new GridLayout( 1, true);
		editorsLayout.marginHeight = 0;
		editorsContainer.setLayout( editorsLayout);
		editorsContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, false, 0, 0));
		
		createParameterEditors();
		
		scrolledComposite.setContent( editorsContainer);
		scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		
		return mainContainer;
	}	


	@Override
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
		GridData editorGridData = new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0);
		if ( editorsContainer != null) {	
			Set<IParameterEditor> editors = getEditors();
			for ( IParameterEditor editor : editors) {
				Control editorControl = editor.createControl( editorsContainer);
				editorControl.setLayoutData( editorGridData);
				
				// react on dynamically insertion/deletion of controls when messages occur
				if ( editor instanceof AbstractEditor<?>)
					((AbstractEditor<?>) editor).getLookAndBehaviour().setControlChangedListener( new Listener() {
						
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


	@Override
	protected IMessageView getMessageView() {
		return messagePanel;
	}

	
	protected void setLookAndBehaviour(IWidgetLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}
	
	public IWidgetLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}
	
	public void updateControl() {
		lookAndBehaviour.doOnChange();
		if ( mainContainer != null)
			mainContainer.layout();
	}
	
	protected abstract IWidgetLookAndBehaviour getDefaultLookAndBehaviour();
	


}
