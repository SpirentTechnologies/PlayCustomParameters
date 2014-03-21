package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerificator;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;


public class VerifyingText extends VerifyingAdapter<Text> {
	
	
  public VerifyingText( final Composite theParent, final int theStyle, final IVerificator<String> ... theVerificators) {
	  super( theParent, theStyle, theVerificators);
  }
	
	@Override
	public void setText( final String theText) {
		getEncapsulatedWidget().setText( theText);
	}

	@Override
	public String getText() {
		return getEncapsulatedWidget().getText();
	}
	
	@Override
	protected final Text createWidget(Composite theParent, int theStyle) {
		return new Text( theParent, theStyle);
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
			modifiedText = "0";

		return modifiedText;		
	}
	
	@Override
	public void setFocus() {
		getEncapsulatedWidget().setFocus();
	}
	
}
