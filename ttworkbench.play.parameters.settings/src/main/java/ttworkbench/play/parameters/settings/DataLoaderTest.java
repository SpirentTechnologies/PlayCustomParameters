package ttworkbench.play.parameters.settings;


import ttworkbench.play.parameters.settings.Data.EditorTypeMapping;
import ttworkbench.play.parameters.settings.Data.Parameter;
import ttworkbench.play.parameters.settings.Data.Relation;
import ttworkbench.play.parameters.settings.Data.RelationPartner;
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
						
						System.out.println("       Relation-Partners: "+relation.getRelationPartners().length);
						for(RelationPartner relationPartner : relation.getRelationPartners()) {

							if(relationPartner.getPartner() instanceof Widget) {
								System.out.println("        - Widget: \""+ ((Widget) relationPartner.getPartner()).getName()+"\"");
							}
							else if(relationPartner.getPartner() instanceof Parameter) {
								System.out.println("        - Parameter: "+ ((Parameter) relationPartner.getPartner()).getId());
							}
							System.out.println("          Registered for Messages: "+relationPartner.isRegisteredForMessages());
							System.out.println("          Registered for Actions: "+relationPartner.isRegisteredForActions());	
						}
					}						
				}
			}
			

			System.out.println("Type-Mappings: "+data.getTypeEditorMappings().length);
			for(EditorTypeMapping mapping : data.getTypeEditorMappings()) {
					System.out.println(" - Editor-Class: "+mapping.getType());
					System.out.println("   Editor-Attributes: "+mapping.getAttributes());
					System.out.println("   Mapping Type: "+mapping.getTypeExpression());
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
