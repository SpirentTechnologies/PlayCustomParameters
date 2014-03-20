package ttworkbench.play.parameters.ipv6.widgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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


import ttworkbench.play.parameters.ipv6.components.messaging.views.EditorMessageDisplay;
import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.components.messaging.views.MessagePanel;
import ttworkbench.play.parameters.ipv6.components.messaging.views.WidgetMessageDisplay;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class CustomWidget extends NotifyingWidget {

	private Composite mainContainer;
	private Composite editorsContainer;
	private ScrolledComposite scrolledComposite;
	private IWidgetLookAndBehaviour lookAndBehaviour;
	//private MessagePanel messagePanel;
  private WidgetMessageDisplay messageDisplay;
	private final Map<IParameterEditor, Composite> editorControls = new HashMap<IParameterEditor, Composite>();
	
	public CustomWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
		setLookAndBehaviour( getDefaultLookAndBehaviour());
	}
	
	
//	private void createMessagePanel( Composite theParent) {
//		messagePanel = new MessagePanel( theParent, SWT.NONE);
//		messagePanel.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false, 0, 0));
//		messagePanel.setLookAndBehaviour( lookAndBehaviour.getMessaagePanelLookAndBehaviour());
//		messagePanel.getLookAndBehaviour().addChangedListener( new Listener() {
//			@Override
//			public void handleEvent(Event theArg0) {
//				updateControl();
//			}
//		});
//	}
	
	private void createMessageDisplay( Composite theParent) {
		messageDisplay = new WidgetMessageDisplay( theParent, SWT.NONE);
		messageDisplay.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false, 0, 0));
		messageDisplay.setLookAndBehaviour( lookAndBehaviour.getMessaagePanelLookAndBehaviour());
		messageDisplay.getLookAndBehaviour().addChangedListener( new Listener() {
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
		// PRAGMA according to eclipse doc use of GridData styles is not recommended. Use SWT styles instead.  
		mainContainer.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 0, 0));
		mainContainer.setLayout( new GridLayout());
		
	  // display message panel above the scrollbox with the editors 
		//createMessagePanel( mainContainer); 
		createMessageDisplay( mainContainer);
				
		// scrollbox with the editors
	  scrolledComposite = new ScrolledComposite( mainContainer, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout( new FillLayout( SWT.HORIZONTAL));
		scrolledComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 0, 0));

		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		editorsContainer = new Composite( scrolledComposite, SWT.None);
		GridLayout editorsLayout = new GridLayout();
		editorsLayout.marginHeight = 0;
		editorsContainer.setLayout( editorsLayout);
		editorsContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, false, 0, 0));
		
		createFreshEditors();
		
		scrolledComposite.setContent( editorsContainer);
		scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		
		return mainContainer;
	}	


	@Override
	public void update() {
		createFreshEditors();
	}	
	
	protected void deleteParameterEditors() {
		if ( editorsContainer != null) {	
		 Control[] controls = editorsContainer.getChildren();
			for (Control control : controls) {
			  control.dispose();	
			}
		}
	}

	
	protected void createFreshEditors() {
		Set<IParameterEditor> freshEditors = getEditors();
		freshEditors.removeAll( editorControls.keySet());
		for (IParameterEditor freshEditor : freshEditors) {
			createParameterEditor( freshEditor);
		}
	}
		
		
	protected void createParameterEditor( final IParameterEditor theEditor) {
		GridData editorGridData = new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0);
		if ( editorsContainer != null) {	
			Composite editorControl = theEditor.createControl( editorsContainer);
			editorControls.put( theEditor, editorControl);
			
			editorControl.setLayoutData( editorGridData);

			// react on dynamically insertion/deletion of controls when messages occur
			if ( theEditor instanceof AbstractEditor<?>)
				((AbstractEditor<?>) theEditor).getLookAndBehaviour().addControlChangedListener( new Listener() {

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


	@Override
	protected IMessageView getMessageView() {
		return messageDisplay;
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