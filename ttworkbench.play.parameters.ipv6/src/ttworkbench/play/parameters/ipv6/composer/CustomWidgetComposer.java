package ttworkbench.play.parameters.ipv6.composer;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.ParameterEditorMapper;
import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.validators.RelatedValidator;
import ttworkbench.play.parameters.ipv6.validators.RelatedValidator.RelationKey;
import ttworkbench.play.parameters.ipv6.widgets.CustomWidget;
import ttworkbench.play.parameters.settings.Data;
import com.testingtech.ttworkbench.ttman.parameters.api.IActionHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class CustomWidgetComposer extends WidgetComposer {
	
	private Data.Widget widget;
	
	private HashMap<RelatedValidator, Data.Relation> triggeredRelations = new HashMap<RelatedValidator, Data.Relation>();
	private HashMap<Data.Validator, IParameterValidator> validatorMapping = new HashMap<Data.Validator, IParameterValidator>();

	
	
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
				
				
				/*
				 * self validation 
				 */
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
				
				
				/*
				 * related validation
				 */
				for(Data.Relation dataRelation : dataParameter.getRelations()) {
					IParameterValidator validator = getValidator(dataRelation.getValidator());
					if(validator!=null) {
						
						if(validator instanceof RelatedValidator) {
							triggerRelations((RelatedValidator) validator, dataRelation);
						}
						
						// assign the validator to the parameter
						getConfigurator().assign( validator, defaultWidget, parameter);
					}
					else {
						logError( "A validator for \""+theId+"\" could not be resolved: \""+dataRelation.getValidator().getType()+"\"");
					}
				}
			}
			else {
				logError("The parameter could not be found: \""+theId+"\".");
			}
		}
		
		triggerRelations();
		
		
		
	}

	
	private void triggerRelations() {
		for(Entry<RelatedValidator, Data.Relation> triggerEntry : triggeredRelations.entrySet()) {
			RelatedValidator validator = triggerEntry.getKey();
			Data.Relation relation = triggerEntry.getValue();
			Data.RelationPartner[] parameter = relation.getRelationPartners();
			
			RelationKey[] keys = validator.getRelationKeys();
			for(int i=0; i<keys.length && i<parameter.length; i++) {
				String id = parameter[i].getParameter().getId();
				boolean msg = parameter[i].isRegisteredForMessages();
				boolean act = parameter[i].isRegisteredForActions();
				
				// set related parameter instance
				IParameter<?> relatedParameter = getParametersMap().getParameterById( id);
				validator.addParameter( keys[i], relatedParameter);
				
				// set related parameter editor
				Set<IParameterEditor> editors = getConfigurator().getEditors( relatedParameter);
				if(editors.size()>0) {
					IParameterEditor<?> editor = editors.iterator().next();
					
					validator.addEditor( keys[i], editor);
					

					// register for messages
					if(msg && editor instanceof IMessageHandler) {
						validator.registerForMessages( (IMessageHandler) editor);
					}
					if(relation.getValidator().isWidgetNotified() && widget instanceof IMessageHandler) {
						validator.registerForMessages( (IMessageHandler) widget);
					}
					
					// register for actions
					if(act && editor instanceof IActionHandler) {
						validator.registerForActions( (IActionHandler) editor);
					}
					if(relation.getValidator().isWidgetNotified() && widget instanceof IActionHandler) {
						validator.registerForActions( (IActionHandler) widget);
					}
				}
				
			}
		}
	}

	private synchronized void triggerRelations(RelatedValidator theValidator, Data.Relation theRelation) {
			triggeredRelations.put( theValidator, theRelation);
	}
	
	private IParameterValidator getValidator(Data.Validator theDataValidator) {
		IParameterValidator validator = validatorMapping.get( theDataValidator);
		if(validator==null) {
			Class<?> validatorType = theDataValidator.getType();
			if(validatorType!=null) {
				try {
					Object validatorRaw = validatorType.newInstance();
					if(validatorRaw instanceof IParameterValidator) {
						validator = (IParameterValidator) validatorRaw;
	
						for(Entry<String, String> attribute : theDataValidator.getAttributes().entrySet()) {
							validator.setAttribute( attribute.getKey(), attribute.getValue());
						}
						
						validatorMapping.put( theDataValidator, validator);
					}
					else {
						logError( "Could not cast \""+validatorRaw+"\" from type \""+validatorType+"\" to a valid IParameterValidator.");
					}
				}
				catch(Exception e) {
					logError( "Could not create instance from class \""+validatorType+"\". Tried to use constructor without parameters.");
				}
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
