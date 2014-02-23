package ttworkbench.ttman.parameters.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.osgi.util.NLS;

import com.testingtech.ttworkbench.core.util.EclipseExtensionRegistyUtil;
import com.testingtech.ttworkbench.ttman.ManagementPlugin;
import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IBaseFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IModelComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IModelController;
import com.testingtech.ttworkbench.ttman.parameters.api.IModelControllerFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterBaseFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditorFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterModel;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidatorFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProviderFactory;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.impl.ModelController;

public class ModelComposer implements IModelComposer {

	private static ModelComposer singleton = null;
	
	public static final String PARAMETER_FRAMEWORK = "ttworkbench.play.parameters.ipv6.parameter_framework"; //$NON-NLS-1$

	private IModelController modelController;
	private final Set<IParameterEditorFactory> editorFactories = new HashSet<IParameterEditorFactory>();
	private final Set<IParameterValueProviderFactory> valueProviderFactories = new HashSet<IParameterValueProviderFactory>();
	private final Set<IParameterValidatorFactory> parameterValidatorFactories = new HashSet<IParameterValidatorFactory>();
	
	public ModelComposer() {
		super();
		registerModelController();
	}
	
	private void registerModelController() {
		//get the registered extensions
	    IExtension[] extensions = EclipseExtensionRegistyUtil.getExtensions(PARAMETER_FRAMEWORK);
	    for (IExtension extension : extensions) {
	    	IConfigurationElement[] configElements = extension.getConfigurationElements();
	    	for (IConfigurationElement configurationElement : configElements) {
	    		String elementName = configurationElement.getName();
	    		if ( "modelcontroller".equals( elementName))
	    			try{
	    				Object execExtension = configurationElement.createExecutableExtension("class"); //$NON-NLS-1$
	    				if ( execExtension instanceof IModelControllerFactory) {
	    					IModelControllerFactory factory = (IModelControllerFactory)execExtension;
	    					factory.setTitle( configurationElement.getAttribute("name"));
	    					factory.setDescription(configurationElement.getAttribute("description"));
	    					this.modelController = factory.create();
	    					// TODO read & set attributes
	    				}
	    			} catch (CoreException e) {
	    				ManagementPlugin.getSharedInstance().eclipseLog(
	    						NLS.bind("Error occurred while initializing {0} ''{1}'' id:{2}", new Object[]{
	    								elementName, configurationElement.getName(), configurationElement.getNamespaceIdentifier()}), e);
	    			}
	    	}
	    }
	}

	

	@Override
	public IParameterModel getParameterModel() {
		return modelController.getParameterModel();
	}

	@Override
	public Set<IWidget> getWidgets() {
		return modelController.getWidgets();
	}

	@Override
	public IWidget getDefaultWidget() {
		return modelController.getDefaultWidget();
	}

	@Override
	public Set<IParameterValueProvider> getValueProviders( IParameter theParameter) {
		return modelController.getValueProviders( theParameter);
	}

	@Override
	public Set<IParameterEditor> getEditors(IParameter theParameter) {
		return modelController.getEditors( theParameter);
	}

	@Override
	public Set<IParameterValidator> getValidators(IParameter theParameter) {
		return modelController.getValidators( theParameter);
	}

	@Override
	public Set<IParameterEditorFactory> getAvailableEditors(
			IParameter theParameter) {
		// TODO ??? return new HashSet<IParameterEditorFactory>( editorFactories);
		return editorFactories;
	}

	@Override
	public Set<IParameterValueProviderFactory> getAvailableValueProviders(
			IParameter theParameter) {
		return valueProviderFactories;
	}

	@Override
	public Set<IParameterValidatorFactory> getAvailableValidators(
			IParameter theParameter) {
		return parameterValidatorFactories;
	}

	@Override
	public void assign(IParameterEditor theEditor, IParameter theParameter,
			IWidget theWidget) {
		//theWidget.setEditors( editors)
		// TODO Auto-generated method stub

	}

	@Override
	public void assign(IParameterValueProvider theValueProvider,
			List<IParameter> theParameters, IWidget theWidget) {
		// TODO Auto-generated method stub

	}

	@Override
	public void assign(IParameterValidator theValidator,
			List<IParameter> theParameters, IWidget theWidget) {
		// TODO Auto-generated method stub

	}
	
	private void initWidgets() {
		Set<IWidget> widgets = getWidgets();
		for (IWidget widget : widgets) {
			
		}
	}
	
	 public static ModelComposer getSingletonInstance() {
		  if ( singleton == null)
			singleton = new ModelComposer();  
		  return singleton;
	  }

}
