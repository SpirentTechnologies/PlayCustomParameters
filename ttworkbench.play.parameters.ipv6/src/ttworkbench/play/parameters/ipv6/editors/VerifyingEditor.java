package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;

public abstract class VerifyingEditor<C extends Control, P> extends ValidatingEditor<P> {
	
	private IVerifyingControl<? extends C,P> inputControl;
	

	public VerifyingEditor( final String theTitle, final String theDescription) {
		super( theTitle, theDescription);
	}
	
	public VerifyingEditor( final String theTitle, final String theDescription, final IVerifyingControl<C, P> theInputControl) {
		this( theTitle, theDescription);
		setInputControl( theInputControl);
	}
	
	public void setInputValue( final String theValue) {
		inputControl.setText( theValue);
	}
	
	protected void setInputControl( final IVerifyingControl<? extends C, P> theInputControl) {
		this.inputControl = theInputControl;
	}
	
	public IVerifyingControl<? extends C, P> getInputControl() {
		return inputControl;
	}
	
	@Override
	public void setFocus() {
		if ( hasControl())
		  inputControl.getControl().setFocus();
	}

  @Override
  public void reloadParameter() {
  	if ( hasControl()) {
  		String updatedValue = ParameterValueUtil.getValue( getParameter());
  	  inputControl.forceText( updatedValue);
  	}
  }

}
