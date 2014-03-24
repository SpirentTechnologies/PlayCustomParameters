package ttworkbench.play.parameters.settings.data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import ttworkbench.play.parameters.settings.Data;

public class ParameterImpl implements Data.Parameter {

	private final boolean descriptionVisible;
	private final Data.Relation[] relations;
	private final String id;
	private final String description;
	private final Object defaultValue;
	private final Map<String, String> attributes;

	public ParameterImpl(boolean thisDescriptionVisible,
			Data.Relation[] thisRelations,
			String thisId, String thisDescription, Object thisDefaultValue,
			Map<String, String> thisAttributes) {
		
		this.descriptionVisible = thisDescriptionVisible;
		this.relations = thisRelations;
		this.id = thisId;
		this.description = thisDescription;
		this.defaultValue = thisDefaultValue;
		this.attributes = thisAttributes;
	}
	
	
	public ParameterImpl(Data.Parameter parameter) {
		this(
			parameter.isDescriptionVisible(),
			parameter.getRelations(),
			parameter.getId(),
			parameter.getDescription(),
			parameter.getDefaultValue(),
			new LinkedHashMap<String, String>(parameter.getAttributes())
		);
	}

	public boolean isDescriptionVisible() {
		return descriptionVisible;
	}
	
	public Data.Relation[] getRelations() {
		return relations;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime
				* result
				+ ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (descriptionVisible ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(relations);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParameterImpl other = (ParameterImpl) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (descriptionVisible != other.descriptionVisible)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(relations, other.relations))
			return false;
		return true;
	}

}
