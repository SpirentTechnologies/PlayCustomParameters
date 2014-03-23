package ttworkbench.play.parameters.ipv6.widgets;

import org.eclipse.swt.graphics.Image;
import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;


public class FibWidget extends CustomWidget {

	private static final String TITLE = "Fib Widget";
	private static final String DESCRIPTION = "Fibonacci sequence tester.";
	private static final Image IMAGE = null;
	
	
	public FibWidget() {
		super( TITLE, DESCRIPTION, IMAGE);
	}


	@Override
	protected IWidgetLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultWidgetLookAndBehaviour();
	}






}
