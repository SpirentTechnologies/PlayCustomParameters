package ttworkbench.play.parameters.ipv6.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.MessagePopup;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public abstract class AbstractEditor<T> implements IParameterEditor<T> {

	public enum ComponentState {
		INSTANTIATED, CREATED, DESTROYED
	}
	
	private ComponentState componentState = ComponentState.INSTANTIATED;
	
	private boolean visible = true;
	private boolean enabled = true;
	
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
		if ( componentState == ComponentState.CREATED) {	
		  if ( control.isVisible() != visible)
		  	control.setVisible( visible);
		  if ( control.isEnabled() != enabled)
		  	control.setEnabled( enabled);
	  }
	}

	@Override
	public boolean isEnabled() {
	  return componentState == ComponentState.CREATED && enabled;
	}

	@Override
	public void setVisible( boolean theVisibleState) {
		this.visible = theVisibleState;
	  applySettings();
	}

	@Override
	public boolean isVisible() {
		return componentState == ComponentState.CREATED && visible;
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
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void setParameter(IParameter<T> theParameter) {
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
      loadProvidedValues();
      // validate();
	}
	
	public void updateControl() {
		lookAndBehaviour.doOnChange();
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
	  control = new Composite( theParent, SWT.None);
		
	  componentState = ComponentState.CREATED;
		control.addDisposeListener( new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent theArg0) {
				componentState = ComponentState.DESTROYED;
			}
		});
		
	  designControl( control); 
	  applySettings();
	  
	  return control;
	}
	
	protected abstract void designControl( final Composite theControl);
	

	public Composite getControl() {
		return control;
	}
	
	public ComponentState getState() {
		return componentState;
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
	 * For performance and specificity reasons override this method is recommended.
	 */
	public void setFocus() {
		if ( componentState == ComponentState.CREATED) {	
			control.setFocus();
			if ( !control.isFocusControl())
				setFocusToSomeChild( control);
		}
	}
	
}


