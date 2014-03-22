package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Spinner;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;


public class VerifyingSpinner<P> extends VerifyingAdapter<Spinner,P> {

	
	public VerifyingSpinner( final IParameter<P> theParameter, final Composite theParent, final int theStyle, final IVerificator<String> ... theVerificators) {
	  super( theParameter, theParent, theStyle, theVerificators);
  }

	@Override
	public void setText(String theText) {
		getControl().setSelection( Integer.valueOf( theText));
	}

	@Override
	public String getText() {
		return getControl().getText();
	}

	@Override
	protected String getModifiedTextByEvent(Event theEvent) {
		String currentText = getControl().getText();
		Character key = theEvent.character;
		String insertion = (key == '\b') ? "" : theEvent.text; 
		int beginIndex = theEvent.start;
		int endIndex = theEvent.end;
		String leftString = currentText.substring( 0, beginIndex);
		String rightString = currentText.substring( endIndex, currentText.length());
		String modifiedText = leftString + insertion + rightString;

		if ( modifiedText.isEmpty())
			modifiedText = "0";

		return modifiedText;
	}

	@Override
	protected Spinner createControl(Composite theParent, int theStyle) {
		return new Spinner( theParent, theStyle);
	}
	

}
