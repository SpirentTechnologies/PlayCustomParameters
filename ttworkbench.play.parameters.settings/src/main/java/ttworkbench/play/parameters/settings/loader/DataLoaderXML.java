package ttworkbench.play.parameters.settings.loader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.data.EditorTypeMappingImpl;
import ttworkbench.play.parameters.settings.data.LazyRelationImpl;
import ttworkbench.play.parameters.settings.data.ParameterImpl;
import ttworkbench.play.parameters.settings.data.ValidatorImpl;
import ttworkbench.play.parameters.settings.data.WidgetImpl;
import ttworkbench.play.parameters.settings.exceptions.MalformedParameterConfigurationException;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;

public class DataLoaderXML extends DataLoaderAbstract {

	private static boolean DEFAULT_RELATED_PARAMETER_MESSAGE = false;
	private static boolean DEFAULT_RELATED_PARAMETER_ACTION = false;
	private static boolean DEFAULT_DESCRIPTION_VISIBLE = true;
	
	private String file;
	private Data.Widget[] widgets;
	private Data.EditorTypeMapping[] editorTypeMappings;
	
	
	
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


	public Data.EditorTypeMapping[] getEditorTypeMappings() throws ParameterConfigurationException {
		if(editorTypeMappings==null) {
			loadFromSettingsFile();
		}
		return editorTypeMappings;
	}

	private void loadFromSettingsFile() throws ParameterConfigurationException {
		try {
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.setFileName(file);
			config.setSchemaValidation(true);
			config.load();

			Map<String, Data.Validator> validators = loadValidators(config);
			setValidators(validators);
			
			Map<String, Data.Parameter> parameters = loadParameters(config);
			setParameters(parameters);
			
			Map<String, Data.Widget> widgets = loadWidgets(config);
			setWidgets(widgets);
			
			editorTypeMappings = loadEditorTypeMappings(config);
			
			this.widgets = widgets.values().toArray(new Data.Widget[0]);
			
		} catch (ConfigurationException e) {
			MalformedParameterConfigurationException exception = new MalformedParameterConfigurationException("Configuration could not be read: "+e.getMessage(), e);
			addError(exception);
			widgets = new Data.Widget[0];
			editorTypeMappings = new Data.EditorTypeMapping[0];
		}
	}


	/*
	 * validators
	 */


	private Map<String, Data.Validator> loadValidators(XMLConfiguration config) throws ParameterConfigurationException {
		HashMap<String, Data.Validator> result = new HashMap<String, Data.Validator>();
		
		for(HierarchicalConfiguration val : config.configurationsAt("validators.validator")) {
			String id = val.getString("[@id]");
			Class<?> type = getType(val.getString("classpath"));
			Map<String, String> attributes = getAttributesFromConfig(val);
			result.put(id, new ValidatorImpl(type, attributes));
		}
		return result;
	}
	

	/*
	 * parameters
	 */

	private Map<String, Data.Parameter> loadParameters(XMLConfiguration config) throws ParameterConfigurationException {
		HashMap<String, Data.Parameter> result = new HashMap<String, Data.Parameter>();
		for(HierarchicalConfiguration val : config.configurationsAt("parameters.parameter")) {

			LinkedList<Data.Relation> relations = loadRelations(val);
			
			String thisId = val.getString("[@id]");
			
			result.put(thisId, new ParameterImpl(
					val.getBoolean("description[@visible]", DEFAULT_DESCRIPTION_VISIBLE),
					relations.toArray(new Data.Relation[0]),
					thisId,
					val.getString("description"),
					val.getString("defaultValue"),
					getAttributesFromConfig(val)
				));
		}
		return result;
	}

	private LinkedList<Data.Relation> loadRelations(HierarchicalConfiguration config) throws ParameterConfigurationException {
		LinkedList<Data.Relation> result = new LinkedList<Data.Relation>();

		for(HierarchicalConfiguration val : config.configurationsAt("validator")) {
			String validatorId = val.getString("[@id]");
			Map<String, String> attrs_new = getAttributesFromConfig(val);
			
			Data.Validator validatorDefault = getValidator(validatorId);
			if(validatorDefault==null) {
				continue;
			}
			
			Map<String, String> attrs = new HashMap<String, String>(validatorDefault.getAttributes());
			attrs.putAll(attrs_new);

			Data.Validator validator = new ValidatorImpl(
					validatorDefault.getType(),
					attrs);
			
			
			LazyRelationImpl relation = new LazyRelationImpl(this, validator);
			for(HierarchicalConfiguration editor : val.configurationsAt("relation")) {
				String parId = editor.getString("[@parameterId]");
				String widgetName = editor.getString("[@widgetName]");
				boolean msg = editor.getBoolean("[@message]", DEFAULT_RELATED_PARAMETER_MESSAGE);
				boolean act = editor.getBoolean("[@action]", DEFAULT_RELATED_PARAMETER_ACTION);
				if(parId!=null) {
					relation.addRelatedParameter(parId, msg, act);
				}
				else if(widgetName!=null) {
					relation.addRelatedWidget(widgetName, msg, act);
				}
			}
			
			result.add(relation);
		}
		return result;
	}


	/*
	 * Widgets
	 */
	
	private Map<String, Data.Widget> loadWidgets(XMLConfiguration config) throws ParameterConfigurationException {
		Map<String, Data.Widget> result = new HashMap<String, Data.Widget>();

		for(HierarchicalConfiguration val : config.configurationsAt("widgets.widget")) {
			List<Data.Parameter> paras = new LinkedList<Data.Parameter>();
			for(Object parameterId : val.getList("editor[@parameterId]")) {
				Data.Parameter parameter = getParameter(parameterId.toString());
				if(parameter!=null) {
					paras.add(parameter);
				}
			}
			
			String name = val.getString("[@name]");

			result.put(name,
				new WidgetImpl(
					name,
					val.getString("description"),
					val.getString("image[@path]"),
					paras.toArray(new Data.Parameter[0]),
					getAttributesFromConfig(val)));
		}
		return result;
	}

	
	/*
	 * Value Type <-> Editor Class Mappings
	 */

	private Data.EditorTypeMapping[] loadEditorTypeMappings(XMLConfiguration config) throws ParameterConfigurationException {
		LinkedList<Data.EditorTypeMapping> result = new LinkedList<Data.EditorTypeMapping>();

		for(HierarchicalConfiguration val : config.configurationsAt("types.editor")) {
			Class<?> type = getType(val.getString("[@classpath]"));
			Map<String, String> attrs = getAttributesFromConfig(val);
			for(Object expression : val.getList("expression")) {
				result.add(new EditorTypeMappingImpl(
						expression.toString(),
						type,
						attrs));
			}
		}
		
		return result.toArray(new Data.EditorTypeMapping[0]);
	}
	

	/*
	 * misc
	 */
	
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
