package ttworkbench.play.parameters.ipv6.validators;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

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
	<T> IParameterEditor<T>[] getEditorsForParameter( IParameter<T> parameter);
}
