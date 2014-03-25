package ttworkbench.play.parameters.ipv6.widgets;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public abstract class AbstractWidget implements IWidget {

	private boolean enabled = true;
	private boolean visible = true;
  private boolean advancedMode = false;
	
	private String title;
	private String description;
	private Image image;
	private Set<IAttribute> attributes;
	private List<IParameterEditor<?>> editors = new LinkedList<IParameterEditor<?>>();
	private Set<IParameterValidator> validators;
	private Set<IParameterValueProvider> valueProvider;
	
	public AbstractWidget( final String theTitle, final String theDescription, final Image theImage) {
		super();
		this.title = theTitle;
		this.description = theDescription;
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
	public Image getImage() {
		return image;
	}
	
	@Override
	public void setAttributes(Set<IAttribute> theAttributes) {
		this.attributes = theAttributes;
	
	  // handle advanced attribute  
		for (IAttribute attr : theAttributes) {
			if ( attr.getName().equalsIgnoreCase( "advanced")) {
				advancedMode = !attr.getValue().equalsIgnoreCase( "false");
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

	protected Set<IParameterValidator> getValidators() {
		return validators;
	}

	protected Set<IParameterValueProvider> getValueProvider() {
		return valueProvider;
	}
	
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
	public abstract Control createControl(Composite theParent);

}
