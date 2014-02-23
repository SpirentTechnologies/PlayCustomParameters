package ttworkbench.ttman.parameters.impl;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IModelComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidgetFactory;

public class DefaultWidgetFactory implements IWidgetFactory {
	
	private IModelComposer modelComposer;

	public DefaultWidgetFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
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
	public IWidget create() {
		return new IWidget() {

			protected static final String TITLE = "Default Widget";

			@Override
			public void setVisible(boolean theVisible) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setEnabled(boolean theEnable) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isVisible() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setValueProviders(Set<IParameterValueProvider> theValueProvider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setValidators(Set<IParameterValidator> theValidator) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setEditors(Set<IParameterEditor> theEditors) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttributes(Set<IAttribute> theAttributes) {
				// TODO Auto-generated method stub

			}

			@Override
			public String getTitle() {
				return TITLE;
			}

			@Override
			public Image getImage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Control createControl(Composite theParent) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public void setModelComposer(IModelComposer theModelComposer) {
		this.modelComposer = theModelComposer;
	}

	@Override
	public IModelComposer getModelComposer() {
		return modelComposer;
	}

}
