package ttworkbench.play.parameters.settings.data;

import java.util.Arrays;
import java.util.Map;

import ttworkbench.play.parameters.settings.Data;

public class WidgetImpl implements Data.Widget {
	
	private final String name;
	private final String description;
	private final String imagePath;
	private final Map<String, String> attributes;
	private final Data.Parameter[] parameters;

	public WidgetImpl(String name, String description, String imagePath, Data.Parameter[] parameters, Map<String, String> attributes) {
		this.name = name;
		this.description = description;
		this.imagePath = imagePath;
		this.parameters = parameters;
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Data.Image getImage() {
		return new Data.Image() {
			public String getPath() {
				return imagePath;
			};
			@Override
			public int hashCode() {
				return imagePath.hashCode();
			}
			@Override
			public boolean equals(Object obj) {
				if(obj instanceof Data.Image) {
					return imagePath.equals(((Data.Image) obj).getPath());
				}
				return super.equals(obj);
			}
		};
	}

	public Data.Parameter[] getParameters() {
		return parameters;
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
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameters);
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
		WidgetImpl other = (WidgetImpl) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(parameters, other.parameters))
			return false;
		return true;
	}
	
	
}
