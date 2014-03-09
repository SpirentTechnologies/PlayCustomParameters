package ttworkbench.play.parameters.ipv6.editors;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public abstract class AbstractEditor<T> implements IParameterEditor<T> {

	private boolean enabled = true;
	private boolean visible = true;
	
	private String title;
	private String description;
	private IAttribute attribute;
	private IParameter<T> parameter;
	private IConfiguration configuration;
	private Set<T> values = new TreeSet<T>();
	
	private Listener controlChangedListener = null;
	private IEditorLookAndBehaviour lookAndBehaviour;
	
	
	
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
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setVisible( boolean theVisibleState) {
		this.visible = theVisibleState;
	}

	@Override
	public boolean isVisible() {
		return visible;
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
		// TODO Auto-generated method stub
		
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
		if ( controlChangedListener != null)
	  	controlChangedListener.handleEvent( new Event());
	}
	
	public void setControlChangedListener(Listener theControlChangedListener) {
		this.controlChangedListener = theControlChangedListener;
	}
	
	protected void setLookAndBehaviour(IEditorLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}
	
	public IEditorLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}
	
	protected abstract IEditorLookAndBehaviour getDefaultLookAndBehaviour();
	
	

}
