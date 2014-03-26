package ttworkbench.play.parameters.ipv6.editors.verification.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

import ttworkbench.play.parameters.ipv6.editors.verification.IVerificationListener;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerifyingAdapter;


public class VerifyingText<P> extends VerifyingAdapter<Text,P> {
	
	
  public VerifyingText( final IParameter<P> theParameter, final Composite theParent, final int theStyle, final IVerifier<String> ... theVerifiers) {
	  super( theParameter, theParent, theStyle, theVerifiers);
  }
	
	@Override
	public void setText( final String theText) {
		getControl().setText( theText);
	}

	@Override
	public String getText() {
		return getControl().getText();
	}
	
	@Override
	protected final Text createControl(Composite theParent, int theStyle) {
		return new Text( theParent, theStyle);
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

	
	
}
