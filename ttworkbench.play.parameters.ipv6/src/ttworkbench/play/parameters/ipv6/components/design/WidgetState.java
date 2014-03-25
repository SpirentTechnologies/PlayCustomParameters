package ttworkbench.play.parameters.ipv6.components.design;

import java.util.EnumSet;


public class WidgetState extends BasicControlState implements IWidgetState {
	
	private EnumSet<WidgetStateFlag> widgetStateFlags = EnumSet.noneOf( WidgetStateFlag.class);
	
	public void setFlag( final WidgetStateFlag theWidgetStateFlag) {
		this.widgetStateFlags.add( theWidgetStateFlag);
	}
	
	public void unsetFlag( final WidgetStateFlag theWidgetStateFlag) {
		this.widgetStateFlags.remove( theWidgetStateFlag);
	}
	
	public void flag( final WidgetStateFlag theWidgetStateFlag, final boolean isSet) {
		if ( isSet)
			this.widgetStateFlags.add( theWidgetStateFlag);
		else
			this.widgetStateFlags.remove( theWidgetStateFlag);	
	}
	
	public boolean isset( final WidgetStateFlag theWidgetStateFlag) {
		return widgetStateFlags.contains( theWidgetStateFlag);
	}
}