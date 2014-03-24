package ttworkbench.play.parameters.ipv6.common;

import org.eclipse.swt.widgets.Control;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public interface IParameterControl<C extends Control,P> {

	C getControl();
	
	IParameter<P> getParameter();
	
}
