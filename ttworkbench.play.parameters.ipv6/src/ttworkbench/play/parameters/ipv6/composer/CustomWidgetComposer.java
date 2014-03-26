package ttworkbench.play.parameters.ipv6.composer;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.ParameterEditorMapper;
import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.validators.IValidatorContext;
import ttworkbench.play.parameters.ipv6.validators.IWithValidatorContext;
import ttworkbench.play.parameters.ipv6.validators.SimpleValidatorContext;
import ttworkbench.play.parameters.ipv6.widgets.CustomWidget;
import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.Data.Relation;

import com.testingtech.ttworkbench.ttman.ManagementPlugin;
import com.testingtech.ttworkbench.ttman.parameters.api.IActionHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;


public class CustomWidgetComposer extends WidgetComposer {
	
	private final Data.Widget widget;
	private final Map<Relation, IParameterValidator> availableValidatorsMap;
	private final LinkedList<Relation> triggerableRelations = new LinkedList<Data.Relation>();

	private static final boolean DEBUG = false; 
	
	public CustomWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap, Data.Widget theWidget, Map<Data.Relation, IParameterValidator> validatorMap) {
		super( theConfigurator, theParametersMap);
		this.widget = theWidget;
		this.availableValidatorsMap = validatorMap;
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
				for(Entry<String, String> attr : dataParameter.getAttributes().entrySet()) {
					editor.setAttribute( attr.getKey(), attr.getValue());
				}
				
				getConfigurator().assign( editor, defaultWidget, parameter);
				
				/*
				 * related validation
				 */
				for(Data.Relation dataRelation : dataParameter.getRelations()) {
					IParameterValidator validator = getValidator(dataRelation);
					if(validator!=null) {
						
						triggerableRelations.add(dataRelation);
						
						// assign the validator to the parameter
						getConfigurator().assign( validator, defaultWidget, parameter);
					}
					else {
						logError( "A validator for \""+theId+"\" could not be resolved: \""+dataRelation.getValidator().getType()+"\"", null);
					}
				}
			}
			else {
				logError("The parameter could not be found: \""+theId+"\".", null);
			}
		}
	}

	
	@Override
	public void resolve() {
		super.resolve();
		
		for(Data.Relation relation : triggerableRelations) {
			IParameterValidator validator = availableValidatorsMap.remove( relation);
			
			if(validator!=null) {
				SimpleValidatorContext context = getValidatorContext(validator);
				Data.RelationPartner[] relationPartners = relation.getRelationPartners();
				
				for(Data.RelationPartner relationPartner : relationPartners) {
	
					Data.Partner partner = relationPartner.getPartner();
					
					boolean msg = relationPartner.isRegisteredForMessages();
					boolean act = relationPartner.isRegisteredForActions();
					
					// set related parameter instance
					if(partner instanceof Data.Parameter) {
						String id = ((Data.Parameter) partner).getId();
						IParameter<?> relatedParameter = getParametersMap().getParameterById( id);
						if(context!=null) {
							context.addParameter( relatedParameter);
						}						

						// set related parameter editor
						Set<IParameterEditor> editors = getConfigurator().getEditors( relatedParameter);
						
						// TODO consider reevaluation: should all editors of a parameter be registered for messages/actions 
						for(IParameterEditor<?> editor : editors) {
							
							// register for messages
							if(msg && editor instanceof IMessageHandler) {
								if(DEBUG) System.out.println("[editorMsg] \""+this.widget.getName()+"\" registered editor "+editor+" to "+validator+".");
								validator.registerForMessages( (IMessageHandler) editor);
							}
							
							// register for actions
							if(act && editor instanceof IActionHandler) {
								if(DEBUG) System.out.println("[editorAct] \""+this.widget.getName()+"\" registered editor "+editor+" to "+validator+".");
								validator.registerForActions( (IActionHandler) editor);
							}
						}
						
					}
					else if(partner instanceof Data.Widget) {
						String name = ((Data.Widget) partner).getName();
						IWidget widget = getWidgetByName( name);
						if(context!=null) {
							context.addWidget( widget);
						}
						
						if(msg && widget instanceof IMessageHandler) {
							if(DEBUG) System.out.println("[widgetMsg] \""+this.widget.getName()+"\" registered widget "+widget+" to "+validator+".");
							validator.registerForMessages( (IMessageHandler) widget);
						}
						if(act && widget instanceof IActionHandler) {
							if(DEBUG) System.out.println("[widgetAct] \""+this.widget.getName()+"\" registered widget "+widget+" to "+validator+".");
							validator.registerForActions( (IActionHandler) widget);
						}
					}
				}
				
			}
		}
	}

	private SimpleValidatorContext getValidatorContext(IParameterValidator theValidator) {
		SimpleValidatorContext context = null;
		if(theValidator instanceof IWithValidatorContext) {
			IValidatorContext rawContext = ( (IWithValidatorContext) theValidator).getContext();
			if(rawContext instanceof SimpleValidatorContext) {
				context = (SimpleValidatorContext) rawContext;
			}
		}
		return context;
	}


	private IParameterValidator getValidator(Data.Relation theDataRelation) {
		IParameterValidator validator = availableValidatorsMap.get( theDataRelation);
		if(validator==null) {
			Class<?> validatorType = theDataRelation.getValidator().getType();
			if(validatorType!=null) {
				try {
					Object validatorRaw = validatorType.newInstance();
					
					if(validatorRaw instanceof IParameterValidator) {
						validator = (IParameterValidator) validatorRaw;
						
						if(validator instanceof IWithValidatorContext) {
							((IWithValidatorContext) validator).setContext(
									new SimpleValidatorContext(
										getConfigurator()
									)
								);
						}
	
						for(Entry<String, String> attribute : theDataRelation.getValidator().getAttributes().entrySet()) {
							validator.setAttribute( attribute.getKey(), attribute.getValue());
						}
						
						availableValidatorsMap.put( theDataRelation, validator);
					}
					else {
						logError( "Could not cast \""+validatorRaw+"\" from type \""+validatorType+"\" to a valid IParameterValidator.", null);
					}
				}
				catch(Exception e) {
					logError( "Could not create instance from class \""+validatorType+"\". Tried to use the default constructor without parameter.", e);
				}
			}
		}
		return validator;
	}

	
	private IWidget getWidgetByName(String name) {
		for(IWidget widget : getConfigurator().getWidgets()) {
			if(widget.getTitle().equals( name)) {
				return widget;
			}
		}
		return null;
	}
	
	private Image getImage(Data.Image theImage) {
		if(theImage!=null && theImage.getPath()!=null) {
			try {
				return new Image( Display.getCurrent(), theImage.getPath());
			}
			catch(Exception e) {
				logError("could not load image: \""+theImage.getPath()+"\".", null);
			}
		}
		return null;
	}

	private void logError(String theString, Exception e) {
		ManagementPlugin
		.getSharedInstance()
		.eclipseLog(theString,  e);
	}

	
}
