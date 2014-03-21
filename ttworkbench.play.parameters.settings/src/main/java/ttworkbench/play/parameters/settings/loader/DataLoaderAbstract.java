package ttworkbench.play.parameters.settings.loader;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.data.ParameterImpl;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;
import ttworkbench.play.parameters.settings.exceptions.ParameterDefinitionNotFoundException;
import ttworkbench.play.parameters.settings.exceptions.ValidatorClassNotFoundException;
import ttworkbench.play.parameters.settings.exceptions.ValidatorDefinitionNotFoundException;

public abstract class DataLoaderAbstract {

	private static boolean INTERRUPT_EXCEPTION = false;
	private static boolean USE_UNDEFINED_PARAMETERS = true;
	
	private static final Vector<Exception> errors = new Vector<Exception>();
	
	private Map<String, Data.Validator> validators;
	private Map<String, Data.Parameter> parameters;
	private Map<String, Data.Widget> widgets;

	
	public static Vector<Exception> getErrors() {
		return errors;
	}
	


	
	/*
	 * getters
	 */
	
	protected Class<?> getType(String type) throws ValidatorClassNotFoundException {
		try {
			return getClass().getClassLoader().loadClass(type);
		} catch (ClassNotFoundException e) {
			ValidatorClassNotFoundException exception = new ValidatorClassNotFoundException("Could not find java class definition for "+type+". Please ensure class path settings and change the default class loader if needed.");
			errors.add(exception);
			if(INTERRUPT_EXCEPTION) {
				throw exception;
			}
			return null;
		}
	}

	

	protected Data.Validator getValidator(String validatorId) throws ValidatorDefinitionNotFoundException {
		Data.Validator validator = this.validators.get(validatorId);
		if(validator==null) {
			ValidatorDefinitionNotFoundException exception = new ValidatorDefinitionNotFoundException("Could not find validator by id "+validatorId);
			errors.add(exception);
			if(INTERRUPT_EXCEPTION) {
				throw exception;
			}
		}
		return validator;
	}

	public Data.Widget getWidget(String widgetName) {
		return widgets.get(widgetName);
	}

	public Data.Parameter getParameter(String parameterId) throws ParameterDefinitionNotFoundException {
		Data.Parameter parameter = this.parameters.get(parameterId);
		if(parameter==null) {
			if(USE_UNDEFINED_PARAMETERS) {
				final String thisId = parameterId;
				parameter = new ParameterImpl(
						false,
						new Data.Relation[0],
						thisId,
						"",
						null,
						new HashMap<String, String>());
			}
			else {
				ParameterDefinitionNotFoundException exception = new ParameterDefinitionNotFoundException("Could not find parameter by id "+parameterId);
				errors.add(exception);
				if(INTERRUPT_EXCEPTION) {
					throw exception;
				}
			}
		}
		return parameter;
	}
	

	public void addError(ParameterConfigurationException exception) throws ParameterConfigurationException {
		errors.add(exception);
		if(INTERRUPT_EXCEPTION) {
			throw exception;
		}
	}
	

	protected void setParameters(Map<String, Data.Parameter> parameters) {
		this.parameters = parameters;
	}

	protected void setValidators(Map<String, Data.Validator> validators) {
		this.validators = validators;
	}
	public void setWidgets(Map<String, Data.Widget> widgets) {
		this.widgets = widgets;
	}
}


