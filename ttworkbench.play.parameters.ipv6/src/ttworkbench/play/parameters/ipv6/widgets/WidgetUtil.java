package ttworkbench.play.parameters.ipv6.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.Globals;
import ttworkbench.play.parameters.ipv6.common.IParameterControl;
import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import org.eclipse.ui.part.WorkbenchPart;

import com.testingtech.ttworkbench.core.ui.CommonImages;
import com.testingtech.ttworkbench.core.ui.ComposedImageDescriptor;
import com.testingtech.ttworkbench.core.ui.CommonImages.CommonImage;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class WidgetUtil {
	

	public static boolean selectNextParentTabOfControl(Control theControl) {
		Control currentControl = theControl;
		Control lastControl = null;
		while ( currentControl != null &&
				    !(currentControl instanceof CTabFolder)) {
			lastControl = currentControl;
			currentControl = currentControl.getParent();
		}
		
	  if ( currentControl != null &&
	  		 currentControl instanceof CTabFolder) {
	    CTabFolder tabFolder = (CTabFolder) currentControl;
	    CTabItem[] items = tabFolder.getItems();
	    for (CTabItem item : items) {
	    	if ( item.getControl() == lastControl) {
	    		tabFolder.setSelection( item);
	    		return true;
	    	}
	    }
	  }
	  
	  return false;
	}
	
	public static boolean isGrantParentOfChild( final Control theParent, final Control theChild) {
		Control currentControl = theChild;
		while ( currentControl != null && currentControl != theParent) {
			currentControl = currentControl.getParent();
		}
		return currentControl == theParent;
	}
	
	public static Map<AbstractWidget, List<AbstractEditor<?>>> getWidgetToEditorsOfParameterMap( IParameterControl<? extends Control,?> theParameterControl) {
		Map<AbstractWidget, List<AbstractEditor<?>>> result = new HashMap<AbstractWidget, List<AbstractEditor<?>>>();
		Set<IParameterEditor> parameterEditors = Globals.getConfiguration().getEditors( theParameterControl.getParameter());
		List<AbstractEditor<?>> widgetEditors;
		
		Map<AbstractWidget, List<AbstractEditor<?>>> widgetToEditorsMap = getWidgetToEditorsMap();
		for ( AbstractWidget widget : widgetToEditorsMap.keySet()) {
			widgetEditors = widgetToEditorsMap.get( widget);
			if ( !Collections.disjoint( widgetEditors, parameterEditors)) {
				widgetEditors.retainAll( parameterEditors);
				result.put( widget, widgetEditors);
			}
		}
		return result;
	}
	
	

	/**
	 * retrieves a mapping of AbstractWidgets to the AbstractEditors on them. 
	 * @return
	 */
	public static Map<AbstractWidget, List<AbstractEditor<?>>> getWidgetToEditorsMap() {
		Map<AbstractWidget, List<AbstractEditor<?>>> result = new HashMap<AbstractWidget, List<AbstractEditor<?>>>();
		List<AbstractEditor<?>> abstractEditors;
		List<IParameterEditor<?>> editors;

		if ( Globals.hasConfiguration()) {
			Set<IWidget> widgets = Globals.getConfiguration().getWidgets();
			for (IWidget widget : widgets) {
				if ( widget instanceof AbstractWidget) {
					abstractEditors = new ArrayList<AbstractEditor<?>>();
					editors = ((AbstractWidget) widget).acquireEditors();
					for (IParameterEditor<?> editor : editors) {
						if ( editor instanceof AbstractEditor)
							abstractEditors.add( (AbstractEditor<?>) editor);
					}
					result.put( (AbstractWidget) widget, abstractEditors);
				}
			}
		}
		return result;
	}

	
	public static CTabFolder getTabFolder() {
		Set<IWidget> widgets = Globals.getConfiguration().getWidgets();
		for (IWidget widget : widgets) {
			if ( widget instanceof AbstractWidget) {
				AbstractWidget abstractWidget = (AbstractWidget) widget;
			  if ( abstractWidget.hasControl()) {
			  	Control widgetControl = abstractWidget.getControl();
			    return (CTabFolder) widgetControl.getParent();
			  }
			}
		}
		return null;
	}
	
}
