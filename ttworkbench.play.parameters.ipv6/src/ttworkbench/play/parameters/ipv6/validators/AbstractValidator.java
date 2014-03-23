package ttworkbench.play.parameters.ipv6.validators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.ttworkbench.ttman.parameters.api.IActionHandler;
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
	private final Set<IActionHandler> actionHandlers = new HashSet<IActionHandler>();
	
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
	public synchronized final List<ValidationResult> validate( IParameter theParameter, Object theClient) {
		List<ValidationResult> results = validateParameter( theParameter, theClient);
		
		if ( results == null)
			results = new ArrayList<ValidationResult>();
			
		if ( !results.isEmpty())
		  notifyMessageHandlers( this, results, theParameter);
		
		return results;
	}

	private void notifyMessageHandlers(AbstractValidator theAbstractValidator,
			List<ValidationResult> theResults, IParameter<?> theParameter) {
		for ( IMessageHandler messageHandler : messageHandlers) {
			messageHandler.report( this, theResults, theParameter);
		} 
	}

	protected abstract List<ValidationResult> validateParameter(IParameter<?> theParameter, Object theClient);

	@Override
	public void registerForMessages(IMessageHandler theMessageHandler) {
		messageHandlers.add( theMessageHandler);
	}
	
	@Override
	public void registerForActions(IActionHandler theActionHandler) {
		actionHandlers.add( theActionHandler);
	}
	

	@Override
	public long getId() {
		return hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ( ( description == null) ? 0 : description.hashCode());
		result = prime * result + ( ( title == null) ? 0 : title.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractValidator other = (AbstractValidator) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals( other.attributes))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals( other.description))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals( other.title))
			return false;
		return true;
	}
}
