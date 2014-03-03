package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.ttworkbench.ttman.parameters.api.IAttribute;
import com.testingtech.ttworkbench.ttman.parameters.api.IMessageHandler;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public abstract class AbstractValidator implements IParameterValidator {

	private final String title;
	private final String description;
	
	private final Set<IAttribute> attributes = new HashSet<IAttribute>();
	
	private final Set<IMessageHandler> messageHandlers = new HashSet<IMessageHandler>();
	
	public AbstractValidator( final String theTitle, final String theDescription) {
		this.title = theTitle;
		this.description = theDescription;
	}
	
	@Override
	public void setAttribute(String theName, String theValue) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public final List<ValidationResult> validate(IParameter theParameter) {
		List<ValidationResult> results = validateParameter( theParameter);
		
		if ( results == null)
			results = new ArrayList<ValidationResult>();
			
		if ( !results.isEmpty())
		  notifyMessageHandlers( this, results, theParameter);
		
		return results;
	}

	private void notifyMessageHandlers(AbstractValidator theAbstractValidator,
			List<ValidationResult> theResults, IParameter theParameter) {
		for ( IMessageHandler messageHandler : messageHandlers) {
			messageHandler.report( this, theResults, theParameter);
		} 
	}

	protected abstract List<ValidationResult> validateParameter(IParameter theParameter);

	@Override
	public void registerForMessages(IMessageHandler theMessageHandler) {
		messageHandlers.add( theMessageHandler);
	}

}
