package ttworkbench.play.parameters.ipv6;

import java.util.List;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposerFactory;

public class ConfigurationComposerFactory implements
		IConfigurationComposerFactory {

	public ConfigurationComposerFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setTitle(String theTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String theDescription) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConfigurationComposer create() {
		return new IPv6ConfigurationComposer();
	}

}
