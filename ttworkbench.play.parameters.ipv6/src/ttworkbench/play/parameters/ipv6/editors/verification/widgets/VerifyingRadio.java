package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class VerifyingRadio<P> extends VerifyingAdapter<Group,P>{

	public VerifyingRadio( final IParameter<P> theParameter, Composite theParent, int theStyle, final IVerifier<String> ... theVerifiers) {
		super( theParameter, theParent, theStyle, theVerifiers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setText(String theText) {
		// TODO Auto-generated method stub
		getControl().setText( theText);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return getControl().getText();
	}

	@Override
	protected String getModifiedTextByEvent(Event theEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Group createControl(Composite theParent, int theStyle) {
		// TODO Auto-generated method stub
		return new Group(theParent, theStyle);
	}

}
