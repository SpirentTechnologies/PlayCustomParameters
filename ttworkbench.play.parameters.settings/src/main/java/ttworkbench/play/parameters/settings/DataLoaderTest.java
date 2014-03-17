package ttworkbench.play.parameters.settings;


import ttworkbench.play.parameters.settings.Data.Parameter;
import ttworkbench.play.parameters.settings.Data.Relation;
import ttworkbench.play.parameters.settings.Data.RelationPartner;
import ttworkbench.play.parameters.settings.Data.Validator;
import ttworkbench.play.parameters.settings.Data.Widget;
import ttworkbench.play.parameters.settings.loader.DataLoader;

public class DataLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Data data = DataLoader.getInstance();
			
			for(Widget widget : data.getWidgets()) {
				System.out.println("Widget: "+widget.getName());
				System.out.println(" Description: "+widget.getDescription());
				System.out.println(" Image-Path: "+widget.getImage().getPath());
				System.out.println(" Widget-Attributes: "+widget.getAttributes());
				System.out.println(" Parameters:");
				
				for(Parameter parameter : widget.getParameters()) {
					System.out.println("  + "+parameter.getId());
					System.out.println("    Description: "+parameter.getDescription());
					System.out.println("    Description-Visible: "+parameter.isDescriptionVisible());
					System.out.println("    Parameter-Attributes: "+parameter.getAttributes());
					System.out.println("    Default: "+parameter.getDefaultValue());
					
					System.out.println("    Relations: "+parameter.getRelations().length);
					for(Relation relation : parameter.getRelations()) {
						System.out.println("     - Validator-Class: "+relation.getValidator().getType());
						System.out.println("       Validator-Attributes: "+relation.getValidator().getAttributes());
						System.out.println("       Validator-Notify: "+relation.getValidator().isWidgetNotified());
						
						System.out.println("       Related Parameters: "+relation.getRelationPartners().length);
						for(RelationPartner relationPartner : relation.getRelationPartners()) {
							System.out.println("        - Widget: "+ relationPartner.getParameter().getId());
							System.out.println("          Registered for Messages: "+relationPartner.isRegisteredForMessages());
							System.out.println("          Registered for Actions: "+relationPartner.isRegisteredForActions());	
						}
					}

					System.out.println("    Validators: "+parameter.getValidators().length);
					for(Validator validator : parameter.getValidators()) {
						System.out.println("     - Validator-Class: "+validator.getType());
						System.out.println("       Validator-Attributes: "+validator.getAttributes());
						System.out.println("       Validator-Notify: "+validator.isWidgetNotified());
					}
						
				}
			}
			
			
			
			System.out.println();
			System.out.println("----");
			System.out.println("Reported Errors:");
			for(Exception e : DataLoader.getErrors()) {
				System.out.println(" ["+e.getClass().getSimpleName()+"] "+e.getMessage());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
