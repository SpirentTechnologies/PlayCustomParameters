package ttworkbench.play.parameters.ipv6.validators;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;

public interface IValidatorContext {

	/**
	 * TODO
	 * @return
	 */
	IParameter<?>[] getParameters();

	/**
	 * TODO
	 * @param parameter
	 * @return
	 */
	<T extends Value> IParameterEditor<T>[] getEditorsForParameter( IParameter<T> parameter);

	/**
	 * TODO
	 * @return
	 */
	IWidget[] getWidgets();
}
