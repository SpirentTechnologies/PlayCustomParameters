package ttworkbench.play.parameters.settings.data;

import java.util.Map;

import ttworkbench.play.parameters.settings.Data;

public class EditorTypeMappingImpl implements Data.EditorTypeMapping {

	private final Map<String, String> attributes;
	private final String expression;
	private final Class<?> type;

	public EditorTypeMappingImpl(String expression, Class<?> type,
			Map<String, String> attributes) {
		this.expression = expression;
		this.type = type;
		this.attributes = attributes;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public String getTypeExpression() {
		return this.expression;
	}

	public Class<?> getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((type == null) ? 0 : type.toString().hashCode());
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
		EditorTypeMappingImpl other = (EditorTypeMappingImpl) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.toString().equals(other.type.toString()))
			return false;
		return true;
	}
	
}
