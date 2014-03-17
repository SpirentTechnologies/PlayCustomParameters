package ttworkbench.play.parameters.ipv6.composer;

import java.util.Collection;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.editors.IntegerEditor;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public class DefaultWidgetComposer extends WidgetComposer {

	private static final String TYPE_MATCH_INTEGER = "^(UInt\\d{0,2}|Int\\d{0,2})$";

	public DefaultWidgetComposer( IConfigurator theConfigurator, ParameterMap theParameters) {
		super( theConfigurator, theParameters);
	}

	@Override
	public void compose() {

		IWidget defaultWidget = new DefaultWidget();
		getConfigurator().addWidget( defaultWidget);

		// TODO: replace demo composition 
		Collection<IParameter> parameters = getParametersMap().getAllParameters();
		for (IParameter parameter : parameters) {
			IParameterEditor editor = new DefaultEditor();
			if(parameter.getType().matches( TYPE_MATCH_INTEGER)) {
				editor = new IntegerEditor();
			}
			getConfigurator().assign( editor, defaultWidget, parameter);
		}
	}

}