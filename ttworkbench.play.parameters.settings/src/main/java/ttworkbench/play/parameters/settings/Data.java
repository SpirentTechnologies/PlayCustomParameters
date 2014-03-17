package ttworkbench.play.parameters.settings;

import java.util.Map;

public interface Data {

	Widget[] getWidgets();
	
	
	public interface Widget {
		String getName();
		String getDescription();
		Image getImage();
		Parameter[] getParameters();
	}
	
	public interface Image {
		String getPath();
	}
	
	public interface Parameter {
		String getId();
		Object getDefaultValue();
		String getDescription();
		boolean isDescriptionVisible();
		
		Validator[] getValidators();
		Relation[] getRelations();
	}
	
	public interface Relation {
		Validator getValidator();
		Parameter getParameterRelated();
	}
	
	public interface Validator {
		Class<?> getType();
		Map<String, String> getAttributes();
	}
	
}
