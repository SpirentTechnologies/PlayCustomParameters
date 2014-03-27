package ttworkbench.play.parameters.ipv6.widgets;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import ttworkbench.play.parameters.ipv6.components.messaging.views.IMessageView;
import ttworkbench.play.parameters.ipv6.components.messaging.views.MessageListener;
import ttworkbench.play.parameters.ipv6.components.messaging.views.WidgetMessageDisplay;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.impl.CustomSWT;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorReport;

public abstract class CustomWidget extends NotifyingWidget {

	private Composite mainContainer;
	private Composite editorsContainer;
	private ScrolledComposite scrolledComposite;
	private IWidgetLookAndBehaviour lookAndBehaviour;
	private WidgetMessageDisplay messageDisplay;
	
  // TODO following parameter map is never read(?)
	private final Map<IParameterEditor<?>, Composite> editorControls = new LinkedHashMap<IParameterEditor<?>, Composite>();
	
	public CustomWidget( String theTitle, String theDescription, Image theImage) {
		super( theTitle, theDescription, theImage);
		setLookAndBehaviour( getDefaultLookAndBehaviour());
	}
	
	private void createMessageDisplay( Composite theParent) {
		messageDisplay = new WidgetMessageDisplay( this, theParent, SWT.NONE);
		messageDisplay.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false, 0, 0));
		messageDisplay.setLookAndBehaviour( lookAndBehaviour.getMessaagePanelLookAndBehaviour());
		messageDisplay.getLookAndBehaviour().addChangedListener( new Listener() {
			@Override
			public void handleEvent(Event theArg0) {
				updateControl();
			}
		});
		
		messageDisplay.addMessageListener( new MessageListener() {
			
			@Override
			public void report(ErrorReport theErrorReport) {
				Event event = new Event();
				event.widget = getControl();
				event.data = theErrorReport;
				event.type = CustomSWT.Message;
				Set<Listener> listeners = getListenersForEvent( CustomSWT.Message);
				for (Listener listener : listeners) {
					listener.handleEvent( event);
				}
			}
		});
		
    insertAdvancedButtonIntoMessageDisplay();
	}
	
	private void insertAdvancedButtonIntoMessageDisplay() {
		ToolBar toolBar = messageDisplay.getToolBar();
		ToolItem advancedModeItem = new ToolItem( toolBar, SWT.CHECK);
		advancedModeItem.setText( "Advanced Mode");	

		Listener selectionListener = new Listener() {
			public void handleEvent(Event event) {
				ToolItem item = (ToolItem)event.widget;
				if ( item.getSelection())
					setEditorsInAdvancedMode();
				else
					setEditorsInNormalMode();
			}
		};
		advancedModeItem.addListener( SWT.Selection, selectionListener);
		toolBar.pack();
		toolBar.getParent().layout();
	}
	
	
	
	protected void setEditorsInNormalMode() {
		AbstractEditor abstractEditor;
		List<IParameterEditor<?>> editors = getEditors();
		for (IParameterEditor editor : editors) {
			if ( editor instanceof AbstractEditor) {
				abstractEditor = (AbstractEditor) editor;
				if ( abstractEditor.isAdvancedMode() &&
					   abstractEditor.hasControl())
					abstractEditor.setVisible( false);
			}
		}
	}


	protected void setEditorsInAdvancedMode() {
		AbstractEditor abstractEditor;
		List<IParameterEditor<?>> editors = getEditors();
		for (IParameterEditor editor : editors) {
			if ( editor instanceof AbstractEditor) {
				abstractEditor = (AbstractEditor) editor;
				if ( abstractEditor.hasControl())
					abstractEditor.setVisible( true);
				else
					createFreshEditors();
			}
		}
	}
	

	@Override
	protected void designControl(Composite theControl) {
		
		theControl.setLayout(new GridLayout());

		mainContainer = theControl;
		// PRAGMA according to eclipse doc use of GridData styles is not recommended. Use SWT styles instead.  
		mainContainer.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 0, 0));
		mainContainer.setLayout( new GridLayout());
		
	  // display message panel above the scrollbox with the editors 
		//createMessagePanel( mainContainer); 
		createMessageDisplay( mainContainer);
				
		// scrollbox with the editors
	  scrolledComposite = new ScrolledComposite( mainContainer, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		// TODO adjust layout to layout data
	  scrolledComposite.setLayout( new FillLayout( SWT.HORIZONTAL));
		scrolledComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 0, 0));
    scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// important! to show causer of an error when click on the corresponding message
		scrolledComposite.setShowFocusedControl( true);
		
		editorsContainer = new Composite( scrolledComposite, SWT.None);
		GridLayout editorsLayout = new GridLayout();
		editorsLayout.marginHeight = 0;
		editorsContainer.setLayout( editorsLayout);
		editorsContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, false, 0, 0));
		
		createFreshEditors();
		
		scrolledComposite.setContent( editorsContainer);
		scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
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
		List<IParameterEditor<?>> freshEditors = acquireEditors();
		freshEditors.removeAll( editorControls.keySet());
		for (IParameterEditor<?> freshEditor : freshEditors) {
			createParameterEditor( freshEditor);
			if ( freshEditor instanceof ValidatingEditor<?>) {
				ValidatingEditor<?> validatingEditor = (ValidatingEditor<?>) freshEditor;
				validatingEditor.getMessageView().setSuperiorView( this.messageDisplay);
			}
		}
	}
		
		
	protected void createParameterEditor( final IParameterEditor<?> theEditor) {
		GridData editorGridData = new GridData(SWT.FILL, SWT.TOP, true, false, 0, 0);
		if ( editorsContainer != null) {	
			Composite editorControl = theEditor.createControl( editorsContainer);
			editorControls.put( theEditor, editorControl);
			
			editorControl.setLayoutData( editorGridData);

			if ( theEditor instanceof AbstractEditor<?>) {
			  AbstractEditor abstractEditor = ((AbstractEditor) theEditor);
				// react on dynamically insertion/deletion of controls when messages occur
				abstractEditor.getLookAndBehaviour().addControlChangedListener( new Listener() {

					@Override
					public void handleEvent(Event theArg0) {
						scrolledComposite.setMinSize( editorsContainer.computeSize( SWT.DEFAULT, SWT.DEFAULT));
						scrolledComposite.layout( true, true);
					}
				});
				
				// advanced flagged editors show only in advanced mode
				// assume, the editor is set visible by default
				if ( !this.isAdvancedMode() && abstractEditor.isAdvancedMode())
					abstractEditor.setVisible( false);
			}
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
	
	
	
	@Override
	public void setVisible(boolean theVisibleState) {
		super.setVisible( theVisibleState);
		// cannot hide a tab item, therefore just disable it
		setEnabled( theVisibleState);
	}
	
	@Override
	public void setEnabled(boolean theEnabledState) {
		super.setEnabled( theEnabledState);
		mainContainer.setEnabled( theEnabledState);
	}

}