package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.graphics.Image;

import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;

public class IPWidget extends CustomWidget {
	private static final String TITLE = "IP Widget";
	private static final String DESCRIPTION = "IP addresses editor";
	private static final Image IMAGE = null;

	public IPWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultWidgetLookAndBehaviour();
	}
}
