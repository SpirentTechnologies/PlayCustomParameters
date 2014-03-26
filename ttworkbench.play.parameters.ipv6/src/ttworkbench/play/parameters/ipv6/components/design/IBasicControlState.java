package ttworkbench.play.parameters.ipv6.components.design;

public interface IBasicControlState {
	boolean isset( ComponentState theComponentState);
	
	boolean isset( ControlState theControlState);
	
	boolean isset( ControlStateFlag theControlStateFlag);
	
	void setFlag( ControlStateFlag theControlStateFlag);
	
	void unsetFlag( ControlStateFlag theControlStateFlag);
	
}