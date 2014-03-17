package ttworkbench.play.parameters.settings.loader;


import java.util.Map;
import java.util.Vector;


import ttworkbench.play.parameters.settings.Data;
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

	
	public static Vector<Exception> getErrors() {
		return errors;
	}
	


	protected class LazyRelation implements Data.Relation {

		private Data.Validator validator;
		private String parameterId;
		private Data.Parameter parameter;

		public LazyRelation(Data.Validator validator, String parameterId) {
			this.validator = validator;
			this.parameterId = parameterId;
		}
		public Data.Validator getValidator() {
			return validator;
		}

		public Data.Parameter getParameterRelated() {
			if(parameter==null) {
				try {
					parameter = getParameter(parameterId);
				} catch (ParameterDefinitionNotFoundException e) {
					errors.add(e);
				}
			}
			return parameter;
		}
		
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


	protected Data.Parameter getParameter(String parameterId) throws ParameterDefinitionNotFoundException {
		Data.Parameter parameter = this.parameters.get(parameterId);
		if(parameter==null) {
			if(USE_UNDEFINED_PARAMETERS) {
				final String thisId = parameterId;
				parameter = new Data.Parameter() {
					
					public boolean isDescriptionVisible() {
						return false;
					}
					
					public Data.Validator[] getValidators() {
						return new Data.Validator[0];
					}
					
					public Data.Relation[] getRelations() {
						return new Data.Relation[0];
					}
					
					public String getId() {
						return thisId;
					}
					
					public String getDescription() {
						return "";
					}
					
					public Object getDefaultValue() {
						return null;
					}
				};
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
	

	protected void addError(ParameterConfigurationException exception) throws ParameterConfigurationException {
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
}
