package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.graphics.Image;

import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;

public class EnumWidget extends CustomWidget{
	
	private static final String TITLE = "Enum Widget";
	private static final String DESCRIPTION = "Enum editor";
	private static final Image IMAGE = null;

	public EnumWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
		// TODO Auto-generated method stub
		return new DefaultWidgetLookAndBehaviour();
	}

}
