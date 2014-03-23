package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.graphics.Image;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;

public class MacWidget extends CustomWidget{
	
	private static final String TITLE = "MAC Widget";
	private static final String DESCRIPTION = "MAC addresses editor";
	private static final Image IMAGE = null;

	public MacWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultWidgetLookAndBehaviour();
	}
}
