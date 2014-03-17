package ttworkbench.play.parameters.settings.loader;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;


import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.Data.RelationPartner;
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
		private class Rel {
			public Rel(String id, boolean msg, boolean act) {
				this.id = id;
				this.msg = msg;
				this.act = act;
			}
			private String id;
			private boolean msg;
			private boolean act;
		}
		private class RelParameter extends Rel {
			public RelParameter(String id, boolean msg, boolean act) {
				super(id, msg, act);
			}
		}
		private class RelWidget extends Rel {
			public RelWidget(String name, boolean msg, boolean act) {
				super(name, msg, act);
			}
		}

		private Data.Validator validator;
		private LinkedList<Rel> relParameters = new LinkedList<Rel>();
		private Data.RelationPartner[] parameters = null;
		private boolean widgetNotify = false;;

		public LazyRelation(Data.Validator validator) {
			this.validator = validator;
		}
		public Data.Validator getValidator() {
			return validator;
		}

		public Data.RelationPartner[] getRelationPartners() {
			if(parameters==null) {
				try {
					parameters = new Data.RelationPartner[relParameters.size()];
					for(int i=0; i<parameters.length; i++) {
						Rel rel = relParameters.get(i);
						final boolean thisRegisteredForMessages = rel.msg;
						final boolean thisRegisteredForActions = rel.act;
						final Data.Parameter thisParameter = getParameter(rel.id);
						parameters[i] = new RelationPartner() {
							
							public boolean isRegisteredForMessages() {
								return thisRegisteredForMessages;
							}
							
							public boolean isRegisteredForActions() {
								return thisRegisteredForActions;
							}
							
							public Data.Parameter getParameter() {
								return thisParameter;
							}
						};
					}
				} catch (ParameterDefinitionNotFoundException e) {
					errors.add(e);
				}
			}
			return parameters;
		}
		public void addRelatedParameter(String parId, boolean parMsg, boolean parAct) {
			relParameters.add(new RelParameter(parId, parMsg, parAct));
		}
		public void addRelatedWidget(String widgetName, boolean parMsg, boolean parAct) {
			relParameters.add(new RelWidget(widgetName, parMsg, parAct));
		}
		public int getNumParametersRelated() {
			return relParameters.size();
		}
		public void setWidgetNotified(boolean validatorNotify) {
			this.widgetNotify = validatorNotify;
		}
		public boolean isWidgetNotified() {
			return widgetNotify;
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

					public Map<String, String> getAttributes() {
						return new HashMap<String, String>();
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
