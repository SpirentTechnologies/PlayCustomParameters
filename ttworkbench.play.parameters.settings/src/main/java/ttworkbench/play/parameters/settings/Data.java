package ttworkbench.play.parameters.settings;

import java.util.Map;

public interface Data {

	Widget[] getWidgets();
	
	EditorTypeMapping[] getTypeEditorMappings();
	
	public interface Widget extends WithAttributes, Partner {
		String getName();
		String getDescription();
		Image getImage();
		Parameter[] getParameters();
	}
	
	public interface Image {
		String getPath();
	}
	
	public interface Parameter extends WithAttributes, Partner {
		String getId();
		Object getDefaultValue();
		String getDescription();
		boolean isDescriptionVisible();
		
		Relation[] getRelations();
	}

	public interface Relation {
		Validator getValidator();
		RelationPartner[] getRelationPartners();
	}
	
	public interface RelationPartner {
		Partner getPartner();
		boolean isRegisteredForMessages();
		boolean isRegisteredForActions();
	}
	
	public interface Validator extends WithAttributes {
		Class<?> getType();
	}
	
	public interface WithAttributes {
		Map<String, String> getAttributes();
	}
	
	public interface EditorTypeMapping extends WithAttributes {
		String getTypeExpression();
		Class<?> getType();
	}
	
	public interface Partner {
	}
}
