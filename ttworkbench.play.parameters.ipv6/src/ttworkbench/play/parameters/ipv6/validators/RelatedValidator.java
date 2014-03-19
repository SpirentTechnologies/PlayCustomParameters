package ttworkbench.play.parameters.ipv6.validators;

import java.util.HashMap;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IValidatorContext;

public abstract class RelatedValidator extends AbstractValidator {
	
	
	public interface RelationKey {
		
	}
	private IValidatorContext context;
	
	
	public abstract RelationKey[] getRelationKeys();
	
	private HashMap<RelationKey, IParameter<?>> relatedParameters = new HashMap<RelationKey, IParameter<?>>();	
	private HashMap<RelationKey, IParameterEditor<?>> relatedEditorsInWidget = new HashMap<RelationKey, IParameterEditor<?>>();

	public RelatedValidator( String theTitle, String theDescription, IValidatorContext theContext) {
		super( theTitle, theDescription);
		this.context = theContext;
	}

	public void addParameter( RelationKey key, IParameter<?> parameter) {
		relatedParameters.put( key, parameter);
	}

	public void addEditor( RelationKey key, IParameterEditor<?> editor) {
		relatedEditorsInWidget.put( key, editor);
	}
	
	@SuppressWarnings("unchecked")
	public <T> IParameter<T> getParameter( RelationKey key) {
		return (IParameter<T>) relatedParameters.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> IParameterEditor<T> getEditor( RelationKey key) {
		return (IParameterEditor<T>) relatedEditorsInWidget.get(key);
	}
	
	protected IValidatorContext getContext() {
		return context;
	}
	
	@Override
	public int hashCode() {
		// TODO + context 
		return super.hashCode() + 31*context.hashCode();
	}
	
	@Override
	public long getId() {
		return hashCode();
	}
  

}
