package ttworkbench.play.parameters.ipv6.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
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

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
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
	private Set<IParameterEditor> editors = new HashSet<IParameterEditor>();
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
	}
	
	protected Set<IAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void addEditor( IParameterEditor theEditors) {
		editors.add( theEditors);
		
	}
	
	protected Set<IParameterEditor> getEditors() {
		return editors;
	}

	@Override
	public void setValidators(Set<IParameterValidator> theValidator) {
		validators = theValidator;
	}
	
	protected Set<IParameterValidator> getValidators() {
		return validators;
	}

	@Override
	public void setValueProviders(Set<IParameterValueProvider> theValueProvider) {
		valueProvider = theValueProvider;
	}

	protected Set<IParameterValueProvider> getValueProvider() {
		return valueProvider;
	}
	
	@Override
	public abstract Control createControl(Composite theParent);



}
