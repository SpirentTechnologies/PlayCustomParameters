package ttworkbench.play.parameters.settings.loader;


import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;

public class DataLoader extends DataLoaderAbstract {
	
	private static final String defaultFileName = "/widget.parameters.xml";

	private static Data data;
	private static Integer BLOCKER = 1;	
	

	public static Data getInstance() throws ParameterConfigurationException {
		if (data == null) {
			instantiateData();
		}
		return data;
	}

	private static void instantiateData() throws ParameterConfigurationException {
		synchronized (BLOCKER) {
			if (data == null) {
				DataLoaderXML dataLoader = new DataLoaderXML(defaultFileName);
				final Data.Widget[] widgets = dataLoader.getWidgets();
				final Data.EditorTypeMapping[] editorTypeMappings = dataLoader.getEditorTypeMappings();
				data = new Data() {
					public Widget[] getWidgets() {
						return widgets;
					}

					public EditorTypeMapping[] getTypeEditorMappings() {
						return editorTypeMappings;
					}
				};
			}
		}
	}
	
}
