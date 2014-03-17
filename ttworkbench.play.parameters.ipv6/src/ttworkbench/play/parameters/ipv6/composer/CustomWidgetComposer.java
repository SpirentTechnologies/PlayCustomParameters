package ttworkbench.play.parameters.ipv6.composer;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.editors.IntegerEditor;
import ttworkbench.play.parameters.ipv6.widgets.CustomWidget;
import ttworkbench.play.parameters.settings.Data;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class CustomWidgetComposer extends WidgetComposer {

	private static final String TYPE_MATCH_INTEGER = "^(UInt\\d{0,2}|Int\\d{0,2})$";
	
	private Data.Widget widget;

	public CustomWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap, Data.Widget theWidget) {
		super( theConfigurator, theParametersMap);
		this.widget = theWidget;
	}
	
	@Override
	public void compose() {
		
		String widgetName = widget.getName();
		String widgetDescription = widget.getDescription();
		Image widgetImage = getImage(widget.getImage());

		IWidget defaultWidget = new CustomWidget(widgetName, widgetDescription, widgetImage) {
			
			@Override
			protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
				return new DefaultWidgetLookAndBehaviour();
			}
		};
		
		
		getConfigurator().addWidget( defaultWidget);


		
		for(Data.Parameter dataParameter : widget.getParameters()) {
			String theId = dataParameter.getId();
			IParameter<?> parameter = getParametersMap().getParameterById( theId);
			
			if(parameter!=null) {
				IParameterEditor<?> editor = new DefaultEditor();
				
				if(parameter.getType().matches( TYPE_MATCH_INTEGER)) {
					editor = new IntegerEditor();
				}
				getConfigurator().assign( editor, defaultWidget, parameter);
			}
			else {
				// TODO logging
				System.err.println("parameter not found: \""+theId+"\".");
			}
		}
		
		
		
	}

	private Image getImage(Data.Image theImage) {
		if(theImage!=null && theImage.getPath()!=null) {
			try {
				return new Image( Display.getCurrent(), theImage.getPath());
			}
			catch(Exception e) {
				// TODO logging
				System.err.println("could not load image: \""+theImage.getPath()+"\".");
				e.printStackTrace();
			}
		}
		return null;
	}

	
}
