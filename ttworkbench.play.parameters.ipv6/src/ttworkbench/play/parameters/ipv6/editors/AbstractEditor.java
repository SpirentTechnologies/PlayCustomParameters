package ttworkbench.play.parameters.ipv6.editors;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public abstract class AbstractEditor<T> implements IParameterEditor<T> {

	public enum ComponentState {
		INITIATING, CONSTRUCTED, DESTROYING, DESTROYED
	}
	
	public enum ControlState {
		NULL, CREATING, CREATED, UPDATING, DISPOSED
	}
	
	public enum EditorStateFlag {
		UPDATE_CONFIG, UPDATE_CONTROL, UPDATE_PARAMETER, VERIFYING, VALIDATING
	}
	
	public interface IEditorState {
		boolean contains( ComponentState theComponentState);
		
		boolean contains( ControlState theControlState);
		
		boolean contains( EditorStateFlag theEditorStateFlag);
		
		void setFlag( EditorStateFlag theEditorStateFlag);
		
		void unsetFlag( EditorStateFlag theEditorStateFlag);
		
	}
	
	public class EditorState implements IEditorState {
		
		private ComponentState componentState = ComponentState.INITIATING;
		private ControlState controlState = ControlState.NULL;
		private EnumSet<EditorStateFlag> editorStateFlags = EnumSet.noneOf( EditorStateFlag.class);
		
		public void setComponentState( ComponentState theComponentState) {
			this.componentState = theComponentState;
		}
		
		public void setControlState( ControlState theControlState) {
			this.controlState = theControlState;
		}
	
		public void setFlag( EditorStateFlag theEditorStateFlag) {
			this.editorStateFlags.add( theEditorStateFlag);
		}
		
		public void unsetFlag( EditorStateFlag theEditorStateFlag) {
			this.editorStateFlags.remove( theEditorStateFlag);
		}
		
		public boolean contains( ComponentState theComponentState) {
			return componentState.equals( theComponentState);
		}
		
		public boolean contains( ControlState theControlState) {
			return controlState.equals( theControlState);
		}
		
		public boolean contains( EditorStateFlag theEditorStateFlag) {
			return editorStateFlags.contains( theEditorStateFlag);
		}
	}
	
	private EditorState editorState = new EditorState();
	
	private boolean visible = true;
	private boolean enabled = true;
	private boolean advancedMode = false;
	
	private String title;
	private String description;
	private IParameter<T> parameter;
	private IConfiguration configuration;
	private Set<T> values = new TreeSet<T>();
	private Composite control;
	
	private IEditorLookAndBehaviour lookAndBehaviour;
	
	private Map<String,String> attributes = new HashMap<String, String>();

	
	public AbstractEditor( final String theTitle, final String theDescription) {
		super();
		this.title = theTitle;
		this.description = theDescription;
		setLookAndBehaviour( getDefaultLookAndBehaviour());
		editorState.setComponentState( ComponentState.CONSTRUCTED);
	}
	
	protected Set<T> getValues() {
		return values;
	}
	
	@Override
	public void setEnabled( boolean theEnabledState) {
	  this.enabled = theEnabledState;
	  applySettings();
	}
	
	public void applySettings() {
		if ( editorState.contains( ControlState.CREATED)) {
			
			// Composite.isVisible() behaves strange:
			//  when the parent object is currently not shown, it'll return false.
			//  this is why flag-checking here returns wrong answers.
			
		  // if ( control.isVisible() != visible)
		  	control.setVisible( visible);

		  if ( control.isEnabled() != enabled)
		  	control.setEnabled( enabled);
		  
	  }
	}

	@Override
	public boolean isEnabled() {
	  return hasControl() && control.isEnabled();
	}

	@Override
	public void setVisible( boolean theVisibleState) {
		this.visible = theVisibleState;
	  applySettings();
	}

	@Override
	public boolean isVisible() {
		return hasControl() && control.isVisible();
	}

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setAttribute(String theName, String theValue) {
		attributes.put( theName, theValue);
		
		// handle advanced attribute  
		if ( theName.equalsIgnoreCase( "advanced")) {
			advancedMode = !theValue.equalsIgnoreCase( "false");
		}
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateParameter() {
		editorState.setFlag( EditorStateFlag.UPDATE_PARAMETER);
		reloadParameter();
		editorState.unsetFlag( EditorStateFlag.UPDATE_PARAMETER);
	}
	
  /**
   * reloadParameter is called after the parameter value has changed 
   * to ensure the current value displayed in the editor is the actual parameter value.  
   */
	public abstract void reloadParameter();
	
	public void setAdvancedMode() {
		this.advancedMode = true;
		
	}
	
  public void setNormalMode() {
  	this.advancedMode = false;	
	}
  
  public boolean isAdvancedMode() {
  	return advancedMode;	
	}
  
	@Override
	public void setParameter(IParameter<T> theParameter) {
		if(theParameter == null) {
			throw new IllegalArgumentException( "Parameter provided is null.");
		}
		this.parameter = theParameter;
	}
	
	@Override
	public IParameter<T> getParameter() {
		return parameter;
	}
	
	@Override
	public void setConfiguration(IConfiguration theConfiguration) {
	  this.configuration = theConfiguration;
	}
	
	protected IConfiguration getConfiguration() {
		return configuration;
	}

	protected void loadProvidedValues() {
		if ( configuration != null) {
			Set<IParameterValueProvider> competentValueProviders = configuration.getValueProviders( parameter);
			for (IParameterValueProvider competentValueProvider : competentValueProviders) {
				values.addAll( competentValueProvider.getAvailableValues( parameter));
			}
		}
	}
	
	
	@Override
	public void updateConfig() {
		editorState.setFlag( EditorStateFlag.UPDATE_CONFIG);
    loadProvidedValues();
		editorState.unsetFlag( EditorStateFlag.UPDATE_CONFIG);
	}
	
	
	public void updateControl() {
		editorState.setFlag( EditorStateFlag.UPDATE_CONTROL);
		lookAndBehaviour.doOnChange();
		editorState.unsetFlag( EditorStateFlag.UPDATE_CONTROL);
	}
	
	protected void setLookAndBehaviour(IEditorLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}
	
	public IEditorLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}
	
	protected abstract IEditorLookAndBehaviour getDefaultLookAndBehaviour();
	
	
	@Override
	public final Composite createControl( Composite theParent) {
		editorState.setControlState( ControlState.CREATING);
		control = new Composite( theParent, SWT.None);
	  control.addDisposeListener( new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent theArg0) {
				editorState.setControlState( ControlState.DISPOSED);
			}
		});
		
	  designControl( control); 
	  applySettings();
	  
	  editorState.setControlState( ControlState.CREATED);
	  return control;
	}
	
	protected abstract void designControl( final Composite theControl);
	

	public Composite getControl() {
		return control;
	}
	
	public boolean hasControl() {
		return editorState.contains( ControlState.CREATED);
	}
	
	public IEditorState getState() {
		return editorState;
	}
	
	private boolean setFocusToSomeChild( final Composite theControl) {
		for ( Control ctrl : theControl.getChildren()) {
			ctrl.setFocus();
			if ( ctrl.isFocusControl() && !(ctrl instanceof IMessageContainer))
				return true;
			if ( ctrl instanceof Composite)
				if ( setFocusToSomeChild( (Composite) ctrl))
				  return true;
		}	
		return false;
	}
	
	/**
	 * For performance and specificity reasons overriding this method is recommended.
	 */
	public void setFocus() {
		if ( hasControl()) {	
			control.setFocus();
			if ( !control.isFocusControl())
				setFocusToSomeChild( control);
		}
	}
	
	
	@Override
	public String toString() {
		return "\""+getParameter().getId()+"\"@"+hashCode();
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		editorState.setComponentState( ComponentState.DESTROYING);
		super.finalize();
		editorState.setComponentState( ComponentState.DESTROYED);
	}
	
}


