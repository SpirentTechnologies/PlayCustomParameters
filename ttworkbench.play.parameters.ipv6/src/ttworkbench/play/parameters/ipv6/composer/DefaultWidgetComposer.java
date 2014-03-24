package ttworkbench.play.parameters.ipv6.composer;

import java.util.Collection;

import ttworkbench.play.parameters.ipv6.ParameterEditorMapper;
import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class DefaultWidgetComposer extends WidgetComposer {

	public DefaultWidgetComposer( IConfigurator theConfigurator, ParameterMap theParameters) {
		super( theConfigurator, theParameters);
	}

	@Override
	public void compose() {
		IWidget defaultWidget = new DefaultWidget();
		getConfigurator().addWidget( defaultWidget);

		Collection<IParameter<?>> parameters = getParametersMap().getAllParameters();
		for (IParameter<?> parameter : parameters) {
			IParameterEditor<?> editor = ParameterEditorMapper.getInstance().getEditor( parameter);
			getConfigurator().assign( editor, defaultWidget, parameter);
		}
	}

}