package ttworkbench.play.parameters.ipv6.validators;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public abstract class ContextualValidator extends AbstractValidator implements IWithValidatorContext {
	private IValidatorContext context;

	public ContextualValidator( String theTitle, String theDescription) {
		super( theTitle, theDescription);
	}

	public void setContext(IValidatorContext theContext) {
		this.context = theContext;
	}
	
	@SuppressWarnings("unchecked")
	public <T> IParameter<T> getParameter( int index) {
		return (IParameter<T>) getContext().getParameters()[index];
	}

	public <T> IParameterEditor<T>[] getEditors( int index) {
		IParameter<T> parameter = getParameter( index);
		return getContext().getEditorsForParameter( parameter);
	}
	
	public IValidatorContext getContext() {
		return context;
	}
	
	@Override
	public int hashCode() {
		// TODO + context
		return super.hashCode() + (context!=null ? 31*context.hashCode() : 0);
	}
	
	@Override
	public long getId() {
		return hashCode();
	}
  

}
