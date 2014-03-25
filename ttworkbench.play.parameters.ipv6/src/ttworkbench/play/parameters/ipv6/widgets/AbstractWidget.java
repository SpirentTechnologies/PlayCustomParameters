package ttworkbench.play.parameters.ipv6.widgets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.components.design.ComponentState;
import ttworkbench.play.parameters.ipv6.components.design.ControlState;
import ttworkbench.play.parameters.ipv6.components.design.ControlStateFlag;
import ttworkbench.play.parameters.ipv6.components.design.EditorState;
import ttworkbench.play.parameters.ipv6.components.design.WidgetState;
import ttworkbench.play.parameters.ipv6.components.design.WidgetStateFlag;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public abstract class AbstractWidget implements IWidget {

  private WidgetState widgetState = new WidgetState();
 
	private String title;
	private String description;
	private Image image;
	private Set<IAttribute> attributes;
	private List<IParameterEditor<?>> editors = new LinkedList<IParameterEditor<?>>();
	private Set<IParameterValidator> validators;
	private Set<IParameterValueProvider> valueProvider;
	private Composite control;
	
	public AbstractWidget( final String theTitle, final String theDescription, final Image theImage) {
		super();
		this.title = theTitle;
		this.description = theDescription;
		widgetState.setComponentState( ComponentState.CONSTRUCTED);
	}
	
	@Override
	public void setEnabled( boolean theEnabledState) {
		widgetState.flag( ControlStateFlag.ENABLED, theEnabledState);
	}
	
	@Override
	public boolean isEnabled() {
		return widgetState.isset( ControlStateFlag.ENABLED);
	}
	
	@Override
	public void setVisible( boolean theVisibleState) {
		widgetState.flag( ControlStateFlag.VISIBLE, theVisibleState);
	}
	
	@Override
	public boolean isVisible() {
		return widgetState.isset( ControlStateFlag.VISIBLE);
	}
	
	@Override
	public void setSelected(boolean theSelectedState) {
		widgetState.flag( WidgetStateFlag.SELECTED, theSelectedState);
	}
	
	@Override
	public boolean isSelected() {
		return widgetState.isset( WidgetStateFlag.SELECTED);
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
	public Image getImage() {
		return image;
	}
	
	@Override
	public void setAttributes(Set<IAttribute> theAttributes) {
		this.attributes = theAttributes;
	
	  // handle advanced attribute  
		for (IAttribute attr : theAttributes) {
			if ( attr.getName().equalsIgnoreCase( "advanced")) {
				widgetState.flag( WidgetStateFlag.ADVANCED_MODE, !attr.getValue().equalsIgnoreCase( "false"));
			}
		}
	}
	
	@Override
	public void addEditor( IParameterEditor theEditor) {
		editors.add( theEditor);	
	}
	
	@Override
	public void setValidators(Set<IParameterValidator> theValidator) {
		validators = theValidator;
	}
	
	@Override
	public void setValueProviders(Set<IParameterValueProvider> theValueProvider) {
		valueProvider = theValueProvider;
	}

	
	@Override
	public String toString() {
		return "\""+getTitle()+"\"@"+hashCode();
	}
	
	protected Set<IAttribute> getAttributes() {
		return attributes;
	}	
	
	protected List<IParameterEditor<?>> getEditors() {
		return editors;
	}
	
	/**
	 * Retrieves a copy of the internal managed editor list 
	 * @return
	 */
	public List<IParameterEditor<?>> acquireEditors() {
		return new ArrayList<IParameterEditor<?>>(editors);
	}

	protected Set<IParameterValidator> getValidators() {
		return validators;
	}

	protected Set<IParameterValueProvider> getValueProvider() {
		return valueProvider;
	}
	
	public void setAdvancedMode() {
		widgetState.setFlag( WidgetStateFlag.ADVANCED_MODE);
	}
	
  public void setNormalMode() {
  	widgetState.unsetFlag( WidgetStateFlag.ADVANCED_MODE);
	}
  
  public boolean isAdvancedMode() {
  	return widgetState.isset( WidgetStateFlag.ADVANCED_MODE);	
	}
  
  public WidgetState getState() {
  	return widgetState;
  }
  
	public boolean hasControl() {
		return widgetState.isset( ControlState.CREATED);
	}
	
	public Composite getControl() {
		return control;
	}
	
	protected abstract void designControl( final Composite theControl);
	
	@Override
	public final Composite createControl( Composite theParent) {
		widgetState.setControlState( ControlState.CREATING);
		control = new Composite( theParent, SWT.None);
	  control.addDisposeListener( new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent theArg0) {
				widgetState.setControlState( ControlState.DISPOSED);
			}
		});
		
	  designControl( control); 
	  applySettings();
	  
	  widgetState.setControlState( ControlState.CREATED);
	  return control;
	}
	
	public void applySettings() {
		if ( widgetState.isset( ControlState.CREATED)) {
			
		  // if ( control.isVisible() != widgetState.isset( ControlStateFlag.VISIBLE))
		  	control.setVisible( widgetState.isset( ControlStateFlag.VISIBLE));

		  if ( control.isEnabled() != widgetState.isset( ControlStateFlag.ENABLED))
		  	control.setEnabled( widgetState.isset( ControlStateFlag.ENABLED));
		  
	  }
	}

	
	
	@Override
	protected void finalize() throws Throwable {
		widgetState.setComponentState( ComponentState.DESTROYING);
		super.finalize();
		widgetState.setComponentState( ComponentState.DESTROYED);
	}

}
