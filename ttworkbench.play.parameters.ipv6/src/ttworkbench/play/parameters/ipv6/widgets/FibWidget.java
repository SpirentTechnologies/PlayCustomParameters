package ttworkbench.play.parameters.ipv6.widgets;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ttworkbench.play.parameters.ipv6.customize.DefaultWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IWidgetLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;


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
