package ttworkbench.play.parameters.settings.loader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.exceptions.MalformedParameterConfigurationException;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;

public class DataLoaderXML extends DataLoaderAbstract {

	private static boolean DEFAULT_RELATED_PARAMETER_MESSAGE = false;
	private static boolean DEFAULT_RELATED_PARAMETER_ACTION = false;
	private static boolean DEFAULT_DESCRIPTION_VISIBLE = true;
	
	private String file;
	private Data.Widget[] widgets;
	
	
	
	public DataLoaderXML(String filename) {
		try {
			String fn = DataLoaderXML.class.getResource(filename).toString();
			this.file = new URI(fn).getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}


	public synchronized Data.Widget[] getWidgets() throws ParameterConfigurationException {
		if(widgets==null) {
			loadFromSettingsFile();
		}
		return widgets;
	}

	private void loadFromSettingsFile() throws ParameterConfigurationException {
		try {
			XMLConfiguration config = new XMLConfiguration();
			config.setFileName(file);
			config.setSchemaValidation(true);
			config.load();

			Map<String, Data.Validator> validators = loadValidators(config);
			setValidators(validators);
			
			Map<String, Data.Parameter> parameters = loadParameters(config);
			setParameters(parameters);
			
			widgets = loadWidgets(config);
			
		} catch (ConfigurationException e) {
			MalformedParameterConfigurationException exception = new MalformedParameterConfigurationException("Configuration could not be read: "+e.getMessage(), e);
			addError(exception);
			widgets = new Data.Widget[0];
		}
	}


	/*
	 * validators
	 */

	private Map<String, Data.Validator> loadValidators(XMLConfiguration config) throws ParameterConfigurationException {
		HashMap<String, Data.Validator> result = new HashMap<String, Data.Validator>();
		
		for(HierarchicalConfiguration val : config.configurationsAt("validators.validator")) {
			String id = val.getString("[@id]");
			final Class<?> type = getType(val.getString("classpath"));
			final Map<String, String> attributes = getAttributesFromConfig(val);
			result.put(id, new Data.Validator() {
				
				public Class<?> getType() {
					return type;
				}

				public Map<String, String> getAttributes() {
					return attributes;
				}
			});
		}
		return result;
	}
	

	/*
	 * parameters
	 */

	private Map<String, Data.Parameter> loadParameters(XMLConfiguration config) throws ParameterConfigurationException {
		HashMap<String, Data.Parameter> result = new HashMap<String, Data.Parameter>();
		for(HierarchicalConfiguration val : config.configurationsAt("parameters.parameter")) {

			LinkedList<Data.Relation> relations = new LinkedList<Data.Relation>();
			LinkedList<Data.Validator> validators = new LinkedList<Data.Validator>();
			loadRelations(val, relations, validators);
			
			final String thisId = val.getString("[@id]");
			final boolean thisDescriptionVisible = val.getBoolean("description[@visible]", DEFAULT_DESCRIPTION_VISIBLE);
			final Data.Relation[] thisRelations = relations.toArray(new Data.Relation[0]);
			final Data.Validator[] thisValidators = validators.toArray(new Data.Validator[0]);
			final String thisDescription = val.getString("description");
			final String thisDefaultValue = val.getString("defaultValue");
			final Map<String, String> thisAttributes = getAttributesFromConfig(val);
			
			result.put(thisId, new Data.Parameter() {
				
				public boolean isDescriptionVisible() {
					return thisDescriptionVisible;
				}
				
				public Data.Relation[] getRelations() {
					return thisRelations;
				}
				
				public Data.Validator[] getValidators() {
					return thisValidators;
				}
				
				public String getId() {
					return thisId;
				}
				
				public String getDescription() {
					return thisDescription;
				}
				
				public Object getDefaultValue() {
					return thisDefaultValue;
				}

				public Map<String, String> getAttributes() {
					return thisAttributes;
				}
			});
		}
		return result;
	}

	private void loadRelations(HierarchicalConfiguration config, Collection<Data.Relation> relations, Collection<Data.Validator> validators) throws ParameterConfigurationException {

		for(HierarchicalConfiguration val : config.configurationsAt("validator")) {
			String validatorId = val.getString("[@id]");
			Map<String, String> attrs_new = getAttributesFromConfig(val);
			
			final Data.Validator validatorDefault = getValidator(validatorId);
			if(validatorDefault==null) {
				continue;
			}
			
			final Map<String, String> attrs = new HashMap<String, String>(validatorDefault.getAttributes());
			attrs.putAll(attrs_new);

			final Data.Validator validator = new Data.Validator() {
					
				public Class<?> getType() {
					return validatorDefault.getType();
				}
				
				public Map<String, String> getAttributes() {
					return attrs;
				}
			};
			
			
			LazyRelation relation = new LazyRelation(validator);
			for(HierarchicalConfiguration editor : val.configurationsAt("relation")) {
				String parId = editor.getString("[@parameterId]");
				boolean parMsg = editor.getBoolean("[@message]", DEFAULT_RELATED_PARAMETER_MESSAGE);
				boolean parAct = editor.getBoolean("[@action]", DEFAULT_RELATED_PARAMETER_ACTION);
				relation.addRelatedParameter(parId, parMsg, parAct);
			}
			if(relation.getNumParametersRelated()>0) {
				relations.add(relation);
			}
			else {
				validators.add(validator);
			}
		}
	}


	/*
	 * Widgets
	 */
	
	private Data.Widget[] loadWidgets(XMLConfiguration config) throws ParameterConfigurationException {
		LinkedList<Data.Widget> result = new LinkedList<Data.Widget>();

		for(HierarchicalConfiguration val : config.configurationsAt("widgets.widget")) {
			final String thisName = val.getString("[@name]");
			final String thisDescription = val.getString("description");
			final String thisImagePath = val.getString("image[@path]");

			final List<Data.Parameter> paras = new LinkedList<Data.Parameter>();
			for(Object parameterId : val.getList("editor[@parameterId]")) {
				Data.Parameter parameter = getParameter(parameterId.toString());
				if(parameter!=null) {
					paras.add(parameter);
				}
			}
			
			final Data.Parameter[] thisParameters = paras.toArray(new Data.Parameter[0]);
			final Map<String, String> thisAttributes = getAttributesFromConfig(val);
			
			result.add(new Data.Widget() {

				public String getName() {
					return thisName;
				}

				public String getDescription() {
					return thisDescription;
				}

				public Data.Image getImage() {
					return new Data.Image() {
						public String getPath() {
							return thisImagePath;
						};
					};
				}

				public Data.Parameter[] getParameters() {
					return thisParameters;
				}

				public Map<String, String> getAttributes() {
					return thisAttributes;
				}
				
			});
		}
		return result.toArray(new Data.Widget[0]);
	}


	private Map<String, String> getAttributesFromConfig(HierarchicalConfiguration config) {
		HashMap<String, String> result = new HashMap<String,String>();
		
		for(HierarchicalConfiguration val : config.configurationsAt("attribute")) {
			String value = val.getString("");
			String key = val.getString("[@name]");
			result.put(key, value);
		}
		return result;
	}
	
}
