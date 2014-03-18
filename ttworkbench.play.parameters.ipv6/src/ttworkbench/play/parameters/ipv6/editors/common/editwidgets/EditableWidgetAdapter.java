package ttworkbench.play.parameters.ipv6.editors.common.editwidgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

@Deprecated
public class EditableWidgetAdapter {
	
	private static final Map<Class<? extends Widget>,IEditableWidgetAdapter> supportedWidgets = new HashMap<Class<? extends Widget>,IEditableWidgetAdapter>();
	private static final IEditableWidgetAdapter[] widgetAdapters = {
	  new TextAdapter(),
	  new SpinnerAdapter()
	};
	
	static {
		for (IEditableWidgetAdapter widgetAdapter : widgetAdapters) {
			supportedWidgets.put( widgetAdapter.getSupportedWidget(), widgetAdapter);
		
		}
	}
	
	

	public static void setTextForWidget( final String theText, final Widget theWidget) throws UnsupportedWidgetException {
		if ( !isWidgetSupported( theWidget))
			throw new UnsupportedWidgetException();
		supportedWidgets.get( theWidget.getClass()).setTextForWidget( theText, theWidget);
	}
	
	public static String getTextFromWidget( final Widget theWidget) throws UnsupportedWidgetException {
		if ( !isWidgetSupported( theWidget))
			throw new UnsupportedWidgetException();
		return supportedWidgets.get( theWidget.getClass()).getTextFromWidget( theWidget);
	}
	
	public static String getTextByEvent( final Event theEvent) throws UnsupportedWidgetException, UnhandledEventException {
		if ( !isWidgetSupported( theEvent.widget))
			throw new UnsupportedWidgetException();
		return supportedWidgets.get( theEvent.widget.getClass()).getTextByEvent( theEvent);
	}

	private static boolean isWidgetSupported( Widget theWidget) {
		return supportedWidgets.containsKey( theWidget.getClass());
	}
	

}
