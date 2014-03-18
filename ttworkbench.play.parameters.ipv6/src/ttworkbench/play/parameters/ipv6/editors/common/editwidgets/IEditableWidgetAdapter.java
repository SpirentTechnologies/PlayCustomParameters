package ttworkbench.play.parameters.ipv6.editors.common.editwidgets;

import org.apache.commons.lang.UnhandledException;
import org.eclipse.swt.widgets.Event;

@Deprecated
public interface IEditableWidgetAdapter<T> {

	void setTextForWidget( String theText, T theWidget);

	String getTextFromWidget( T theWidget);

	String getTextByEvent( Event theEvent) throws UnhandledEventException;
	
	Class<T> getSupportedWidget();
}
