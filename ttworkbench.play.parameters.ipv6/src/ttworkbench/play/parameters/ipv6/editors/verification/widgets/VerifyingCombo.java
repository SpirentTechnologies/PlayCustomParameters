package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;

public class VerifyingCombo extends VerifyingAdapter<Combo>{

	public VerifyingCombo( Composite theParent, int theStyle, final IVerificator<String> ... theVerificators) {
		super( theParent, theStyle, theVerificators);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setText(String theText) {
		// TODO Auto-generated method stub
		getEncapsulatedWidget().setText( theText);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return getEncapsulatedWidget().getText();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		getEncapsulatedWidget().setFocus();
	}

	@Override
	protected String getModifiedTextByEvent(Event theEvent) {
		String currentText = getEncapsulatedWidget().getText();
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
	protected Combo createWidget(Composite theParent, int theStyle) {
		// TODO Auto-generated method stub
		return new Combo(theParent, theStyle);
	}

}
