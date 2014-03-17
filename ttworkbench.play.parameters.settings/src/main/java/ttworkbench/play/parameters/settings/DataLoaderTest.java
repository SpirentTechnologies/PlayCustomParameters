package ttworkbench.play.parameters.settings;


import ttworkbench.play.parameters.settings.Data.Parameter;
import ttworkbench.play.parameters.settings.Data.Relation;
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
				System.out.println(" Parameters:");
				
				for(Parameter parameter : widget.getParameters()) {
					System.out.println("  + "+parameter.getId());
					System.out.println("    Description: "+parameter.getDescription());
					System.out.println("    Description-Visible: "+parameter.isDescriptionVisible());
					System.out.println("    Default: "+parameter.getDefaultValue());
					
					System.out.println("    Relations: "+parameter.getRelations().length);
					for(Relation relation : parameter.getRelations()) {
						System.out.println("     - Parameter: "+relation.getParameterRelated().getId());
						System.out.println("       Validator-Class: "+relation.getValidator().getType());
						System.out.println("       Validator-Attributes: "+relation.getValidator().getAttributes());
					}

					System.out.println("    Validators: "+parameter.getValidators().length);
					for(Validator validator : parameter.getValidators()) {
						System.out.println("     - Validator-Class: "+validator.getType());
						System.out.println("       Validator-Attributes: "+validator.getAttributes());
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
