package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;

import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class VerifyingCombo<P extends Expression> extends VerifyingAdapter<Combo,P>{

	public VerifyingCombo( final IParameter<P> theParameter, Composite theParent, int theStyle, final IVerifier<String> ... theVerifiers) {
		super( theParameter, theParent, theStyle, theVerifiers);
	}

	@Override
	public void setText(String theText) {
		getControl().setText( theText);
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
			modifiedText = "00:00:00:00:00:00";

		return modifiedText;		
	}

	@Override
	protected Combo createControl(Composite theParent, int theStyle) {
		return new Combo(theParent, theStyle);
	}


}
