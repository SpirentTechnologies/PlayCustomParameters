package ttworkbench.play.parameters.ipv6.components.design;

public interface IWidgetState extends IBasicControlState {
	
	boolean isset( WidgetStateFlag theWidgetStateFlag);
	
	void setFlag( WidgetStateFlag theWidgetStateFlag);
	
	void unsetFlag( WidgetStateFlag theWidgetStateFlag);
	
}