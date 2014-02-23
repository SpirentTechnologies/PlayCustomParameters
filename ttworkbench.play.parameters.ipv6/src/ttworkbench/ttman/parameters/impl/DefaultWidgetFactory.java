package ttworkbench.ttman.parameters.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.ModelComposer;
import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IModelComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidgetFactory;

public class DefaultWidgetFactory implements IWidgetFactory {
	
	private IModelComposer modelComposer = ModelComposer.getSingletonInstance();
	private String title;
	private String description;
	private List<IAttribute> attributes = new ArrayList<IAttribute>();

	@Override
	public void setTitle( String theTitle) {
	  this.title = theTitle;	
	}

	@Override
	public String getTitle() {
	  return title;
	}

	@Override
	public void setDescription(String theDescription) {
	  this.description = theDescription;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<IAttribute> getAttributes() {
	  return attributes ;
	}

	@Override
	public IWidget create() {
		IWidget defaultWidget = new DefaultWidget();
		
		IModelComposer modelComposer = getModelComposer();
		Set<IParameter> allParameters = modelComposer.getParameterModel().getParameters();
		for ( IParameter parameter : allParameters) {
			modelComposer.assign( new DefaultEditor(), parameter, defaultWidget);
		}
		return defaultWidget;
	}

	@Override
	public void setModelComposer(IModelComposer theModelComposer) {
		this.modelComposer = theModelComposer;
	}

	@Override
	public IModelComposer getModelComposer() {
		return modelComposer;
	}

}
