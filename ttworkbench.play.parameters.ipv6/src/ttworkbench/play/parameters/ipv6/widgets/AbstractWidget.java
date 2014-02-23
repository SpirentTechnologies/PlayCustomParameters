package ttworkbench.play.parameters.ipv6.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public abstract class AbstractWidget implements IWidget {

	private boolean enabled = true;
	private boolean visible = true;
	
	private String title;
	private String description;
	private Image image;
	private Set<IAttribute> attributes;
	private Set<IParameterEditor> editors;
	private Set<IParameterValidator> validators;
	private Set<IParameterValueProvider> valueProvider;
	
	public AbstractWidget( final String theTitle, final String theDescription, final Image theImage) {
		super();
		this.title = theTitle;
		this.description = theDescription;
		this.image = theImage;		
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
	public void setAttributes(Set<IAttribute> theAttributes) {
		this.attributes = theAttributes;
	}

	@Override
	public void setEditors(Set<IParameterEditor> theEditors) {
		editors = theEditors;
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
	public abstract Control createControl(Composite theParent);

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
	
	protected abstract Set<IParameter> filterRelevantParameters( Set<IParameter> theParameters);

}
