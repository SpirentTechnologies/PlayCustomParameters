package ttworkbench.play.parameters.ipv6.composer;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.ParameterEditorMapper;
import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.widgets.CustomWidget;
import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.Data.Validator;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class CustomWidgetComposer extends WidgetComposer {
	
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
				IParameterEditor<?> editor = ParameterEditorMapper.getInstance().getEditor(parameter);
				getConfigurator().assign( editor, defaultWidget, parameter);
				
				
				
				for(Data.Validator dataValidator : dataParameter.getValidators()) {
					IParameterValidator validator = getValidator(dataValidator);
					if(validator!=null) {
						
						// register the validator to the editor
						if(editor instanceof IMessageHandler) {
							validator.registerForMessages( (IMessageHandler) editor);
						}
						
						// assign the validator to the parameter
						getConfigurator().assign( validator, defaultWidget, parameter);
					}
					else {
						logError( "A validator for \""+theId+"\" could not be resolved: \""+dataValidator.getType()+"\"");
					}
				}
			}
			else {
				logError("The parameter could not be found: \""+theId+"\".");
			}
		}
		
		
		
	}

	private IParameterValidator getValidator(Validator theDataValidator) {
		IParameterValidator validator = null;
		Class<?> validatorType = theDataValidator.getType();
		if(validatorType!=null) {
			try {
				Object validatorRaw = validatorType.newInstance();
				if(validatorRaw instanceof IParameterValidator) {
					validator = (IParameterValidator) validatorRaw;
				}
				else {
					logError( "Could not cast \""+validatorRaw+"\" from type \""+validatorType+"\" to a valid IParameterValidator.");
				}
			}
			catch(Exception e) {
				logError( "Could not create instance from class \""+validatorType+"\". Tried to use constructor without parameters.");
			}
		}
		return validator;
	}

	private Image getImage(Data.Image theImage) {
		if(theImage!=null && theImage.getPath()!=null) {
			try {
				return new Image( Display.getCurrent(), theImage.getPath());
			}
			catch(Exception e) {
				logError("could not load image: \""+theImage.getPath()+"\".");
			}
		}
		return null;
	}

	private void logError(String theString) {
		// TODO logger
		System.err.println(theString);		
	}

	
}
