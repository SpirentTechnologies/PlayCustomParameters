package ttworkbench.play.parameters.ipv6.components.design;

import java.util.EnumSet;


public class BasicControlState implements IBasicControlState {
	
	private ComponentState componentState = ComponentState.INITIATING;
	private ControlState controlState = ControlState.NULL;
	private EnumSet<ControlStateFlag> controlStateFlags = EnumSet.of( ControlStateFlag.VISIBLE, ControlStateFlag.ENABLED);
	
	public void setComponentState( ComponentState theComponentState) {
		this.componentState = theComponentState;
	}
	
	public void setControlState( ControlState theControlState) {
		this.controlState = theControlState;
	}

	public void setFlag( ControlStateFlag theControlStateFlag) {
		this.controlStateFlags.add( theControlStateFlag);
	}
	
	public void unsetFlag( ControlStateFlag theControlStateFlag) {
		this.controlStateFlags.remove( theControlStateFlag);
	}
	
	public void flag( ControlStateFlag theControlStateFlag, final boolean isSet) {
		if ( isSet)
			this.controlStateFlags.add( theControlStateFlag);
		else
			this.controlStateFlags.remove( theControlStateFlag);	
	}
	
	public boolean isset( ComponentState theComponentState) {
		return componentState.equals( theComponentState);
	}
	
	public boolean isset( ControlState theControlState) {
		return controlState.equals( theControlState);
	}
	
	public boolean isset( ControlStateFlag theControlStateFlag) {
		return controlStateFlags.contains( theControlStateFlag);
	}
}