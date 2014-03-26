package ttworkbench.play.parameters.ipv6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import ttworkbench.play.parameters.ipv6.common.Globals;
import ttworkbench.play.parameters.ipv6.composer.CustomWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.DefaultWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.EnumWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.FibWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.IPWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.IWidgetComposer;
import ttworkbench.play.parameters.ipv6.composer.MacWidgetComposer;
import ttworkbench.play.parameters.settings.Data;
import ttworkbench.play.parameters.settings.Data.Relation;
import ttworkbench.play.parameters.settings.exceptions.ParameterConfigurationException;
import ttworkbench.play.parameters.settings.loader.DataLoader;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;

public class IPv6ConfigurationComposer implements IConfigurationComposer {

	@Override
	// TODO refactor: rename method to "compose()" ?
	public void createWidgets(IConfigurator theConfigurator) {
		Globals.setConfiguration( theConfigurator);
		
		ParameterMap parametersMap = new ParameterMap( theConfigurator);
		if (parametersMap.isEmpty())
			return;

		// first added widget will be set automatically as default widget.
		List<IWidgetComposer> widgetComposers = new ArrayList<IWidgetComposer>();

		// default preset widgets
		widgetComposers.add( new DefaultWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new FibWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new MacWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new IPWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new EnumWidgetComposer( theConfigurator, parametersMap));
		
		
		// custom widget configuration
		widgetComposers.addAll( getCustomWidgetComposers(theConfigurator, parametersMap));


		theConfigurator.beginConfigure();
		for (IWidgetComposer widgetComposer : widgetComposers) {
			try {
				widgetComposer.compose();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (IWidgetComposer widgetComposer : widgetComposers) {
			try {
				widgetComposer.resolve();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		theConfigurator.endConfigure();
	}

	
	
	
	private LinkedList<IWidgetComposer> getCustomWidgetComposers(IConfigurator theConfigurator, ParameterMap theParametersMap) {
		LinkedList<IWidgetComposer> customs = new LinkedList<IWidgetComposer>();
		try {
			Data.Widget[] widgets = DataLoader.getInstance().getWidgets();
			Map<Relation, IParameterValidator> validatorMap = new ConcurrentHashMap<Data.Relation, IParameterValidator>();
			for (Data.Widget widget : widgets) {
				customs.add( new CustomWidgetComposer( theConfigurator, theParametersMap, widget, validatorMap));
			}
			
			if(widgets.length<1) {
				throw new ParameterConfigurationException("No widgets have been found.");
			}
		}
		catch (ParameterConfigurationException e) {
			String msg = e.getMessage();
			int i=0;
			for(Exception e1 : DataLoader.getErrors()) {
				msg += "\n["+(++i)+"] "+e1.getMessage()+"\n";
				
				int j=0;
				for(Throwable cause = e1.getCause(); cause!=null; cause = cause.getCause()) {
					msg += "\n["+i+"."+(++j)+"] " + cause.getMessage()+"\n";
				}
			}
			
			MessageBox dialog = new MessageBox(Display.getDefault().getActiveShell(), SWT.ERROR | SWT.OK);
			dialog.setText("A problem occured while loading widget and parameters settings.");
			dialog.setMessage(msg);
			dialog.open(); 
		}
		return customs;
	}
}
